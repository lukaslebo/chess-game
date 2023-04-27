package view.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import model.board.Square

@Composable
fun Piece(square: Square) {
    if (square.piece != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(square.piece.asset),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center).fillMaxSize(0.7f),
            )
        }
    }
}
