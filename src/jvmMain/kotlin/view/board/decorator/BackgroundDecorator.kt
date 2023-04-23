package view.board.decorator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.board.Square

object BackgroundDecorator : SquareDecorator {
    @Composable
    override fun decorate(square: Square) {
        Box(Modifier.fillMaxSize().background(square.backgroundColor))
    }
}
