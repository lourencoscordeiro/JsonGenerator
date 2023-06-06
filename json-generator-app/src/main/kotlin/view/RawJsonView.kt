package view

import json.models.JsonElement
import json.models.JsonObject
import json.models.observability.JsonElementObserver
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JTextArea

class RawJsonView(val jsonElement: JsonObject) : JLabel() {

    private val srcArea = JTextArea().apply {
        tabSize = 2
        isEditable = false
    }

    init {

        layout = GridLayout()
        add(srcArea)

        jsonElement.addObserver(object : JsonElementObserver {

            override fun addedElement(newValue: JsonElement) {
                updateJson()
            }

            override fun updatedElement(newValue: JsonElement) {
                updateJson()
            }

            override fun erasedElement(erasedValue: JsonElement) {
                updateJson()
            }

            override fun erasedAll() {
                updateJson()
            }

        })

    }

    private fun updateJson() {
        srcArea.text = jsonElement.toPrettyJsonString(0)
    }

}