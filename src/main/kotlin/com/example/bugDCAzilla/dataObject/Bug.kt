package com.example.bugDCAzilla.dataObject

class Bug(
    var id: Int,
    var author: String,
    var state: State,
    var date: String,
    var title: String,
    var description: String,
    var comments: MutableList<Comment>,
    var labels: List<String>
) {
    companion object {
        var nextID = 0
    }
    fun addComment(comment: Comment) {
        comments.add(comment)
    }

    fun addLabel(label: String) {
        labels = labels + label
    }
}

enum class State {
    OPEN, CLOSED
}
