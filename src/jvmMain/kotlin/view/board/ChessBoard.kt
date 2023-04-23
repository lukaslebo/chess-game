package view.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.board.File
import model.board.Position
import model.board.Rank
import model.board.Square

@Composable
fun ChessBoard() {
    val squaresByPosition = Position.values().associateWith { Square(it) }

    Box(modifier = Modifier.aspectRatio(1f)) {
        Column {
            for (rank in Rank.values().reversed()) {
                Row(modifier = Modifier.weight(1f)) {
                    for (file in File.values()) {
                        Box(modifier = Modifier.weight(1f)) {
                            val square = squaresByPosition.getValue(Position.fromFileAndRank(file, rank))
                            Square(square)
                        }
                    }
                }
            }
        }
    }
}
