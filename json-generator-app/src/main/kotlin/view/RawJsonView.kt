package view

import json.models.JsonElement
import json.models.observability.JsonElementObserver
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JTextArea

class RawJsonView(private val jsonElement: JsonElement) : JLabel() {

    init {

        layout = GridLayout()

        val srcArea = JTextArea()
        srcArea.tabSize = 2
        srcArea.isEditable = false
        add(srcArea)

        jsonElement.addObserver(object : JsonElementObserver {

            override fun addedElement(newValue: JsonElement) {
                srcArea.text = jsonElement.toPrettyJsonString(0)
            }

        })

    }

}