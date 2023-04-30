@file:Suppress("EnumEntryName")

package model.board

enum class Position {
    a1, a2, a3, a4, a5, a6, a7, a8,
    b1, b2, b3, b4, b5, b6, b7, b8,
    c1, c2, c3, c4, c5, c6, c7, c8,
    d1, d2, d3, d4, d5, d6, d7, d8,
    e1, e2, e3, e4, e5, e6, e7, e8,
    f1, f2, f3, f4, f5, f6, f7, f8,
    g1, g2, g3, g4, g5, g6, g7, g8,
    h1, h2, h3, h4, h5, h6, h7, h8;

    val file: File = File.values()[ordinal / 8]
    val rank: Rank = Rank.values()[ordinal % 8]

    companion object {
        private val positionByFileAndRank = values().associateBy { it.file to it.rank }

        fun fromFileAndRank(file: File, rank: Rank) =
            positionByFileAndRank.getValue(file to rank)

        fun fromFileAndRank(file: Int, rank: Int): Position? =
            positionByFileAndRank[File.from(file) to Rank.from(rank)]
    }

    operator fun plus(distance: Pair<Int, Int>): Position? =
        fromFileAndRank(file.number + distance.first, rank.number + distance.second)
}

enum class File {
    a, b, c, d, e, f, g, h;

    val number = ordinal + 1

    companion object {
        fun from(file: Int): File? = if (file in 1..8) values()[file - 1] else null
    }
}

enum class Rank {
    r1, r2, r3, r4, r5, r6, r7, r8;

    val number = ordinal + 1

    companion object {
        fun from(rank: Int): Rank? = if (rank in 1..8) Rank.values()[rank - 1] else null
    }
}
