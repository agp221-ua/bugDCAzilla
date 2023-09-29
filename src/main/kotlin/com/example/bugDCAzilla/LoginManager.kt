package com.example.bugDCAzilla

import com.example.bugDCAzilla.user.UsersManager
import javax.swing.*
import java.awt.*

class LoginManager {

    companion object{
        val loginManager = LoginManager()
    }

    fun show() {
        SwingUtilities.invokeLater {
            createLoginWindow()
        }
    }

    private fun createLoginWindow() {
        val frame = JFrame("Inicio de Sesión")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.size = Dimension(400, 200)
        frame.isResizable = false
        frame.iconImage = ImageIcon(javaClass.getResource("/icono.png")).image

        val panel = JPanel()
        panel.layout = GridLayout(4, 2, 10, 10)
        panel.border = BorderFactory.createEmptyBorder(20, 20, 20, 20)

        val titleLabel = JLabel("Bienvenido")
        titleLabel.font = Font("Arial", Font.BOLD, 20)
        titleLabel.horizontalAlignment = JLabel.CENTER

        val userLabel = JLabel("Usuario:")
        val passwordLabel = JLabel("Contraseña:")

        val userTextField = JTextField(10)
        val passwordField = JPasswordField(10)

        val loginButton = JButton("Iniciar Sesión")
        loginButton.background = Color(59, 89, 182)
        loginButton.foreground = Color.WHITE

        val registerButton = JButton("Registrarse")
        registerButton.background = Color(255, 165, 0)
        registerButton.foreground = Color.WHITE

        loginButton.addActionListener {
            val username = userTextField.text
            val password = String(passwordField.password)

            if (isValidLogin(username, password)) {
                //TODO cerrar ventana y ir a venana principal
            } else {
                JOptionPane.showMessageDialog(
                    frame,
                    "Inicio de sesión fallido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }

        registerButton.addActionListener {
            val username = userTextField.text
            val password = String(passwordField.password)

            if (isValidRegister(username, password)) {
                //TODO cerrar ventana y ir a venana principal
            } else {
                JOptionPane.showMessageDialog(
                    frame,
                    "Registro fallido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                )
            }

        }

        panel.add(titleLabel)
        panel.add(JLabel("")) // Espacio en blanco
        panel.add(userLabel)
        panel.add(userTextField)
        panel.add(passwordLabel)
        panel.add(passwordField)
        panel.add(loginButton)
        panel.add(registerButton)

        frame.add(panel)
        frame.isVisible = true
    }

    fun isValidLogin(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty() && UsersManager.usersManager.login(username, password)
    }
    fun isValidRegister(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty() && UsersManager.usersManager.register(username, password)
    }

}