package view.board

import androidx.compose.runtime.Composable
import model.board.Square
import view.board.decorator.BackgroundDecorator
import view.board.decorator.LabelDecorator

@Composable
fun Square(square: Square) {
    squareDecorators.forEach {
        it.decorate(square)
    }
}

private val squareDecorators = arrayOf(
    BackgroundDecorator,
    LabelDecorator,
)
