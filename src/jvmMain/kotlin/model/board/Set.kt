package model.board

enum class Set {
    WHITE, BLACK;

    val opposite: Set
        get() = when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
        }
}
