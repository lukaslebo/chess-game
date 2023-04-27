package view.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.board.*
import model.board.Set

@Composable
fun ChessBoard() {
    val squaresByPosition = Position.values().associateWith { Square(it, initialPieces[it]) }

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

private val initialPieces = mapOf(
    Position.a8 to Rook(Set.BLACK),
    Position.b8 to Knight(Set.BLACK),
    Position.c8 to Bishop(Set.BLACK),
    Position.d8 to Queen(Set.BLACK),
    Position.e8 to King(Set.BLACK),
    Position.f8 to Bishop(Set.BLACK),
    Position.g8 to Knight(Set.BLACK),
    Position.h8 to Rook(Set.BLACK),

    Position.a7 to Pawn(Set.BLACK),
    Position.b7 to Pawn(Set.BLACK),
    Position.c7 to Pawn(Set.BLACK),
    Position.d7 to Pawn(Set.BLACK),
    Position.e7 to Pawn(Set.BLACK),
    Position.f7 to Pawn(Set.BLACK),
    Position.g7 to Pawn(Set.BLACK),
    Position.h7 to Pawn(Set.BLACK),

    Position.a2 to Pawn(Set.WHITE),
    Position.b2 to Pawn(Set.WHITE),
    Position.c2 to Pawn(Set.WHITE),
    Position.d2 to Pawn(Set.WHITE),
    Position.e2 to Pawn(Set.WHITE),
    Position.f2 to Pawn(Set.WHITE),
    Position.g2 to Pawn(Set.WHITE),
    Position.h2 to Pawn(Set.WHITE),

    Position.a1 to Rook(Set.WHITE),
    Position.b1 to Knight(Set.WHITE),
    Position.c1 to Bishop(Set.WHITE),
    Position.d1 to Queen(Set.WHITE),
    Position.e1 to King(Set.WHITE),
    Position.f1 to Bishop(Set.WHITE),
    Position.g1 to Knight(Set.WHITE),
    Position.h1 to Rook(Set.WHITE),
)
