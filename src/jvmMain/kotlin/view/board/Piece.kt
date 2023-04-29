package view.board

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import model.board.Position
import model.board.Square

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Piece(
    square: Square,
    onDragStart: (Position) -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit,
) {
    val dragOffset = if (square.isActive) square.gameState.uiState.constrainedPieceDragOffset else Offset.Zero
    if (square.piece != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(square.piece.asset),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(0.7f)
                    .onDrag(
                        onDragStart = { onDragStart(square.position) },
                        onDrag = onDrag,
                        onDragEnd = onDragEnd,
                    )
                    .offset(
                        dragOffset.x.dp,
                        dragOffset.y.dp,
                    ),
            )
        }
    }
}
