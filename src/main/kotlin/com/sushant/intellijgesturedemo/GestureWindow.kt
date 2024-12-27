package com.sushant.intellijgesturedemo

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import java.awt.Dimension
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel

class GestureWindow : DumbAwareAction() {
    companion object {
        const val WINDOW_WIDTH = 800
        const val WINDOW_HEIGHT = 600
        const val MINIMUM_SIZE = 0.25
        const val MAXIMUM_SIZE = 1.0
        const val SCALE_FACTOR = 500.0
    }

    private val frame = JFrame("Gesture Window")
    private val imageIcon = ImageIcon(javaClass.getResource("/jetbrains.png"))
    private val label = JLabel()
    private var enteredAt = Point()
    private var lastPos = Point()
    private var currentScale = 1.0

    init {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT)
        frame.layout = null

        label.icon = imageIcon
        label.size = Dimension(imageIcon.iconWidth, imageIcon.iconHeight)
        label.location = Point(
            (frame.width - imageIcon.iconWidth) / 2,
            (frame.height - imageIcon.iconHeight) / 2
        )
        frame.add(label)

        frame.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent) {
                enteredAt = e.point
                lastPos = e.point
                currentScale = MINIMUM_SIZE
                scaleAndPositionImage(e.point)
                label.isVisible = true
            }

            override fun mouseExited(e: MouseEvent?) {
                label.isVisible = false
            }
        })

        frame.addMouseMotionListener(object : MouseAdapter() {
            override fun mouseMoved(e: MouseEvent) {
                if (!label.isVisible) return

                val deltaChange = e.point.distance(lastPos)
                if (deltaChange != 0.0) {
                    println(deltaChange)
                    val scaleChange = deltaChange / SCALE_FACTOR
                    // If moves back towards entered out, scale image down
                    currentScale += if (enteredAt.distance(e.point) < enteredAt.distance(lastPos)) {
                        -scaleChange
                    } else {
                        scaleChange
                    }
                    println(currentScale)
                    currentScale = currentScale.coerceIn(MINIMUM_SIZE, MAXIMUM_SIZE)
                    scaleAndPositionImage(e.point)
                }

                lastPos = e.point
            }
        })
    }

    override fun actionPerformed(e: AnActionEvent) {
        frame.isVisible = !frame.isVisible
    }

    fun scaleAndPositionImage(pos: Point) {
        // Scale Image
        val imageWidth = imageIcon.iconWidth
        val imageHeight = imageIcon.iconHeight
        label.icon = ImageIcon(
            imageIcon.image.getScaledInstance(
                (imageWidth * currentScale).toInt(),
                (imageHeight * currentScale).toInt(),
                java.awt.Image.SCALE_SMOOTH
            )
        )
        label.size = Dimension(label.icon.iconWidth, label.icon.iconHeight)

        // Put image at centre of cursor
        label.location = Point(
            pos.x - label.width / 2,
            pos.y - label.height / 2
        )
    }
}
