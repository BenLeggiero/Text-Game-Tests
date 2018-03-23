package org.bh.app.textGameTests.ui

import javafx.scene.control.TextField

/**
 * @author Ben Leggiero
 * @since 2018-01-26
 */
class TGTTextInputField : TextField() {
    init {
        styleClass.add("tgt-text-input-field")
        style
    }
}