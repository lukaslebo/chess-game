package model.move

import model.board.*

typealias Board = Map<Position, Piece>

sealed interface BoardEffect {
    fun applyOn(board: Board): Board
}

sealed interface Move : BoardEffect {
    val piece: Piece
    val from: Position
    val to: Position
}

sealed interface NonCapturingMove : Move

sealed interface CapturingMove : Move {
    val capturedPiece: Piece
}

data class StandardMove(
    override val piece: Piece,
    override val from: Position,
    override val to: Position,
) : NonCapturingMove {
    override fun applyOn(board: Board) = board.minus(from).plus(to to piece)
}

data class Capture(
    override val piece: Piece,
    override val from: Position,
    override val to: Position,
    override val capturedPiece: Piece,
) : CapturingMove {
    override fun applyOn(board: Board) = board.minus(from).plus(to to piece)
}

data class EnPassant(
    override val piece: Piece,
    override val from: Position,
    override val to: Position,
    override val capturedPiece: Piece,
    val capturedOn: Position,
) : CapturingMove {
    override fun applyOn(board: Board) = board.minus(from).minus(capturedOn).plus(to to piece)
}

data class KingSideCastle(override val piece: King) : NonCapturingMove {
    private val rank = piece.startingRank
    override val from = Position.fromFileAndRank(File.e, rank)
    override val to = Position.fromFileAndRank(File.g, rank)
    private val rookFrom = Position.fromFileAndRank(File.h, rank)
    private val rookTo = Position.fromFileAndRank(File.f, rank)

    override fun applyOn(board: Board) =
        board.minus(from).minus(rookFrom).plus(to to piece).plus(rookTo to board.getValue(rookFrom))
}

data class QueenSideCastle(override val piece: King) : NonCapturingMove {
    private val rank = piece.startingRank
    override val from = Position.fromFileAndRank(File.e, rank)
    override val to = Position.fromFileAndRank(File.c, rank)
    private val rookFrom = Position.fromFileAndRank(File.a, rank)
    private val rookTo = Position.fromFileAndRank(File.d, rank)

    override fun applyOn(board: Board) =
        board.minus(from).minus(rookFrom).plus(to to piece).plus(rookTo to board.getValue(rookFrom))
}
