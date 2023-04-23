package view.board.decorator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.board.File
import model.board.Rank
import model.board.Square

object LabelDecorator : SquareDecorator {
    @Composable
    override fun decorate(square: Square) {
        if (square.position.file == File.a) {
            Box(Modifier.fillMaxSize().padding(8.dp)) {
                Text(
                    square.position.rank.number.toString(),
                    color = square.color,
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }
        }
        if (square.position.rank == Rank.r1) {
            Box(Modifier.fillMaxSize().padding(4.dp)) {
                Text(square.position.file.name, color = square.color, modifier = Modifier.align(Alignment.BottomEnd))
            }
        }
    }
}
