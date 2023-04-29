package model.state

import androidx.compose.ui.geometry.Offset
import model.board.*
import model.board.Set

data class GameState(
    val activePosition: Position? = null,
    val boardSnapshots: List<BoardSnapshot> = listOf(
        BoardSnapshot(
            piecesByPosition = initialPieces,
            setToPlay = Set.WHITE,
        )
    ),
    val uiState: UiState = UiState(),
) {
    val piecesByPosition = boardSnapshots.last().piecesByPosition
    val setToPlay = boardSnapshots.last().setToPlay
}

data class BoardSnapshot(
    val piecesByPosition: Map<Position, Piece>,
    val setToPlay: Set,
    val move: BoardMove? = null,
)

data class Move(
    val from: Position,
    val to: Position,
)

data class BoardMove(
    val piece: Piece,
    val from: Position,
    val to: Position,
    val capturedPiece: Piece? = null,
)

data class UiState(
    val squareSize: Int = 1,
    val pieceDragOffset: Offset = Offset.Zero,
    val pieceMinDragOffset: Offset = Offset.Zero,
    val pieceMaxDragOffset: Offset = Offset.Zero,
    val constrainedPieceDragOffset: Offset = Offset.Zero,
)

private val initialPieces = mapOf(
    Position.a8 to Rook(Set.BLACK),
    Position.b8 to Knight(Set.BLACK),
    Position.c8 to Bishop(Set.BLACK),
    Position.d8 to Queen(Set.BLACK),
    Position.e8 to King(Set.BLACK),
    Position.f8 to Bishop(Set.BLACK),
    Position.g8 to Knight(Set.BLACK),
    Position.h8 to Rook(Set.BLACK),

    Position.a7 to Pawn(Set.BLACK),
    Position.b7 to Pawn(Set.BLACK),
    Position.c7 to Pawn(Set.BLACK),
    Position.d7 to Pawn(Set.BLACK),
    Position.e7 to Pawn(Set.BLACK),
    Position.f7 to Pawn(Set.BLACK),
    Position.g7 to Pawn(Set.BLACK),
    Position.h7 to Pawn(Set.BLACK),

    Position.a2 to Pawn(Set.WHITE),
    Position.b2 to Pawn(Set.WHITE),
    Position.c2 to Pawn(Set.WHITE),
    Position.d2 to Pawn(Set.WHITE),
    Position.e2 to Pawn(Set.WHITE),
    Position.f2 to Pawn(Set.WHITE),
    Position.g2 to Pawn(Set.WHITE),
    Position.h2 to Pawn(Set.WHITE),

    Position.a1 to Rook(Set.WHITE),
    Position.b1 to Knight(Set.WHITE),
    Position.c1 to Bishop(Set.WHITE),
    Position.d1 to Queen(Set.WHITE),
    Position.e1 to King(Set.WHITE),
    Position.f1 to Bishop(Set.WHITE),
    Position.g1 to Knight(Set.WHITE),
    Position.h1 to Rook(Set.WHITE),
)
