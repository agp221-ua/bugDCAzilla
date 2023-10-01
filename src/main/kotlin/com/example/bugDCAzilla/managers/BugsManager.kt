package com.example.bugDCAzilla.managers

import com.example.bugDCAzilla.dataObject.Bug
import com.example.bugDCAzilla.dataObject.Comment
import com.example.bugDCAzilla.dataObject.State
import java.io.*

class BugsManager {

    companion object {
        private val FILE_NAME = "bugs.bd";
        val bugsManager: BugsManager = BugsManager()
    }



    var bugs: MutableMap<Int, Bug> = mutableMapOf()
    var labels: MutableSet<String> = mutableSetOf()

    init {
        loadBugs()
    }

    fun loadBugs() {
        val file = File(FILE_NAME)
        if (!file.exists())
            file.createNewFile()

        file.inputStream().bufferedReader().forEachLine {
            val bug = parseBug(it) ?: return@forEachLine
            bugs[bug.id] = bug
            Bug.nextID = if (bug.id >= Bug.nextID) bug.id + 1 else Bug.nextID
        }
    }

    private fun parseBug(line: String): Bug? {
        val fields = line.split('␟')
        if (fields.size != 8)
            return null

        return Bug(
            fields[0].toIntOrNull() ?: return null,
            fields[1],
            if (fields[2] == "OPEN") State.OPEN else State.CLOSED,
            fields[3],
            fields[4],
            fields[5].replace("␜", "\n"),
            parseComments(fields[6]),
            parseLabels(fields[7])
        )
    }

    private fun parseBug(bug: Bug): String {
        return "${bug.id}␟${bug.author}␟${bug.state}␟${bug.date}␟${bug.title}␟${bug.description.replace("\n", "␜")}␟${parseComments(bug.comments)}␟${parseLabels(bug.labels)}"
    }

    private fun parseComments(line: String): List<Comment> {
        val comments = line.split('␝')
        val commentsArray = mutableListOf<Comment>()
        for (comment in comments) {
            val fields = comment.split('␞')
            if (fields.size != 3)
                continue
            commentsArray.add(Comment(fields[0], fields[1], fields[2].replace("␜", "\n")))
        }
        return commentsArray
    }

    private fun parseComments(comments: List<Comment>): String {
        var commentsString = ""
        for (comment in comments) {
            commentsString += "${comment.author}␞${comment.date}␞${comment.text.replace("\n", "␜")}␝"
        }
        return commentsString
    }

    private fun parseLabels(labels: List<String>): String {
        var labelsString = ""
        for (label in labels) {
            labelsString += "$label␝"
        }
        return labelsString
    }

    fun addBug(bug: Bug) {
        try {
            val file = FileWriter(FILE_NAME, true)
            val writer = BufferedWriter(file)

            writer.write(parseBug(bug))
            writer.newLine()

            writer.close()
            file.close()

            labels.addAll(bug.labels)
            bugs[bug.id] = bug

        } catch (_: IOException) {
        }
    }

    fun saveBug(bug: Bug){
        val file = File(FILE_NAME)
        val tempFile = File("${FILE_NAME}.temp")

        val br = BufferedReader(FileReader(file))
        val bw = BufferedWriter(FileWriter(tempFile))

        val idABuscar = bug.id.toString()

        br.use { reader ->
            bw.use { writer ->
                var linea: String?
                while (reader.readLine().also { linea = it } != null) {
                    if (linea?.startsWith(idABuscar) == true) {
                        val nuevaLinea = parseBug(bug)
                        writer.write("$nuevaLinea\n")
                    } else {
                        writer.write("$linea\n")
                    }
                }
            }
        }
        tempFile.renameTo(file)
    }

    private fun parseLabels(s: String): List<String> {
        val labelss = s.split('␝')
        val labelsArray = mutableListOf<String>()
        for (label in labelss) {
            labelsArray.add(label)
        }
        labels.addAll(labelsArray)
        return labelsArray
    }

    fun bugsArray(search: String): Array<Array<Any>> {
        val bugsArray = mutableListOf<Array<Any>>()
        for (bug in bugs) {
            var add = false
            if (!search.startsWith(':')) {
                if (bug.value.title.contains(search, true)) add = true
                else if (bug.value.description.contains(search, true)) add = true
                if (!add) {
                    for (label in bug.value.labels) {
                        if (label.contains(search, true)) {
                            add = true
                            break
                        }
                    }
                }
                if (!add) {
                    for (comment in bug.value.comments) {
                        if (comment.text.contains(search, true)) {
                            add = true
                            break
                        }
                    }
                }
            } else {
                val searchLabel = search.substring(1)
                if (bug.value.labels.contains(searchLabel)) add = true
            }
            if (add) bugsArray.add(arrayOf(bug.value.id, bug.value.date, bug.value.title, bug.value.labels))
        }

        return bugsArray.toTypedArray()
    }


}
