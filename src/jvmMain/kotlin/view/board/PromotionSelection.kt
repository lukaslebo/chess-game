package view.board

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import model.board.Queen
import model.board.Rank
import model.board.Set
import model.move.Promotion
import model.state.GameState

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun PromotionSelection(
    gameState: GameState,
    applyPromotion: (Promotion) -> Unit,
    cancelPromotion: () -> Unit,
) {
    if (gameState.promotionSelection.isNotEmpty()) {
        val position = gameState.promotionSelection.first().to
        val offsetX = ((position.file.ordinal + 0.15f) * gameState.uiState.squareSize).dp
        val offsetY =
            (gameState.uiState.squareSize * if (position.rank == Rank.r8) 0.15f else 5.05f).dp - if (position.rank == Rank.r1) 24.dp else 0.dp
        val sortedPromotions = when (gameState.setToPlay) {
            Set.WHITE -> gameState.promotionSelection.sortedByDescending { it.pieceAfterPromotion.value }
            Set.BLACK -> gameState.promotionSelection.sortedBy { it.pieceAfterPromotion.value }
        }
        Column(
            modifier = Modifier.width((gameState.uiState.squareSize * 0.7f).dp).absoluteOffset(offsetX, offsetY)
                .clip(RoundedCornerShape(20)),
        ) {
            if (gameState.setToPlay == Set.BLACK) {
                CancelPromotion(cancelPromotion = cancelPromotion)
            }
            for (promotion in sortedPromotions) {
                val initialColor = if (promotion.pieceAfterPromotion is Queen) Color.LightGray else Color.White
                var color by remember { mutableStateOf(initialColor) }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(color)
                        .onPointerEvent(PointerEventType.Enter) { color = Color.LightGray }
                        .onPointerEvent(PointerEventType.Exit) { color = Color.White }
                        .onClick { applyPromotion(promotion) }
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(promotion.pieceAfterPromotion.asset),
                        contentDescription = null,
                    )
                }
            }
            if (gameState.setToPlay == Set.WHITE) {
                CancelPromotion(cancelPromotion = cancelPromotion)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun CancelPromotion(cancelPromotion: () -> Unit) {
    var color by remember { mutableStateOf(Color.White) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color)
        .onPointerEvent(PointerEventType.Enter) { color = Color.LightGray }
        .onPointerEvent(PointerEventType.Exit) { color = Color.White }
        .onClick { cancelPromotion() }
        .padding(10.dp)
    ) {
        Canvas(modifier = Modifier.align(Alignment.Center).height(14.dp).width(14.dp)) {
            drawPath(
                Path().apply {
                    moveTo(0f, 0f)
                    lineTo(14f, 14f)
                    moveTo(0f, 14f)
                    lineTo(14f, 0f)
                },
                color = Color.DarkGray,
                style = Stroke(width = 4f, cap = StrokeCap.Round)
            )
        }
    }
}
