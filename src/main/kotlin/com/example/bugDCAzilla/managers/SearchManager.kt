package com.example.bugDCAzilla.managers

import com.example.bugDCAzilla.Main
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.Toolkit
import javax.swing.*
import javax.swing.table.DefaultTableModel


class SearchManager {
    fun createSearchManagerWindow() : JPanel {
        return createSearchManagerWindow("")
    }
    fun createSearchManagerWindow(search: String) : JPanel {
        val panel = JPanel()
        val layout = GridBagLayout()
        panel.layout = layout
        val gbc = GridBagConstraints()
        gbc.fill = GridBagConstraints.BOTH
        gbc.weightx = 1.0
        gbc.weighty = 1.0
        gbc.insets = Insets(5, 5, 5, 5)

        gbc.gridx = 0
        gbc.gridy = 0
        gbc.weighty = 0.0
        val backButton = JButton("Volver")
        backButton.addActionListener { Main.show(MainManager.mainManager.createMainWindow()) }
        panel.add(backButton, gbc)

        gbc.gridx = 1
        val searchField = JTextField(60)
        searchField.text = search
        searchField.font = Font("Arial", Font.BOLD, 18)
        panel.add(searchField, gbc)

        gbc.gridx = 2
        val searchButton = JButton("Buscar")
        searchButton.addActionListener {
            val searchTerm = searchField.text.trim()
            Main.show(createSearchManagerWindow(searchTerm))
        }
        panel.add(searchButton, gbc)

        gbc.gridx = 0
        gbc.gridy = 1
        gbc.gridwidth = 3
        gbc.weighty = 1.0
        val columnNames = arrayOf("ID", "Fecha", "Título", "Labels")
        val data = BugsManager.bugsManager.bugsArray(search)
        val model = DefaultTableModel(data, columnNames)
        val table = JTable(model)
        table.setDefaultEditor(Object::class.java, null)

        setJTableColumnsWidth(table, Toolkit.getDefaultToolkit().screenSize.width, 5.toDouble() ,8.toDouble(), 67.toDouble(), 20.toDouble())

        panel.add(JScrollPane(table), gbc)

        table.selectionModel.selectionMode = ListSelectionModel.SINGLE_SELECTION
        table.selectionModel.addListSelectionListener {
            if (it.valueIsAdjusting) return@addListSelectionListener
            if (table.selectedRow != -1) {
                val bugID = table.getValueAt(table.selectedRow, 0) as Int
                Main.show(BugManager.bugManager.createBugManagerWindow(bugID))
            }
        }

        return panel

    }

    companion object {
        val searchManager: SearchManager = SearchManager()
    }

    fun setJTableColumnsWidth(
        table: JTable, tablePreferredWidth: Int,
        vararg percentages: Double
    ) {
        var total = 0.0
        for (i in 0..<table.columnModel.columnCount) {
            total += percentages[i]
        }
        for (i in 0..<table.columnModel.columnCount) {
            val column = table.columnModel.getColumn(i)
            column.setPreferredWidth((tablePreferredWidth * (percentages[i] / total)).toInt())
        }
    }

}
