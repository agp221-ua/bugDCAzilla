package com.example.bugDCAzilla.managers

import com.example.bugDCAzilla.Main
import java.awt.BorderLayout
import java.awt.Font
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class DocumentationManager {
    fun show() {
        //TODO("Not yet implemented")
    }

    fun createDocumentationWindow(): JPanel {
        val panel = JPanel()
        panel.layout = BorderLayout()

        // Botón para volver al menú anterior
        val backButton = JButton("Volver")
        backButton.addActionListener {
            Main.show(MainManager.mainManager.createMainWindow())
        }

        // Panel para el título "DOCUMENTACION"
        val titlePanel = JPanel()
        val titleLabel = JLabel("DOCUMENTACION")
        titlePanel.add(titleLabel)

        // Panel para el texto central
        val textPanel = JPanel()
        val textLabel = JLabel(
            "<html><div style='text-align: center;'>" +
                    "Bienvenido a la documentación de la aplicación.<br><br>" +
                    "Esta es una descripción de cómo funciona la aplicación.<br><br>" +
                    "Puedes agregar más información aquí.<br><br>" +
                    "</div></html>"
        )
        textLabel.font = Font("Arial", Font.PLAIN, 16)
        textPanel.add(textLabel)

        // Agregar los componentes al panel principal
        panel.add(backButton, BorderLayout.NORTH)
        panel.add(titlePanel, BorderLayout.CENTER)
        panel.add(textPanel, BorderLayout.SOUTH)
        return panel
    }

    companion object {
        val documentationManager: DocumentationManager = DocumentationManager()
    }

}
