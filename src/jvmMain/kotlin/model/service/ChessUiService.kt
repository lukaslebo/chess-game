package model.service

import androidx.compose.ui.geometry.Offset
import model.board.File
import model.board.Position
import model.board.Rank
import model.state.BoardMove
import model.state.BoardSnapshot
import model.state.GameState
import model.state.Move
import kotlin.math.max
import kotlin.math.min

interface ChessUiService {
    val gameState: GameState
    fun onClick(position: Position)
    fun onDragStart(position: Position)
    fun onDrag(offset: Offset)
    fun onDragEnd()
    fun onSquareSizeChanged(squareSize: Int)
    fun updateOnStateChanges(onState: GameStateObserver)
}

typealias GameStateObserver = (GameState) -> Unit

object DefaultChessUiService : ChessUiService {
    override var gameState = GameState()
    private val observers = HashSet<GameStateObserver>()

    override fun onClick(position: Position) {
        val activePosition = gameState.activePosition

        if (activePosition != null) {
            gameState = gameState.applyMove(Move(activePosition, position))
        } else if (position in gameState.piecesByPosition) {
            gameState = gameState.copy(activePosition = position)
        }

        update()
    }

    override fun onDragStart(position: Position) {
        val squareSize = gameState.uiState.squareSize
        gameState = gameState.copy(
            activePosition = position,
            uiState = gameState.uiState.copy(
                pieceMinDragOffset = Offset(
                    (-position.file.ordinal * squareSize - squareSize / 2).toFloat(),
                    (-(7 - position.rank.ordinal) * squareSize - squareSize / 2).toFloat(),
                ),
                pieceMaxDragOffset = Offset(
                    ((7 - position.file.ordinal) * squareSize + squareSize / 2).toFloat(),
                    (position.rank.ordinal * squareSize + squareSize / 2).toFloat(),
                ),
            ),
        )
        update()
    }

    override fun onDrag(offset: Offset) {
        val newOffset = gameState.uiState.pieceDragOffset + offset
        val min = gameState.uiState.pieceMinDragOffset
        val max = gameState.uiState.pieceMaxDragOffset
        gameState = gameState.copy(
            uiState = gameState.uiState.copy(
                pieceDragOffset = newOffset,
                constrainedPieceDragOffset = Offset(
                    min(max(min.x, newOffset.x), max.x),
                    min(max(min.y, newOffset.y), max.y),
                )
            ),
        )
        update()
    }

    override fun onDragEnd() {
        val fromPosition = gameState.activePosition
        val toPosition = fromPosition?.getTargetPosition(
            offset = gameState.uiState.constrainedPieceDragOffset,
            squareSize = gameState.uiState.squareSize,
        )

        if (fromPosition != null && toPosition != null) {
            gameState = gameState.applyMove(Move(fromPosition, toPosition))
        }

        gameState = gameState.copy(
            uiState = gameState.uiState.copy(
                pieceDragOffset = Offset.Zero,
                pieceMinDragOffset = Offset.Zero,
                pieceMaxDragOffset = Offset.Zero,
                constrainedPieceDragOffset = Offset.Zero,
            ),
        )
        update()
    }

    override fun onSquareSizeChanged(squareSize: Int) {
        gameState = gameState.copy(
            uiState = gameState.uiState.copy(squareSize = squareSize),
        )
        // No update required
    }

    override fun updateOnStateChanges(onState: GameStateObserver) {
        observers += onState
    }

    private fun update() {
        observers.forEach { it(gameState) }
    }
}

fun Position.getTargetPosition(offset: Offset, squareSize: Int): Position? {
    fun Float.addDirectionalHalf() = if (this > 0) this + 0.499 else this - 0.499

    val addFiles = (offset.x / squareSize).addDirectionalHalf().toInt()
    val addRanks = -(offset.y / squareSize).addDirectionalHalf().toInt()
    val newFileOrdinal = file.ordinal + addFiles
    val newRankOrdinal = rank.ordinal + addRanks

    if (newFileOrdinal < 0 || newFileOrdinal > 7 || newRankOrdinal < 0 || newRankOrdinal > 7) {
        return null
    }

    val newFile = File.values()[newFileOrdinal]
    val newRank = Rank.values()[newRankOrdinal]
    return Position.fromFileAndRank(newFile, newRank)
}

fun GameState.applyMove(move: Move): GameState {
    if (move.from == move.to) return copy(activePosition = null)

    val piece = piecesByPosition[move.from] ?: error("No piece on position ${move.from}")
    val capturedPiece = piecesByPosition[move.to]

    val boardMove = BoardMove(
        piece = piece,
        from = move.from,
        to = move.to,
        capturedPiece = capturedPiece,
    )

    val newPiecesByPosition = piecesByPosition
        .minus(move.from)
        .minus(move.from)
        .plus(move.to to piece)

    return copy(
        activePosition = null,
        boardSnapshots = boardSnapshots.dropLast(1) + BoardSnapshot(
            piecesByPosition = piecesByPosition,
            setToPlay = setToPlay,
            move = boardMove,
        ) + BoardSnapshot(
            piecesByPosition = newPiecesByPosition,
            setToPlay = setToPlay.opposite,
            move = null,
        ),
    )
}


