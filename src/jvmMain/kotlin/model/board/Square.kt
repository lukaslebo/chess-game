package model.board

import androidx.compose.ui.graphics.Color
import model.state.GameState

private val lightSquareColor = Color(0xFFEEEED2)
private val darkSquareColor = Color(0xFF769656)

class Square(
    val position: Position,
    val gameState: GameState,
) {
    val isActive = gameState.activePosition == position
    val piece: Piece? = gameState.piecesByPosition[position]

    val isLightSquare = (position.ordinal + position.file.ordinal % 2) % 2 == 1
    val isDarkSquare = (position.ordinal + position.file.ordinal % 2) % 2 == 0

    val backgroundColor = if (isDarkSquare) darkSquareColor else lightSquareColor
    val color = if (isLightSquare) darkSquareColor else lightSquareColor
}
