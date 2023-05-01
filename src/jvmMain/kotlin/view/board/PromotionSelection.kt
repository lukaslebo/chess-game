package view.board

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import model.board.Queen
import model.board.Rank
import model.board.Set
import model.service.DefaultChessUiService
import model.state.GameState

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun PromotionSelection(gameState: GameState) {
    if (gameState.promotionSelection.isNotEmpty()) {
        val position = gameState.promotionSelection.first().to
        val offsetX = ((position.file.ordinal + 0.15f) * gameState.uiState.squareSize).dp
        val offsetY = (gameState.uiState.squareSize * if (position.rank == Rank.r8) 0.15f else 5.05f).dp
        val sortedPromotions = when (gameState.setToPlay) {
            Set.WHITE -> gameState.promotionSelection.sortedByDescending { it.pieceAfterPromotion.value }
            Set.BLACK -> gameState.promotionSelection.sortedBy { it.pieceAfterPromotion.value }
        }
        Column(
            modifier = Modifier.absoluteOffset(offsetX, offsetY).clip(RoundedCornerShape(20)),
        ) {
            for (promotion in sortedPromotions) {
                val initialColor = if (promotion.pieceAfterPromotion is Queen) Color.LightGray else Color.White
                var color by remember { mutableStateOf(initialColor) }
                Box(
                    modifier = Modifier
                        .height((gameState.uiState.squareSize * 0.7f).dp)
                        .aspectRatio(1f)
                        .background(color)
                        .onPointerEvent(PointerEventType.Enter) { color = Color.LightGray }
                        .onPointerEvent(PointerEventType.Exit) { color = Color.White }
                        .onClick { DefaultChessUiService.applyPromotion(promotion) }
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(promotion.pieceAfterPromotion.asset),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
