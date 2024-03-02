import icg.core.MatrixViewFactory
import java.awt.Color
import kotlin.test.Test
import kotlin.test.assertEquals

class MatrixViewFactoryTest : CommonTest() {
    @Test
    fun getRow() {
        val image = createImage(123, 1, growingGenerator())
        val factory = MatrixViewFactory(image, symmetricPattern(1))
        val row = factory.row(0)
        var counter = 0
        row.forEach {
            assertEquals(counter++, Color(it[0, 0]).blue)
        }
    }

    @Test
    fun getColumn() {
        val image = createImage(1, 123, growingGenerator())
        val factory = MatrixViewFactory(image, symmetricPattern(1))
        val row = factory.column(0)
        var counter = 0
        row.forEach {
            assertEquals(counter++, Color(it[0, 0]).blue)
        }
    }
}