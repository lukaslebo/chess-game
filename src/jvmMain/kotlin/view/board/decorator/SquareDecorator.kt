package view.board.decorator

import androidx.compose.runtime.Composable
import model.board.Square

interface SquareDecorator {
    @Composable
    fun decorate(square: Square)
}
