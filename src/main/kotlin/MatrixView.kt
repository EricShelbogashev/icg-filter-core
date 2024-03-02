import java.awt.Point
import java.awt.image.BufferedImage
import java.util.*

/**
 * Предоставляет обертку для безопасного доступа к части изображения,
 * определенной начальной и конечной точками. Это позволяет работать с подматрицей
 * изображения как с независимым объектом.
 *
 * Предполагается, что опорная точка в координатах (0,0).
 *
 * @param image Исходное изображение, к которому предоставляется доступ.
 * @param pattern Шаблон матрицы фильтра.
 * @param pivot Опорная точка отображения. Должна быть действительной точкой изображения.
 */
class MatrixView(
    private val image: BufferedImage,
    private val pattern: Pattern,
    private val pivot: Point,
) {
    val rowRange = IntRange(pattern.start.x, pattern.end.x)
    val columnRange = IntRange(pattern.start.y, pattern.end.y)

    /**
     * Возвращает RGB значение пикселя в заданных координатах.
     *
     * @param x Координата X относительно начальной точки подматрицы.
     * @param y Координата Y относительно начальной точки подматрицы.
     * @return RGB значение пикселя.
     * @throws IndexOutOfBoundsException если указанные координаты выходят за границы подматрицы.
     */
    operator fun get(x: Int, y: Int): Int = safeAccess(x, y).orElseThrow {
        IndexOutOfBoundsException(
            "absolute x or y is out of the image bounds: x=$x, y=$y, width=${image.width}, height=${image.height}, pivot=(${pivot.x},${pivot.y})"
        )
    }

    /**
     * Возвращает RGB значение пикселя в заданных координатах или значение по умолчанию,
     * если указанные координаты выходят за границы подматрицы.
     *
     * @param x Координата X относительно начальной точки подматрицы.
     * @param y Координата Y относительно начальной точки подматрицы.
     * @param defaultVal Значение по умолчанию.
     * @return RGB значение пикселя или значение по умолчанию.
     */
    fun orElseDefault(x: Int, y: Int, defaultVal: Int): Int {
        return safeAccess(x, y).orElse(defaultVal)
    }

    private fun safeAccess(x: Int, y: Int): OptionalInt {
        if (!isWithinBounds(x, y)) return OptionalInt.empty()

        val absoluteX = pivot.x + x
        val absoluteY = pivot.y + y

        return if ((absoluteX >= 0 && absoluteX < image.width && absoluteY >= 0 && absoluteY < image.height)) {
            OptionalInt.of(image.getRGB(absoluteX, absoluteY))
        } else {
            OptionalInt.empty()
        }
    }

    private fun isWithinBounds(x: Int, y: Int): Boolean {
        return x in pattern.start.x..pattern.end.x && y in pattern.start.y..pattern.end.y
    }

    override fun toString(): String {
        val sb = StringBuilder()
        println(pivot)
        for (y in pattern.start.y..pattern.end.y) {
            for (x in pattern.start.x..pattern.end.x) {
                val rgb = orElseDefault(x, y, 0)
                sb.append(String.format("%08X ", rgb))
            }
            sb.append("\n")
        }
        return sb.toString()
    }

}