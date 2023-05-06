package model.service

import androidx.compose.ui.geometry.Offset
import model.board.*
import model.move.CapturingMove
import model.move.Move
import model.move.Promotion
import model.service.move.pseudoLegalMoves
import model.state.BoardSnapshot
import model.state.GameState
import kotlin.math.max
import kotlin.math.min

interface ChessUiService {
    val gameState: GameState
    fun onClick(position: Position)
    fun onDragStart(position: Position)
    fun onDrag(offset: Offset)
    fun onDragEnd()
    fun applyPromotion(promotion: Promotion)
    fun cancelPromotion()
    fun onSquareSizeChanged(squareSize: Int)
    fun updateOnStateChanges(onState: GameStateObserver)
}

typealias GameStateObserver = (GameState) -> Unit

object DefaultChessUiService : ChessUiService {
    override var gameState = GameState()
    private val observers = HashSet<GameStateObserver>()

    override fun onClick(position: Position) {
        if (gameState.promotionSelection.isNotEmpty()) return
        val activePosition = gameState.activePosition
        val piece = gameState.piecesByPosition[position]
        val move = gameState.legalMoves.find { it.from == gameState.activePosition && it.to == position }

        gameState =
            if (move is Promotion)
                gameState.copy(
                    activePosition = null,
                    legalMoves = emptyList(),
                    promotionSelection = gameState.legalMoves.filterIsInstance<Promotion>().filter { it.to == move.to },
                )
            else if (move != null)
                gameState.applyMove(move)
            else if (activePosition != null) gameState.copy(
                activePosition = null,
                legalMoves = emptyList(),
            )
            else if (piece != null)
                gameState.copy(
                    activePosition = position,
                    legalMoves = legalMoves(piece = piece, gameState = gameState),
                )
            else gameState

        update()
    }

    override fun onDragStart(position: Position) {
        if (gameState.promotionSelection.isNotEmpty()) return

        val squareSize = gameState.uiState.squareSize
        val piece = gameState.piecesByPosition[position] ?: error("Can only drag when piece is on square")
        gameState = gameState.copy(
            activePosition = position,
            legalMoves = legalMoves(piece = piece, gameState = gameState),
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
        if (gameState.promotionSelection.isNotEmpty()) return

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
        if (gameState.promotionSelection.isNotEmpty()) return

        val fromPosition = gameState.activePosition ?: error("Can only drag with active square")
        val toPosition = fromPosition.getTargetPosition(
            offset = gameState.uiState.constrainedPieceDragOffset,
            squareSize = gameState.uiState.squareSize,
        )

        val move = gameState.legalMoves.find { it.from == gameState.activePosition && it.to == toPosition }

        if (move is Promotion) {
            gameState = gameState.copy(
                promotionSelection = gameState.legalMoves.filterIsInstance<Promotion>().filter { it.to == move.to },
            )
        } else if (move != null) {
            gameState = gameState.applyMove(move)
        } else {
            gameState = gameState.copy(
                uiState = gameState.uiState.copy(
                    pieceDragOffset = Offset.Zero,
                    pieceMinDragOffset = Offset.Zero,
                    pieceMaxDragOffset = Offset.Zero,
                    constrainedPieceDragOffset = Offset.Zero,
                ),
            )
        }

        update()
    }

    override fun applyPromotion(promotion: Promotion) {
        gameState = gameState.applyMove(promotion)
        update()
    }

    override fun cancelPromotion() {
        gameState = gameState.copy(
            promotionSelection = emptyList(),
            uiState = gameState.uiState.copy(
                pieceDragOffset = Offset.Zero,
                pieceMinDragOffset = Offset.Zero,
                pieceMaxDragOffset = Offset.Zero,
                constrainedPieceDragOffset = Offset.Zero,
            )
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
    if (move.from == move.to) return copy(
        activePosition = null,
        legalMoves = emptyList(),
    )

    return copy(
        activePosition = null,
        legalMoves = emptyList(),
        boardSnapshots = boardSnapshots.dropLast(1) + BoardSnapshot(
            piecesByPosition = piecesByPosition,
            setToPlay = setToPlay,
            move = move,
        ) + BoardSnapshot(
            piecesByPosition = move.applyOn(piecesByPosition),
            setToPlay = setToPlay.opposite,
            move = null,
        ),
        promotionSelection = emptyList(),
        uiState = uiState.copy(
            pieceDragOffset = Offset.Zero,
            pieceMinDragOffset = Offset.Zero,
            pieceMaxDragOffset = Offset.Zero,
            constrainedPieceDragOffset = Offset.Zero,
        ),
    )
}

private fun legalMoves(piece: Piece, gameState: GameState): List<Move> {
    if (piece.set != gameState.setToPlay) return emptyList()

    val pseudoLegalMoves = piece.pseudoLegalMoves(gameState, true)

    return pseudoLegalMoves.filter { !gameState.isKingInCheck(it) }
}

private fun GameState.isKingInCheck(move: Move): Boolean {
    val nextGameState = applyMove(move)

    val opponentSet = move.piece.set.opposite
    val opponentPieces = nextGameState.piecesByPosition.values.filter { it.set == opponentSet }
    return opponentPieces
        .flatMap { it.pseudoLegalMoves(nextGameState) }
        .filterIsInstance<CapturingMove>()
        .any { it.capturedPiece is King }
}

