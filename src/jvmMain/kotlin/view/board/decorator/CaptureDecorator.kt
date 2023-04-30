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

object CaptureDecorator : SquareDecorator {
    @Composable
    override fun decorate(square: Square) {
        if (square.isCapture) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.fillMaxSize(0.95f).align(Alignment.Center)) {
                    drawCircle(
                        Brush.radialGradient(
                            0.0f to Color.Red.copy(alpha = 0.5f),
                            0.4f to Color.Red.copy(alpha = 0.5f),
                            1.0f to Color.Red.copy(alpha = 0.0f),
                        )
                    )
                }
            }
        }
    }
}

