package view.board.decorator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import model.board.Square

private val Orange = Color(0xFFFF6D0A)

object ActivePieceDecorator : SquareDecorator {
    @Composable
    override fun decorate(square: Square) {
        if (square.isActive) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.fillMaxSize(0.8f).align(Alignment.Center)) {
                    drawCircle(
                        Brush.radialGradient(
                            0.0f to Orange.copy(alpha = 0.6f),
                            0.7f to Orange.copy(alpha = 0.4f),
                            1.0f to Orange.copy(alpha = 0.0f),
                        )
                    )
                }
            }
        }
    }
}
