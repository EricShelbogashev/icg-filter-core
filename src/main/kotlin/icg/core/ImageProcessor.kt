package icg.core

import java.awt.Point
import java.awt.image.BufferedImage
import java.util.stream.Stream

class ImageProcessor(private val image: BufferedImage) {
    /**
     * Применяет заданный фильтр ко всему изображению и возвращает новое изображение с примененным фильтром.
     *
     * @return Обработанное изображение.
     */
    fun apply(filter: ICGFilter): BufferedImage {
        val resultImage = BufferedImage(image.width, image.height, image.type)
        val factory = MatrixViewFactory(image, filter.pattern)
        factory.rows()
            .map { row -> Thread.startVirtualThread { job(resultImage, filter, row) } }
            .forEach(Thread::join)
        return resultImage
    }

    private fun job(resultImage: BufferedImage, filter: ICGFilter, row: Stream<MatrixView>) {
        row.forEach { cell -> apply(resultImage, filter.apply(cell), cell.pivot) }
    }

    private fun apply(resultImage: BufferedImage, color: Int, pivot: Point) {
        resultImage.setRGB(pivot.x, pivot.y, color)
    }
}