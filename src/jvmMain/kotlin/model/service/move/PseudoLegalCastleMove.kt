package model.service.move

import model.board.File
import model.board.King
import model.board.Position
import model.board.Rook
import model.move.KingSideCastle
import model.move.Move
import model.move.QueenSideCastle
import model.state.GameState

fun King.pseudoLegalCastleMoves(gameState: GameState) = listOfNotNull(
    gameState.castle(king = this, castleSide = CastleSide.KingSide),
    gameState.castle(king = this, castleSide = CastleSide.QueenSide),
)

private enum class CastleSide(
    val rookStartingFile: File,
    val emptyFiles: Set<File>,
    val kingPathFiles: Set<File>,
) {
    KingSide(
        rookStartingFile = File.h,
        emptyFiles = setOf(File.f, File.g),
        kingPathFiles = setOf(File.e, File.f, File.g),
    ),
    QueenSide(
        rookStartingFile = File.a,
        emptyFiles = setOf(File.b, File.c, File.d),
        kingPathFiles = setOf(File.e, File.d, File.c),
    ),
}

private fun GameState.castle(king: King, castleSide: CastleSide): Move? {
    val initialRookPosition = Position.fromFileAndRank(castleSide.rookStartingFile, king.startingRank)
    val rook = piecesByPosition[initialRookPosition] ?: return null
    if (rook !is Rook && rook.set == king.set) return null

    val kingMoved = boardSnapshots.any { it.move?.piece === king }
    val rookMoved = boardSnapshots.any { it.move?.piece === rook }
    if (kingMoved || rookMoved) return null

    val pathIsBlocked = castleSide.emptyFiles
        .map { Position.fromFileAndRank(it, king.startingRank) }
        .any { it in piecesByPosition }
    if (pathIsBlocked) return null

    val pathOfKing = castleSide.kingPathFiles.mapTo(mutableSetOf()) { Position.fromFileAndRank(it, king.startingRank) }

    val squaresUnderAttack = piecesByPosition.values
        .filter { it.set == king.set.opposite }
        .flatMap { it.pseudoLegalMoves(this) }
        .any { it.to in pathOfKing }

    if (squaresUnderAttack) return null

    return when (castleSide) {
        CastleSide.KingSide -> KingSideCastle(king)
        CastleSide.QueenSide -> QueenSideCastle(king)
    }
}
