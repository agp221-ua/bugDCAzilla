package com.example.bugDCAzilla.managers

import com.example.bugDCAzilla.Main
import java.awt.BorderLayout
import java.awt.Font
import java.awt.GridLayout
import java.io.InputStream
import javax.swing.*

class MainManager {
    companion object {
        val mainManager = MainManager()
    }

    fun show() {
        createMainWindow()
    }

    private lateinit var userLabel: JLabel
    fun createMainWindow(): JPanel{
        val frame = JPanel()
        frame.setSize(1600, 1400)
        frame.layout = BorderLayout()

        // Panel superior con el texto "Anónimo"
        userLabel = JLabel()
        updateUsername()
        userLabel.horizontalAlignment = SwingConstants.RIGHT
        userLabel.font = Font("Arial", Font.BOLD, 16)
        userLabel.border = BorderFactory.createEmptyBorder(10, 0, 10, 20)
        frame.add(userLabel, BorderLayout.NORTH)

        // Panel central con los 4 botones
        val buttonsPanel = JPanel()
        buttonsPanel.layout = GridLayout(1, 4, 10, 4)

        var buttonPanel = JPanel()
        buttonPanel.layout = GridLayout(2, 1, 10, 10)
        val newBugButton = createImageButton("/addBug.png")
        newBugButton.addActionListener { Main.show(NewBugManager.newBugManager.createNewBugWindow()) }
        buttonPanel.add(newBugButton)
        val newBugLabel = JLabel("Nuevo Bug")
        newBugLabel.horizontalAlignment = SwingConstants.CENTER
        newBugLabel.verticalAlignment = SwingConstants.TOP
        newBugLabel.font = Font("Arial", Font.BOLD, 30)
        buttonPanel.add(newBugLabel)
        buttonsPanel.add(buttonPanel)

        buttonPanel = JPanel()
        buttonPanel.layout = GridLayout(2, 1, 10, 10)
        val searchButton = createImageButton("/search.png")
        searchButton.addActionListener { Main.show(SearchManager.searchManager.createSearchManagerWindow()) }
        buttonPanel.add(searchButton)
        val searchLabel = JLabel("Buscar")
        searchLabel.horizontalAlignment = SwingConstants.CENTER
        searchLabel.verticalAlignment = SwingConstants.TOP
        searchLabel.font = Font("Arial", Font.BOLD, 30)
        buttonPanel.add(searchLabel)
        buttonsPanel.add(buttonPanel)

        buttonPanel = JPanel()
        buttonPanel.layout = GridLayout(2, 1, 10, 10)
        val loginButton = createImageButton("/login.png")
        loginButton.addActionListener { LoginManager.loginManager.show() }
        buttonPanel.add(loginButton)
        val loginLabel = JLabel("Iniciar Sesión")
        loginLabel.horizontalAlignment = SwingConstants.CENTER
        loginLabel.verticalAlignment = SwingConstants.TOP
        loginLabel.font = Font("Arial", Font.BOLD, 30)
        buttonPanel.add(loginLabel)
        buttonsPanel.add(buttonPanel)

        buttonPanel = JPanel()
        buttonPanel.layout = GridLayout(2, 1, 10, 10)
        val documentationButton = createImageButton("/documentation.png")
        documentationButton.addActionListener { Main.show(DocumentationManager.documentationManager.createDocumentationWindow()) }
        buttonPanel.add(documentationButton)
        val documentationLabel = JLabel("Documentación")
        documentationLabel.horizontalAlignment = SwingConstants.CENTER
        documentationLabel.verticalAlignment = SwingConstants.TOP
        documentationLabel.font = Font("Arial", Font.BOLD, 30)
        buttonPanel.add(documentationLabel)
        buttonsPanel.add(buttonPanel)

        frame.add(buttonsPanel, BorderLayout.CENTER)
        return frame
    }

    private fun createImageButton(imagePath: String): JButton {
        val button = JButton()
        val imageStream: InputStream? = this.javaClass.getResourceAsStream(imagePath)

        if (imageStream != null) {
            val image = ImageIcon(imageStream.readAllBytes())
            button.icon = image
            button.isBorderPainted = false
            button.isContentAreaFilled = false
        } else {
            // Manejar el caso en el que la imagen no se encuentra
            println("No se pudo cargar la imagen desde: $imagePath")
        }

        return button
    }

    fun updateUsername() {
        userLabel.text = UsersManager.usersManager.currentUser?.name ?: "Anónimo"
    }
}