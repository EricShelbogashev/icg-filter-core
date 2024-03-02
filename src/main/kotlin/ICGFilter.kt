abstract class ICGFilter(val pattern: Pattern) {
    abstract fun apply(matrixView: MatrixView): Int
}