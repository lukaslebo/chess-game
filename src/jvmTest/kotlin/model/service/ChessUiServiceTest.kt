package model.service

import androidx.compose.ui.geometry.Offset
import model.board.Position
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class ChessUiServiceTest {

    @ParameterizedTest(name = "Moving ({1},{2}) from {0} should result in {3}")
    @CsvSource(
        value = [
            "d4,0,0,d4",
            "d4,5,0,d4",
            "d4,-5,0,d4",
            "d4,6,0,e4",
            "d4,-6,0,c4",
            "d4,0,5,d4",
            "d4,0,-5,d4",
            "d4,0,6,d3",
            "d4,0,-6,d5",
            "d4,15,0,e4",
            "d4,16,0,f4",
            "d4,-15,0,c4",
            "d4,-16,0,b4",
            "d4,0,15,d3",
            "d4,0,16,d2",
            "d4,0,-15,d5",
            "d4,0,-16,d6",
            "d4,-16,-16,b6",
            "d4,16,16,f2",
            "a1,-6,0,null",
            "a1,0,6,null",
            "h8,6,0,null",
            "h8,0,-6,null",
        ], nullValues = ["null"]
    )
    fun `getTargetPosition should calculate correct target`(
        from: Position,
        x: Float,
        y: Float,
        expectedTarget: Position?,
    ) {
        val target = from.getTargetPosition(Offset(x, y), 10)
        assertEquals(expectedTarget, target)
    }
}
