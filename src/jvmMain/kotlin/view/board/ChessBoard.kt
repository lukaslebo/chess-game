package view.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.zIndex
import model.board.File
import model.board.Position
import model.board.Rank
import model.board.Square
import model.service.DefaultChessUiService
import model.state.GameState

@Composable
fun ChessBoard(
    gameState: GameState,
    onClick: (Position) -> Unit,
    onDragStart: (Position) -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
) {
    val squaresByPosition = Position.values().associateWith { Square(it, gameState) }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .onSizeChanged { DefaultChessUiService.onSquareSizeChanged(it.width / 8) }
    ) {
        Column {
            for (rank in Rank.values().reversed()) {
                val zIndexRow = if (gameState.activePosition?.rank == rank) 1f else 0f

                Row(modifier = Modifier.weight(1f).zIndex(zIndexRow)) {
                    for (file in File.values()) {
                        val square = squaresByPosition.getValue(Position.fromFileAndRank(file, rank))
                        val zIndexSquare = if (square.isActive) 1f else 0f

                        Box(modifier = Modifier.weight(1f).zIndex(zIndexSquare)) {
                            Square(
                                square = square,
                                onClick = onClick,
                                onDragStart = onDragStart,
                                onDrag = onDrag,
                                onDragEnd = onDragEnd,
                            )
                        }
                    }
                }
            }
        }
        PromotionSelection(gameState)
    }
}
