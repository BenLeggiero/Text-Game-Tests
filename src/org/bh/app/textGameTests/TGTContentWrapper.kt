package org.bh.app.textGameTests

import LatteFX.*
import javafx.scene.control.TextArea
import javafx.scene.layout.BorderPane
import org.bh.app.textGameTests.ui.*

class TGTContentWrapper : LatteWindowContentView(TGTMainView()) {
}

class TGTMainView : BorderPane() {

    val textOutputArea: TextArea
//    val textInputField: TextField
    val buttonInputArea: ButtonInputArea

    init {
        this.styleClass.add("tgt")

//        textInputField = TGTTextInputField()
//        textInputField.maxHeight = textInputField.font.size * 3
//        this.bottom = textInputField

        buttonInputArea = ButtonInputArea()
        this.bottom = buttonInputArea

        textOutputArea = TextArea("Hello, FX!")
//        textOutputArea.font = Font.font("monospace")
        textOutputArea.isEditable = false
        this.center = textOutputArea

        this.stylesheets.add(TGTStyle.cssFileUrlString)
    }
}

