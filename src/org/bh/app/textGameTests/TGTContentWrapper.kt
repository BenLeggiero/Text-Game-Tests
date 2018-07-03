package org.bh.app.textGameTests

import LatteFX.*
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane
import org.bh.app.textGameTests.ui.*
import org.bh.tools.textGame.interaction.InteractionFilter.*

class TGTContentWrapper : LatteWindowContentView(TGTMainView()) {
}

class TGTMainView : BorderPane() {

    val textOutputArea: TextArea
//    val textInputField: TextField
    val gridButtonInputArea: GridButtonInputArea

    init {
        this.styleClass.add("tgt")

//        textInputField = TGTTextInputField()
//        textInputField.maxHeight = textInputField.font.size * 3
//        this.bottom = textInputField

        gridButtonInputArea = GridButtonInputArea(GridButtonInputAreaModel(columnCount = 5,
                                                                           rowCount = 3,
                                                                           buttonModelGenerator = { columnIndex, rowIndex ->
            GridButtonInputModel(column = columnIndex,
                                 row = rowIndex,
                                 titleText = "Hey there",
                                 interactionFilter = visibleToCharacter,
                                 didPress = {
                println("pressed (${it.clickedColumn}, ${it.clickedRow})")
            })
        }))
        this.bottom = gridButtonInputArea

        textOutputArea = TextArea("Hello, FX!")
//        textOutputArea.font = Font.font("monospace")
        textOutputArea.isEditable = false
        this.center = textOutputArea

        this.stylesheets.add(TGTStyle.cssFileUrlString)
    }
}

