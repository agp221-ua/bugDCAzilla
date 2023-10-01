package com.example.bugDCAzilla.managers

import com.example.bugDCAzilla.Main
import com.example.bugDCAzilla.dataObject.Bug
import com.example.bugDCAzilla.dataObject.Comment
import com.example.bugDCAzilla.dataObject.State
import com.example.bugDCAzilla.dataObject.User
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.lang.StringBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.swing.*

class BugManager {

    companion object {
        val timeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")

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

        if (UsersManager.usersManager.currentUser != null && UsersManager.usersManager.currentUser!!.admin) {
            gbc.gridx = 0
            gbc.gridy = 1
            val leftLabel = JLabel("${if (bug.state == State.OPEN) "[OP]" else "[CL]"} #${bug.id} - ${bug.author}")
            leftLabel.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {
                    if (e.clickCount == 1) {
                        if (bug.state == State.OPEN) {
                            bug.state = State.CLOSED
                            leftLabel.text = "[CL] ${bug.id} - ${bug.author}"
                        } else {
                            bug.state = State.OPEN
                            leftLabel.text = "[OP] ${bug.id} - ${bug.author}"
                        }
                        BugsManager.bugsManager.saveBug(bug)
                        Main.show(createBugManagerWindow(bugID))
                    }
                }
            })
            leftLabel.font = Font("Arial", Font.BOLD, 34)
            panel.add(leftLabel, gbc)
        } else {
            gbc.gridx = 0
            gbc.gridy = 1
            val leftLabel = JLabel("${if (bug.state != State.OPEN) "[CL]" else ""} #${bug.id} - ${bug.author}")
            leftLabel.font = Font("Arial", Font.BOLD, 34)
            leftLabel.foreground = Color.blue
            panel.add(leftLabel, gbc)
        }
        gbc.gridy = 1
        gbc.gridwidth = 1
        gbc.anchor = GridBagConstraints.EAST
        val rightLabel = JLabel(bug.date)
        rightLabel.horizontalAlignment = SwingConstants.RIGHT
        rightLabel.font = Font("Arial", Font.BOLD, 34)
        rightLabel.foreground = Color.blue
        panel.add(rightLabel, gbc)

        gbc.gridx = 0
        gbc.gridy = 2
        gbc.gridwidth = 1
        panel.add(Box.createRigidArea(Dimension(10, 2)), gbc)

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
        val descriptionLabel = JLabel("<html>${widthLimitator("${bug.description.replace("\n", "<br>")}<br><br><h2 style=\"color: #8E8E8E\">Etiquetas: ${BugsManager.bugsManager.showLabels(bug)}")}</html>")
        descriptionLabel.font = Font("Arial", Font.BOLD, 20)
        panel.add(descriptionLabel, gbc)

        var y = 6

        gbc.gridy = y++
        gbc.weighty = 1.0
        panel.add(Box.createRigidArea(Dimension(10, 30)), gbc)
        gbc.weighty = 0.0

        for (comment in bug.comments) {
            gbc.gridy = y++
            panel.add(JSeparator(), gbc)

            gbc.gridy = y++
            val authorLabel = JLabel("${comment.author} - ${comment.date}")
            authorLabel.font = Font("Arial", Font.BOLD, 20)
            authorLabel.foreground = Color.blue
            panel.add(authorLabel, gbc)

            gbc.gridy = y++
            val commentLabel = JLabel("<html>${widthLimitator(comment.text.replace("\n", "<br>"))}</html>")
            commentLabel.font = Font("Arial", Font.BOLD, 15)
            panel.add(commentLabel, gbc)

            gbc.gridy = y++
            panel.add(Box.createRigidArea(Dimension(10, 10)), gbc)
        }

        gbc.gridy = y++
        gbc.weighty = 1.0
        panel.add(Box.createRigidArea(Dimension(10, 30)), gbc)
        gbc.weighty = 0.0

        if (bug.state == State.OPEN) {
            gbc.gridy = y++
            val addComment = JTextArea(5, 30)
            addComment.font = Font("Arial", Font.BOLD, 15)
            val scrollPane = JScrollPane(addComment)
            scrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
            panel.add(scrollPane, gbc)

            gbc.gridy = y++
            val addCommentButton = JButton("Añadir comentario")
            addCommentButton.font = Font("Arial", Font.BOLD, 20)
            addCommentButton.addActionListener() {
                if (UsersManager.usersManager.currentUser == null) {
                    LoginManager.loginManager.show()
                    return@addActionListener
                }
                if (addComment.text.trim().isNotBlank()) {
                    bug.comments.add(
                        Comment(
                            UsersManager.usersManager.currentUser!!.name, LocalDateTime.now().format(
                                timeFormat
                            ), addComment.text.trim()
                        )
                    )
                    BugsManager.bugsManager.saveBug(bug)
                    Main.show(createBugManagerWindow(bugID))
                }
            }
            panel.add(addCommentButton, gbc)
        }

        val res = JPanel()
        res.layout = BorderLayout()
        val scroll = JScrollPane(panel)
        scroll.verticalScrollBar.unitIncrement = 30
        scroll.minimumSize =
            Dimension(Toolkit.getDefaultToolkit().screenSize.width, Toolkit.getDefaultToolkit().screenSize.height)
        res.add(scroll, BorderLayout.CENTER)
        res.minimumSize =
            Dimension(Toolkit.getDefaultToolkit().screenSize.width, Toolkit.getDefaultToolkit().screenSize.height)

        return res


    }

    private fun widthLimitator(text: String): String {
        val words = text.split("<br>")
        val sb = StringBuilder()
        for (word in words) {
            var w = word
            while (true){
                if(w.length <= 160) break
                val idx = w.indexOfLast { it == ' ' }
                sb.append(w.substring(0, idx))
                sb.append("<br>")
                w = word.substring(idx)
            }
            sb.append(w)
            sb.append("<br>")
        }
        return sb.toString()
    }




}
