package model.service.util

import model.board.Piece
import model.state.GameState

infix fun GameState.positionOf(piece: Piece) = positionOfOrNull(piece) ?: error("$piece not found on board")

infix fun GameState.positionOfOrNull(piece: Piece?) =
    if (piece != null) piecesByPosition.entries.find { it.value === piece }?.key
    else null
