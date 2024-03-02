package icg.core

import java.awt.Point
import java.awt.image.BufferedImage
import java.util.stream.IntStream
import java.util.stream.Stream

class MatrixViewFactory(private val image: BufferedImage, private val pattern: Pattern) {

    fun row(y: Int): Stream<MatrixView> {
        require(y in 0 until image.height) {
            "индекс строки выходит за пределы изображения"
        }
        return IntStream.range(0, image.width)
            .mapToObj { x -> MatrixView(image, pattern, Point(x, y)) }
    }

    fun rows(): Stream<Stream<MatrixView>> {
        return IntStream.range(0, image.height).mapToObj(::row)
    }

    fun column(x: Int): Stream<MatrixView> {
        require(x in 0 until image.width) {
            "индекс столбца выходит за пределы изображения"
        }
        return IntStream.range(0, image.height)
            .mapToObj { y -> MatrixView(image, pattern, Point(x, y)) }
    }

    fun columns(): Stream<Stream<MatrixView>> {
        return IntStream.range(0, image.width).mapToObj(::column)
    }
}
