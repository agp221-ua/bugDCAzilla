package com.example.bugDCAzilla.managers

import com.example.bugDCAzilla.Main
import com.example.bugDCAzilla.dataObject.State
import com.example.bugDCAzilla.dataObject.User
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class BugManager {

    companion object {
        val bugManager: BugManager = BugManager()
    }
    fun createBugManagerWindow(bugID: Int): JPanel {
        val panel = JPanel()
        val layout = GridBagLayout()
        panel.layout = layout
        val gbc = GridBagConstraints()
        gbc.fill = GridBagConstraints.BOTH
        gbc.weightx = 1.0
        gbc.weighty = 0.0
        gbc.insets = Insets(5, 5, 5, 5)

        val bug = BugsManager.bugsManager.bugs[bugID] ?: return SearchManager.searchManager.createSearchManagerWindow()

        gbc.gridx = 0
        gbc.gridy = 0
        gbc.gridwidth = 1
        val backButton = JButton("Volver")
        backButton.font = Font("Arial", Font.BOLD, 18)
        backButton.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 1) {
                    Main.show(SearchManager.searchManager.createSearchManagerWindow())
                }
            }
        })
        panel.add(backButton, gbc)

        if(UsersManager.usersManager.currentUser != null && UsersManager.usersManager.currentUser!!.admin){
            gbc.gridx = 0
            gbc.gridy = 1
            val leftLabel = JLabel("${if (bug.state == State.OPEN) "[OP]" else "[CL]"} ${bug.id} - ${bug.author}")
            leftLabel.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    if (e.clickCount == 1) {
                        if (bug.state == State.OPEN) {
                            bug.state = State.CLOSED
                            leftLabel.text = "[CL]"
                        } else {
                            bug.state = State.OPEN
                            leftLabel.text = "[OP]"
                        }
                        BugsManager.bugsManager.saveBug(bug)
                    }
                }
            })
            leftLabel.font = Font("Arial", Font.BOLD, 25)
            panel.add(leftLabel, gbc)
        } else {
            gbc.gridx = 0
            gbc.gridy = 1
            val leftLabel = JLabel("${if (bug.state != State.OPEN) "[CL]" else ""} ${bug.id} - ${bug.author}")
            leftLabel.font = Font("Arial", Font.BOLD, 25)
            panel.add(leftLabel, gbc)
        }
        gbc.gridy = 1
        gbc.gridwidth = 1
        gbc.anchor = GridBagConstraints.EAST
        val rightLabel = JLabel(bug.date)
        rightLabel.horizontalAlignment = SwingConstants.RIGHT
        rightLabel.font = Font("Arial", Font.BOLD, 25)
        panel.add(rightLabel, gbc)

        gbc.gridx = 0
        gbc.gridy = 2
        gbc.gridwidth = 1
        panel.add(Box.createRigidArea(Dimension(10, 30)), gbc)

        gbc.gridx = 0
        gbc.gridy = 3
        gbc.gridwidth = 1
        gbc.anchor = GridBagConstraints.WEST
        val titleLabel = JLabel(bug.title)
        titleLabel.font = Font("Arial", Font.BOLD, 40)
        panel.add(titleLabel, gbc)

        gbc.gridx = 0
        gbc.gridy = 4
        gbc.gridwidth = 1
        panel.add(Box.createRigidArea(Dimension(10, 30)), gbc)

        gbc.gridx = 0
        gbc.gridy = 5
        gbc.gridwidth = 1
        //gbc.weighty = 1.0
        val descriptionLabel = JLabel("<html>${bug.description.replace("\n", "<br>")}</html>")
        descriptionLabel.font = Font("Arial", Font.BOLD, 20)
        panel.add(descriptionLabel, gbc)



        val res = JPanel()
        res.layout = BorderLayout()
        val scroll  = JScrollPane(panel)
        scroll.minimumSize = Dimension(Toolkit.getDefaultToolkit().screenSize.width, Toolkit.getDefaultToolkit().screenSize.height)
        res.add(scroll, BorderLayout.CENTER)
        res.minimumSize = Dimension(Toolkit.getDefaultToolkit().screenSize.width, Toolkit.getDefaultToolkit().screenSize.height)

        return res


    }



}
