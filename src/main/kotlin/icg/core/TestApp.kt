import icg.core.ICGFilter
import icg.core.ImageProcessor
import icg.core.MatrixView
import icg.core.Pattern
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Point
import java.awt.event.ActionEvent
import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter

class TestApp : JFrame("Image Filter Application") {
    private val imageLabel: JLabel
    private var originalImage: BufferedImage? = null

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(800, 600)
        maximumSize = Dimension(1000, 1000)
        setLocationRelativeTo(null)

        // Кнопка для выбора изображения
        val chooseButton = JButton("choose image")
        chooseButton.addActionListener { e: ActionEvent? -> chooseImage() }

        // Кнопка для применения фильтра
        val filterButton = JButton("apply filter")
        filterButton.addActionListener { e: ActionEvent? -> applyFilter() }

        // Метка для отображения изображения
        imageLabel = JLabel()
        imageLabel.horizontalAlignment = JLabel.CENTER

        // Панель для кнопок
        val buttonPanel = JPanel()
        buttonPanel.add(chooseButton)
        buttonPanel.add(filterButton)

        // Добавление компонентов в фрейм
        add(buttonPanel, BorderLayout.SOUTH)
        add(JScrollPane(imageLabel), BorderLayout.CENTER)
    }

    private fun chooseImage() {
        val fileChooser = JFileChooser()
        fileChooser.fileFilter = FileNameExtensionFilter("image files", *ImageIO.getReaderFileSuffixes())
        val result = fileChooser.showOpenDialog(this)
        if (result == JFileChooser.APPROVE_OPTION) {
            val selectedFile = fileChooser.selectedFile
            try {
                originalImage = ImageIO.read(selectedFile)
                imageLabel.icon = ImageIcon(originalImage)
                this.pack() // Автоматически подгоняет размер окна под размер изображения
            } catch (ex: IOException) {
                JOptionPane.showMessageDialog(this, "error loading the image: " + ex.message)
            }
        }
    }

    private fun applyFilter() {
        if (originalImage != null) {
            val processor = ImageProcessor(originalImage!!)
            val image = processor.apply(
                object : ICGFilter(Pattern(Point(-1, -1), Point(1, 1))) {
                    override fun apply(matrixView: MatrixView): Int {
                        val res = (
                                +matrixView[-1, 0]
                                        + matrixView[1, 0]
                                        + matrixView[0, -1]
                                        + matrixView[0, 1]
                                ) / 4 / 25
                        return res * 10
                    }
                }
            )
            originalImage = image
            imageLabel.icon = ImageIcon(image)
            this.pack()
        } else {
            JOptionPane.showMessageDialog(this, "please choose an image first")
        }
    }
}
