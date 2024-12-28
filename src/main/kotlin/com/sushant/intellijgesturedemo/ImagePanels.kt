package com.sushant.intellijgesturedemo

import java.awt.Dimension
import java.awt.Point
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel


abstract class BasePanel(frame: JFrame, imagePath: String) : ResizeableComponent() {
    final override val panel = JPanel()
    private val label = JLabel()
    private val imageIcon = ImageIcon(javaClass.getResource(imagePath))

    init {
        panel.size = Dimension(imageIcon.iconWidth, imageIcon.iconHeight)
        panel.location = Point(
            (frame.width - imageIcon.iconWidth) / 2,
            (frame.height - imageIcon.iconHeight) / 2
        )
        panel.layout = null

        label.icon = imageIcon
        label.size = Dimension(imageIcon.iconWidth, imageIcon.iconHeight)
        panel.add(label)
        frame.add(panel)
        panel.isVisible = false
    }

    override fun rescaleAndPosition(pos: Point, scale: Double) {
        // Scale Image
        val imageWidth = imageIcon.iconWidth
        val imageHeight = imageIcon.iconHeight
        label.icon = ImageIcon(
            imageIcon.image.getScaledInstance(
                (imageWidth * scale).toInt(),
                (imageHeight * scale).toInt(),
                java.awt.Image.SCALE_SMOOTH
            )
        )
        panel.size = Dimension(label.icon.iconWidth, label.icon.iconHeight)
        label.size = panel.size

        // Put image at centre of cursor
        panel.location = Point(
            pos.x - panel.width / 2,
            pos.y - panel.height / 2
        )
    }
}

class SimplePanel(frame: JFrame) : BasePanel(frame, "/jetbrains-mono-white.png") {
    override fun doInBackground() {} /* Do nothing */
}

class ComplexPanel(frame: JFrame) : BasePanel(frame, "/jetbrains.png") {
    // Simulate a long-running task
    override fun doInBackground() = Thread.sleep(5000)
}
