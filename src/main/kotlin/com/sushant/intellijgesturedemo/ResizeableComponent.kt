package com.sushant.intellijgesturedemo

import java.awt.Point
import javax.swing.JPanel
import javax.swing.SwingWorker

abstract class ResizeableComponent : SwingWorker<Unit, JPanel>() {
    abstract val panel: JPanel

    open val isVisible: Boolean
        get() = panel.isVisible

    abstract fun rescaleAndPosition(pos: Point, scale: Double)

    open fun toggleVisible() {
        panel.isVisible = !panel.isVisible
    }
}