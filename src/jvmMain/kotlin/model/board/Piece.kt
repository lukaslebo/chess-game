package model.board

import model.board.Set.BLACK
import model.board.Set.WHITE

sealed interface Piece {
    val set: Set
    val symbol: String
    val textSymbol: String
    val value: Int
    val asset: String
}

class King(override val set: Set) : Piece {
    override val symbol = when (set) {
        WHITE -> "♔"
        BLACK -> "♚"
    }
    override val textSymbol = "K"
    override val value = 0
    override val asset = "chess_king_${set.name.lowercase()}.svg"

    val startingRank = when (set) {
        WHITE -> Rank.r1
        BLACK -> Rank.r8
    }
}

class Queen(override val set: Set) : Piece {
    override val symbol = when (set) {
        WHITE -> "♕"
        BLACK -> "♛"
    }
    override val textSymbol = "Q"
    override val value = 9
    override val asset = "chess_queen_${set.name.lowercase()}.svg"
}

class Rook(override val set: Set) : Piece {
    override val symbol = when (set) {
        WHITE -> "♖"
        BLACK -> "♜"
    }
    override val textSymbol = "R"
    override val value = 5
    override val asset = "chess_rook_${set.name.lowercase()}.svg"
}

class Bishop(override val set: Set) : Piece {
    override val symbol = when (set) {
        WHITE -> "♗"
        BLACK -> "♝"
    }
    override val textSymbol = "B"
    override val value = 3
    override val asset = "chess_bishop_${set.name.lowercase()}.svg"
}

class Knight(override val set: Set) : Piece {
    override val symbol = when (set) {
        WHITE -> "♘"
        BLACK -> "♞"
    }
    override val textSymbol = "N"
    override val value = 3
    override val asset = "chess_knight_${set.name.lowercase()}.svg"
}

class Pawn(override val set: Set) : Piece {
    override val symbol = when (set) {
        WHITE -> "♙"
        BLACK -> "♟︎"
    }
    override val textSymbol = ""
    override val value = 1
    override val asset = "chess_pawn_${set.name.lowercase()}.svg"

    val startingRank = when (set) {
        WHITE -> Rank.r2
        BLACK -> Rank.r7
    }

    val rankAfterDoubleMove = when (set) {
        WHITE -> Rank.r4
        BLACK -> Rank.r5
    }
}
