package com.example.bugDCAzilla

import com.example.bugDCAzilla.managers.MainManager
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JPanel

class Main {
    companion object {
        val jFrame = JFrame()
        init {
            jFrame.title = "bugDCAzilla"
            jFrame.iconImage = ImageIcon(javaClass.getResource("/icono.png")).image
            jFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        }
        @JvmStatic
        fun main(args: Array<String>) {
            show(MainManager.mainManager.createMainWindow())
        }

        fun show(panel: JPanel){
            show(panel, 1800, 1400)

        }

        fun show(panel: JPanel, height: Int, width: Int){
            jFrame.contentPane.removeAll()
            jFrame.revalidate()
            jFrame.contentPane.add(panel)
            jFrame.setSize(height, width)
            jFrame.extendedState = JFrame.MAXIMIZED_BOTH
            jFrame.setLocationRelativeTo(null)
            jFrame.isVisible = true

        }

    }
}