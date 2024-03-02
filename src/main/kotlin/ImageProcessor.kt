import java.awt.image.BufferedImage

class ImageProcessor(private val image: BufferedImage) {
    /**
     * Применяет заданный фильтр ко всему изображению и возвращает новое изображение с примененным фильтром.
     *
     * @return Обработанное изображение.
     */
    fun apply(filter: ICGFilter): BufferedImage {
        val resultImage = BufferedImage(image.width, image.height, image.type)
        val factory = MatrixViewFactory(image, filter.pattern)
        factory.rows().forEach { row -> Thread.startVirtualThread { row.forEach { cell -> filter.apply(cell) } } }
        return resultImage
    }
}