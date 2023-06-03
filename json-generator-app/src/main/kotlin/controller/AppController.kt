package controller

import json.models.JsonElement
import json.models.JsonObject
import view.RawJsonView
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.JFrame
import view.StructuredJsonView


fun main() {

    val jsonElement: JsonElement = JsonObject()

    val view = JFrame()
    view.title = "JSON Object Editor"
    view.size = Dimension(600, 600)
    view.layout = GridLayout(0, 2)
    view.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    // adding left panel
    view.add(StructuredJsonView(jsonElement))
    // adding right panel
    view.add(RawJsonView(jsonElement))


    view.isVisible = true

}