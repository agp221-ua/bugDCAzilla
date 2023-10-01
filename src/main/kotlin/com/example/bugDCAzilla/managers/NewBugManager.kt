package com.example.bugDCAzilla.managers

import com.example.bugDCAzilla.Main
import com.example.bugDCAzilla.dataObject.Bug
import com.example.bugDCAzilla.dataObject.State
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.swing.*

class NewBugManager {

    fun createNewBugWindow(): JPanel {
        val panel = JPanel()
        val layout = GridBagLayout()
        panel.layout = layout
        val gbc = GridBagConstraints()
        gbc.fill = GridBagConstraints.HORIZONTAL
        gbc.insets = Insets(5, 5, 5, 5)

        gbc.gridx = 0
        gbc.gridy = 0
        val titleLabel = JLabel("Título:")
        titleLabel.font = Font("Arial", Font.BOLD, 45)
        panel.add(titleLabel, gbc)

        gbc.gridx = 1
        gbc.gridwidth = 2
        val titleField = JTextField(30)
        titleField.font = Font("Arial", Font.BOLD, 35)
        panel.add(titleField, gbc)

        gbc.gridx = 0
        gbc.gridy = 1
        gbc.gridwidth = 1
        val descriptionLabel = JLabel("Descripción:   ")
        descriptionLabel.font = Font("Arial", Font.BOLD, 45)
        panel.add(descriptionLabel, gbc)

        gbc.gridx = 1
        gbc.gridwidth = 2
        val descriptionField = JTextArea(8, 30)
        descriptionField.font = Font("Arial", Font.BOLD, 28)
        val scrollPane = JScrollPane(descriptionField)
        scrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS

        panel.add(scrollPane, gbc)

        gbc.gridx = 0
        gbc.gridy = 2
        gbc.gridwidth = 1
        val labelsLabel = JLabel("Etiquetas:")
        labelsLabel.font = Font("Arial", Font.BOLD, 45)
        panel.add(labelsLabel, gbc)

        gbc.gridx = 1
        val labelsField = JTextField(20)
        labelsField.font = Font("Arial", Font.BOLD, 30)
        panel.add(labelsField, gbc)

        gbc.gridx = 2
        val addLabelButton = JButton("Añadir")
        addLabelButton.font = Font("Arial", Font.BOLD, 30)

        panel.add(addLabelButton, gbc)

        gbc.gridx = 1
        gbc.gridy = 3
        val labelsLabelLabel = JLabel("<html><u>ETIQUETAS</u></html>")
        labelsLabelLabel.font = Font("Arial", Font.BOLD, 25)
        labelsLabelLabel.horizontalAlignment = SwingConstants.CENTER
        panel.add(labelsLabelLabel, gbc)

        gbc.gridx = 2
        val selectedLabelsLabel = JLabel("<html><u>SELECCIONADAS</u></html>")
        selectedLabelsLabel.font = Font("Arial", Font.BOLD, 25)
        selectedLabelsLabel.horizontalAlignment = SwingConstants.CENTER
        panel.add(selectedLabelsLabel, gbc)

        gbc.gridx = 1
        gbc.gridy = 4
        val labelsListModel = DefaultListModel<String>()
        labelsListModel.addAll(BugsManager.bugsManager.labels)
        val labelsList = JList(labelsListModel)
        labelsList.setSize(100, 100)
        labelsList.font = Font("Arial", Font.BOLD, 20)
        val scrollPane2 = JScrollPane(labelsList)
        panel.add(scrollPane2, gbc)

        gbc.gridx = 2
        val selectedLabelsListModel = DefaultListModel<String>()
        val selectedLabelsList = JList(selectedLabelsListModel)
        selectedLabelsList.setSize(100, 100)
        selectedLabelsList.font = Font("Arial", Font.BOLD, 20)
        val scrollPane3 = JScrollPane(selectedLabelsList)
        panel.add(scrollPane3, gbc)

        labelsList.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 1) {
                    labelsList.selectedValue?.let { selectedLabel ->
                        labelsListModel.removeElement(selectedLabel)
                        selectedLabelsListModel.addElement(selectedLabel)
                    }
                }
            }
        })

        selectedLabelsList.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 1) {
                    selectedLabelsList.selectedValue?.let { selectedLabel ->
                        selectedLabelsListModel.removeElement(selectedLabel)
                        labelsListModel.addElement(selectedLabel)
                    }
                }
            }
        })

        addLabelButton.addActionListener {
            val label = labelsField.text.trim().lowercase()
            if (label.isBlank()) return@addActionListener
            if (labelsListModel.contains(label) ||
                selectedLabelsListModel.contains(label)) return@addActionListener
            selectedLabelsListModel.addElement(label)
        }

        gbc.gridx = 0
        gbc.gridy = 5
        gbc.gridwidth = 3
        val errorText = JLabel()
        errorText.font = Font("Arial", Font.BOLD, 25)
        errorText.foreground = Color.RED
        panel.add(errorText, gbc)

        gbc.gridx = 0
        gbc.gridy = 6
        gbc.gridwidth = 1
        val backButton = JButton("Volver")
        backButton.font = Font("Arial", Font.BOLD, 40)
        backButton.addActionListener { Main.show(MainManager.mainManager.createMainWindow()) }
        panel.add(backButton, gbc)

        gbc.gridx = 1
        gbc.gridwidth = 2
        val addButton = JButton("Añadir Bug")
        addButton.font = Font("Arial", Font.BOLD, 40)
        addButton.addActionListener {
            val title = titleField.text.trim()
            if (title.isBlank()) {
                errorText.text = "ERROR: El título no puede estar vacío"
                return@addActionListener
            }
            if (title.length > 200) {
                errorText.text = "ERROR: El título no puede tener más de 50 caracteres"
                return@addActionListener
            }
            val description = descriptionField.text.trim()
            if (description.isBlank()) {
                errorText.text = "ERROR: La descripción no puede estar vacía"
                return@addActionListener
            }
            val labels = mutableListOf<String>()
            for (i in 0..< selectedLabelsListModel.size()) {
                labels.add(selectedLabelsListModel[i])
            }

            if (UsersManager.usersManager.currentUser == null) {
                LoginManager.loginManager.show()
                return@addActionListener
            }

            BugsManager.bugsManager.addBug(Bug(
                Bug.nextID++,
                UsersManager.usersManager.currentUser!!.name,
                State.OPEN,
                LocalDateTime.now().format(timeFormat),
                title,
                description,
                listOf(),
                labels))

            //TODO cargar pagina del bug
            Main.show(SearchManager.searchManager.createSearchManagerWindow())
        }
        panel.add(addButton, gbc)




        return panel
    }

    companion object {
        val timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
        val newBugManager: NewBugManager = NewBugManager()

    }

}
