package org.bh.app.textGameTests.ui

import LatteFX.*
import org.bh.tools.base.abstraction.*
import org.bh.tools.textGame.basics.*
import org.bh.tools.textGame.interaction.*

/**
 * @author Ben Leggiero
 * @since 2018-03-22
 */
class ButtonInputArea(val model: ButtonInputAreaModel) : FXView(), TextGameUserInterface {
    override fun <InteractableType : Interactable<InteractionType>, InteractionType : Interaction, Result : InteractionResult<InteractionType>> userDidInteract(
            interactable: InteractableType,
            interaction: InteractionType): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}



class ButtonInputAreaModel (
        val columnCount: Int8,
        val rowCount: Int8,
        val buttonModelGenerator: (columnIndex: Int8, rowIndex: Int8) -> ButtonInputModel
)

class ButtonInputModel (
        var titleText: String,
        val didPress: (event: UIEvent)
)
