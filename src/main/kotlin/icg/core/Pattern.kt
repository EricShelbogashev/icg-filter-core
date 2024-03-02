package icg.core

import java.awt.Point

/**
 * Шаблон матрицы фильтра.
 *
 * @param start Точка начала подматрицы. Не должна быть больше (0,0).
 * @param end Точка конца подматрицы. Не должна быть меньше (0,0).
 */
data class Pattern(
    val start: Point,
    val end: Point,
)