package model.board

import androidx.compose.ui.graphics.Color

private val lightSquareColor = Color(0xFFEEEED2)
private val darkSquareColor = Color(0xFF769656)

class Square(
    val position: Position,
    val piece: Piece? = null,
) {
    val isLightSquare = (position.ordinal + position.file.ordinal % 2) % 2 == 1
    val isDarkSquare = (position.ordinal + position.file.ordinal % 2) % 2 == 0

    val backgroundColor = if (isDarkSquare) darkSquareColor else lightSquareColor
    val color = if (isLightSquare) darkSquareColor else lightSquareColor
}
