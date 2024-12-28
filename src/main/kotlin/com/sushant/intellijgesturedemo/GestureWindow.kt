package com.sushant.intellijgesturedemo

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.SwingWorker

class GestureWindow : DumbAwareAction() {
    companion object {
        const val WINDOW_WIDTH = 800
        const val WINDOW_HEIGHT = 600
        const val MINIMUM_SIZE = 0.25
        const val MAXIMUM_SIZE = 1.0
        const val SCALE_FACTOR = 500.0
    }

    private val frame = JFrame("Gesture Window")
    private var enteredAt = Point()
    private var lastPos = Point()
    private var currentScale = 1.0

    private var currentComponent: ResizeableComponent = SimplePanel(frame)

    init {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT)
        frame.layout = null

        // Simulate creating a more complicated panel in background
        SwingUtilities.invokeLater {
            val complexPanel = ComplexPanel(frame)
            complexPanel.execute()
            complexPanel.addPropertyChangeListener {
                e -> if (e.propertyName == "state" && e.newValue == SwingWorker.StateValue.DONE) {
                    swapComponent(complexPanel)
                }
            }
        }

        frame.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent) {
                enteredAt = e.point
                lastPos = e.point
                currentScale = MINIMUM_SIZE
                currentComponent.toggleVisible()
            }

            override fun mouseExited(e: MouseEvent?) {
                currentComponent.toggleVisible()
            }
        })

        frame.addMouseMotionListener(object : MouseAdapter() {
            override fun mouseMoved(e: MouseEvent) {
                if (!currentComponent.isVisible) return

                val deltaChange = e.point.distance(lastPos)
                if (deltaChange != 0.0) {
                    val scaleChange = deltaChange / SCALE_FACTOR
                    // If moves back towards entered out, scale image down
                    currentScale += if (enteredAt.distance(e.point) < enteredAt.distance(lastPos)) {
                        -scaleChange
                    } else {
                        scaleChange
                    }
                    currentScale = currentScale.coerceIn(MINIMUM_SIZE, MAXIMUM_SIZE)
                    currentComponent.rescaleAndPosition(e.point, currentScale)
                }

                lastPos = e.point
            }
        })
    }

    /* Swap the current component with the new component */
    private fun swapComponent(newComponent: ResizeableComponent) {
        frame.remove(currentComponent.panel)
        val wasVisible = currentComponent.isVisible
        currentComponent = newComponent
        frame.add(newComponent.panel)
        frame.repaint()
        if (currentComponent.isVisible != wasVisible)
            currentComponent.toggleVisible()
    }

    override fun actionPerformed(e: AnActionEvent) {
        frame.isVisible = !frame.isVisible
    }
}
