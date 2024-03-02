import icg.core.ICGFilter
import icg.core.ImageProcessor
import icg.core.MatrixView
import org.junit.jupiter.api.Test
import java.awt.Color
import kotlin.test.assertEquals

class ImageProcessorTest : CommonTest() {

    @Test
    fun apply_rgbValueGrowingImage_addedNumberOneToTheBlueComponent() {
        val width = 1
        val height = 25
        val image = createImage(width, height, growingGenerator())
        val processor = ImageProcessor(image)
        val filter = object : ICGFilter(symmetricPattern(1)) {
            override fun apply(matrixView: MatrixView): Int = matrixView[0, 0] + 256
        }
        val filteredImage = processor.apply(filter)
        var counter = 0
        for (h in 0 until filteredImage.height) {
            for (w in 0 until filteredImage.width) {
                val color = Color(filteredImage.getRGB(w, h))
                assertEquals(1, color.green)
                assertEquals(counter++, color.blue)
            }
        }
    }
}