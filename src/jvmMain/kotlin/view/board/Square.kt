package view.board

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.onClick
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import model.board.Position
import model.board.Square
import view.board.decorator.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Square(
    square: Square,
    onClick: (Position) -> Unit,
    onDragStart: (Position) -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onClick { onClick(square.position) }
    ) {
        squareDecorators.forEach {
            it.decorate(square)
        }
        Piece(
            square = square,
            onDragStart = onDragStart,
            onDrag = onDrag,
            onDragEnd = onDragEnd,
        )
    }
}

private val squareDecorators = arrayOf(
    BackgroundDecorator,
    ActivePieceDecorator,
    LegalMoveDecorator,
    CaptureDecorator,
    LabelDecorator,
)
