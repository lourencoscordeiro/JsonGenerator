package view

import json.models.JsonElement
import json.models.command.AddElementCommand
import json.models.observability.JsonElementObserver
import java.awt.Component
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class StructuredJsonView(private val jsonElement: JsonElement) : JLabel() {

    init {

        layout = GridLayout()

        val scrollPane = JScrollPane(structuredJsonViewPanel()).apply {
            horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
            verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        }
        add(scrollPane)

    }

    private fun structuredJsonViewPanel(): JPanel =
        JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT

            // menu
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent) {

                    if (SwingUtilities.isRightMouseButton(e)) {

                        val menu = JPopupMenu("Message")
                        val add = JButton("add")

                        add.addActionListener {
                            val text = JOptionPane.showInputDialog("text")

                            // adds null element
                            val command = AddElementCommand(jsonElement, Pair(text, "?"))
                            command.run()
                            add(structureJsonDataWidget(text, "?"))

                            menu.isVisible = false
                            revalidate()
                            repaint()
                        }

                        menu.add(add);
                        menu.show(this@apply, 100, 100);
                    }

                }
            })
        }

    private fun structureJsonDataWidget(key: String, value: String): JPanel =
        JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            alignmentX = Component.LEFT_ALIGNMENT
            alignmentY = Component.TOP_ALIGNMENT

            add(JLabel(key))
            val text = JTextField(value)
            add(text)
        }

}