package com.example.bugDCAzilla.managers

import com.example.bugDCAzilla.Main
import java.awt.BorderLayout
import java.awt.Font
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane

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

        // Panel para el texto central
        val textPanel = JPanel()
        val textLabel = JLabel(
            "<html>" +
                    "   <h1>Documentación de BugDCAzilla</h1>\n" +
                    "\n" +
                    "    <h2>Introducción</h2>\n" +
                    "    <p>BugDCAzilla es una herramienta de seguimiento de problemas que permite gestionar y rastrear los errores y problemas en tu proyecto de desarrollo.</p>\n" +
                    "\n" +
                    "    <h2>Inicio de Sesión</h2>\n" +
                    "    <p>Para comenzar a utilizar BugDCAzilla, sigue estos pasos:</p>\n" +
                    "    <ol>\n" +
                    "        <li>Accede a la página de inicio de sesión / registro.</li>\n" +
                    "        <li>Ingresa tu nombre de usuario y contraseña.</li>\n" +
                    "        <li>Haz clic en el botón \"Iniciar sesión\" o \"Registrarse\" si no tienes cuenta.</li>\n" +
                    "    </ol>\n" +
                    "\n" +
                    "    <h2>Creación de un Nuevo Bug</h2>\n" +
                    "    <p>Para registrar un nuevo error o problema en BugDCAzilla:</p>\n" +
                    "    <ol>\n" +
                    "        <li>Inicia sesión en tu cuenta.</li>\n" +
                    "        <li>Dirígete a la sección \"Crear Nuevo Bug\".</li>\n" +
                    "        <li>Rellena los detalles del bug, incluyendo el título, la descripción y las etiquetas.</li>\n" +
                    "        <li>Guarda el bug.</li>\n" +
                    "    </ol>\n" +
                    "\n" +
                    "    <h2>Búsqueda y Filtros</h2>\n" +
                    "    <p>Utiliza las funciones de búsqueda y filtros de BugDCAzilla para encontrar bugs específicos o realizar un seguimiento de los problemas que te interesan.</p>\n" +
                    "    <ul>\n" +
                    "        <li>Utiliza palabras clave en la barra de búsqueda para buscar bugs por título o descripción.</li>\n" +
                    "        <li>Puedes buscar solo por etiquetas empezando la busqueda por `:`, por ejemplo, para buscar por la etiqueta `dca`, tendrias que buscar por `:dca`.</li>\n" +
                    "    </ul>\n" +
                    "\n" +
                    "    <h2>Colaboración</h2>\n" +
                    "    <p>BugDCAzilla permite a varios usuarios colaborar en la resolución de problemas. Puedes asignar bugs, comentar en ellos y realizar un seguimiento del progreso de cada problema.</p>\n" +
                    "\n" +
                    "    <h2>Informes y Estadísticas</h2>\n" +
                    "    <p>Genera informes y estadísticas sobre el estado de los bugs en tu proyecto. Utiliza estas métricas para evaluar el progreso y la calidad del software.</p>\n" +
                    "\n" +
                    "    <h2>Soporte Técnico</h2>\n" +
                    "    <p>Si tienes alguna pregunta o problema al usar BugDCAzilla, no dudes en ponerte en contacto con nuestro equipo de soporte técnico en <a href=\"mailto:soporte@bugdcazilla.com\">soporte@bugdcazilla.com</a>.</p>\n"+
                    "</html>"
        )
        textLabel.font = Font("Arial", Font.PLAIN, 16)
        textPanel.add(textLabel)

        // Agregar los componentes al panel principal
        panel.add(textPanel, BorderLayout.NORTH)
        panel.add(backButton, BorderLayout.SOUTH)
        return panel
    }

    companion object {
        val documentationManager: DocumentationManager = DocumentationManager()
    }

}
