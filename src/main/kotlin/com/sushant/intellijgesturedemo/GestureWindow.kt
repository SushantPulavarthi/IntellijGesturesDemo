package com.sushant.intellijgesturedemo

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import java.awt.Dimension
import java.awt.Point
import java.awt.Toolkit
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel


class GestureWindow : DumbAwareAction() {
    private val frame = JFrame("Gesture Window")

    init {
        println("Creating Window")
        frame.setSize(800, 600)
        frame.layout = null

        val imageIcon = ImageIcon(javaClass.getResource("/jetbrains.png"))
        val label = JLabel()
        label.icon = imageIcon
        label.size = Dimension(imageIcon.iconWidth, imageIcon.iconHeight)
        label.location = Point(
            (frame.width - imageIcon.iconWidth) / 2,
            (frame.height - imageIcon.iconHeight) / 2
        )
        frame.add(label)

        frame.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent) {
                label.isVisible = true
            }

            override fun mouseExited(e: MouseEvent?) {
                label.isVisible = false
            }
        })

        frame.addMouseMotionListener(object : MouseAdapter() {
            override fun mouseMoved(e: MouseEvent) {
                label.location = Point(
                    e.x - imageIcon.iconWidth / 2,
                    e.y - imageIcon.iconHeight / 2
                )
            }
        })
    }

    override fun actionPerformed(e: AnActionEvent) {
        frame.isVisible = !frame.isVisible
    }
}
