import java.awt.Color
import java.awt.Point
import java.awt.image.BufferedImage

abstract class CommonTest {
    protected fun symmetricPattern(radius: Int) = Pattern(Point(-radius, -radius), Point(radius, radius))

    protected fun growingGenerator() = object : () -> Int {
        var counter = 0
        override fun invoke(): Int = counter++
    }

    protected fun createImage(width: Int, height: Int, generator: () -> Int): BufferedImage {
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        for (h in 0 until height) {
            for (w in 0 until width) {
                image.setRGB(w, h, generator.invoke())
            }
        }
        return image;
    }

    protected fun toString(color: Color): String {
        return "[${
            String.format("%03d", color.red)
        } ${
            String.format("%03d", color.green)
        } ${
            String.format("%03d", color.blue)
        }] "
    }
}