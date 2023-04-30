package model.service.move

import model.board.*
import model.move.Capture
import model.move.Move
import model.move.StandardMove
import model.service.util.positionOf
import model.state.GameState

fun Piece.pseudoLegalMoves(gameState: GameState, includeCastle: Boolean = false): List<Move> = when (this) {
    is King -> pseudoLegalStepMoves(kingSteps, gameState) +
            if (includeCastle) pseudoLegalCastleMoves(gameState) else emptyList()

    is Queen -> pseudoLegalLineMoves(queenDirections, gameState)
    is Rook -> pseudoLegalLineMoves(rookDirections, gameState)
    is Bishop -> pseudoLegalLineMoves(bishopDirections, gameState)
    is Knight -> pseudoLegalStepMoves(knightSteps, gameState)
    is Pawn -> pseudoLegalPawnMoves(gameState)
}

private fun Piece.pseudoLegalStepMoves(steps: List<Pair<Int, Int>>, gameState: GameState): List<Move> {
    val from = gameState positionOf this
    return steps.mapNotNull { step ->
        val to = from + step
        val capturedPiece = gameState.piecesByPosition[to]
        if (to == null || capturedPiece?.set == set) null
        else move(piece = this, from = from, to = to, capturedPiece = capturedPiece)
    }
}

private fun Piece.pseudoLegalLineMoves(directions: List<Pair<Int, Int>>, gameState: GameState): List<Move> {
    val from = gameState positionOf this
    return directions.flatMap { direction ->
        var to = from + direction
        val moves = mutableListOf<Move>()
        while (to != null) {
            val capturdPiece = gameState.piecesByPosition[to]
            if (capturdPiece?.set != set) {
                moves += move(piece = this, from = from, to = to, capturedPiece = capturdPiece)
            }
            if (capturdPiece != null) break
            to += direction
        }
        moves
    }
}

fun move(piece: Piece, from: Position, to: Position, capturedPiece: Piece?) =
    if (capturedPiece != null) Capture(piece = piece, from = from, to = to, capturedPiece = capturedPiece)
    else StandardMove(piece = piece, from = from, to = to)

private val bishopDirections = listOf(
    -1 to 1,
    1 to 1,
    1 to -1,
    -1 to -1,
)

private val rookDirections = listOf(
    0 to 1,
    1 to 0,
    0 to -1,
    -1 to 0,
)

private val queenDirections = bishopDirections + rookDirections

private val kingSteps = queenDirections

private val knightSteps = listOf(
    -1 to 2,
    1 to 2,
    2 to 1,
    2 to -1,
    1 to -2,
    -1 to -2,
    -2 to -1,
    -2 to 1,
)
