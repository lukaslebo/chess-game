import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import model.board.King
import model.board.Set
import model.service.DefaultChessUiService
import view.board.ChessBoard

fun main() = application {
    val state = WindowState(
        size = DpSize(1100.dp, 900.dp),
        position = WindowPosition(Alignment.Center),
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "Chess",
        icon = painterResource(King(Set.BLACK).asset),
        state = state,
    ) {
        App()
    }
}

val dark = Color(0xFF2B2B2B)
val darkFaded = Color(0xFF323232)

@Composable
@Preview
fun App() {
    var gameState by remember { mutableStateOf(DefaultChessUiService.gameState) }
    DefaultChessUiService.updateOnStateChanges { gameState = it }

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize().background(dark).padding(32.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ChessBoard(
                gameState = gameState,
                onClick = DefaultChessUiService::onClick,
                onDragStart = DefaultChessUiService::onDragStart,
                onDrag = DefaultChessUiService::onDrag,
                onDragEnd = DefaultChessUiService::onDragEnd,
                applyPromotion = DefaultChessUiService::applyPromotion,
            )
        }
    }
}
