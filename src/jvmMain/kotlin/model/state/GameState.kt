package model.state

import androidx.compose.ui.geometry.Offset
import model.board.*
import model.board.Position.*
import model.board.Set
import model.board.Set.BLACK
import model.board.Set.WHITE
import model.move.Board
import model.move.Move
import model.move.Promotion

data class GameState(
    val activePosition: Position? = null,
    val legalMoves: List<Move> = emptyList(),
    val boardSnapshots: List<BoardSnapshot> = listOf(
        BoardSnapshot(
            piecesByPosition = initialPieces,
            setToPlay = WHITE,
        )
    ),
    val promotionSelection: List<Promotion> = emptyList(),
    val uiState: UiState = UiState(),
) {
    val piecesByPosition = boardSnapshots.last().piecesByPosition
    val setToPlay = boardSnapshots.last().setToPlay
}

data class BoardSnapshot(
    val piecesByPosition: Board,
    val setToPlay: Set,
    val move: Move? = null,
)

data class UiState(
    val squareSize: Int = 1,
    val pieceDragOffset: Offset = Offset.Zero,
    val pieceMinDragOffset: Offset = Offset.Zero,
    val pieceMaxDragOffset: Offset = Offset.Zero,
    val constrainedPieceDragOffset: Offset = Offset.Zero,
)

private val initialPieces: Board = mapOf(
    a8 to Rook(BLACK),
    b8 to Knight(BLACK),
    c8 to Bishop(BLACK),
    d8 to Queen(BLACK),
    e8 to King(BLACK),
    f8 to Bishop(BLACK),
    g8 to Knight(BLACK),
    h8 to Rook(BLACK),

    a7 to Pawn(BLACK),
    b7 to Pawn(BLACK),
    c7 to Pawn(BLACK),
    d7 to Pawn(BLACK),
    e7 to Pawn(BLACK),
    f7 to Pawn(BLACK),
    g7 to Pawn(BLACK),
    h7 to Pawn(BLACK),

    a2 to Pawn(WHITE),
    b2 to Pawn(WHITE),
    c2 to Pawn(WHITE),
    d2 to Pawn(WHITE),
    e2 to Pawn(WHITE),
    f2 to Pawn(WHITE),
    g2 to Pawn(WHITE),
    h2 to Pawn(WHITE),

    a1 to Rook(WHITE),
    b1 to Knight(WHITE),
    c1 to Bishop(WHITE),
    d1 to Queen(WHITE),
    e1 to King(WHITE),
    f1 to Bishop(WHITE),
    g1 to Knight(WHITE),
    h1 to Rook(WHITE),
)
