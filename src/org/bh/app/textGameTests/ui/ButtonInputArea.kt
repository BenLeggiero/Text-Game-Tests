package org.bh.app.textGameTests.ui

import LatteFX.*
import org.bh.tools.base.abstraction.*
import org.bh.tools.base.collections.extensions.*
import org.bh.tools.base.func.*
import org.bh.tools.base.math.*
import org.bh.tools.textGame.basics.*
import org.bh.tools.textGame.interaction.*
import org.bh.tools.textGame.interaction.InteractionFilter.*
import org.bh.tools.ui.events.*
import org.bh.tools.ui.visualization.*

/**
 * @author Ben Leggiero
 * @since 2018-03-22
 */
class ButtonInputArea(val model: ButtonInputAreaModel) : FXView(), TextGameUIElement<GridButtonInputAction> {

    val models: List<List<GridButtonInputModel>> by lazy {
        val actionTable = MutableList<List<GridButtonInputModel>>()

        (0.int8Value..model.rowCount).forEach { rowIndex32 ->
            val row = MutableList<GridButtonInputModel>()
            val rowIndex = rowIndex32.int8Value

            (0.int8Value..model.columnCount).forEach { columnIndex ->
                row.add(model.buttonModelGenerator(columnIndex.int8Value, rowIndex))
            }

            actionTable.add(row)
        }

        /*return*/ actionTable
    }


    val delegate: Delegate? = null


    override fun interactions(filter: InteractionFilter): List<GridButtonInputAction> {
        return models
                .flatten()
                .filter { it.matches(filter) }
                .map { it.action }
    }


    override fun attemptInteraction(interaction: GridButtonInputAction): InteractionResult<GridButtonInputAction> {
        return delegate?.userDidPressButton(column = interaction.clickedColumn, row = interaction.clickedRow)
                ?: error("User interacted but programmer didn't encode any reaction")
    }


    override fun textDescription(kind: DescriptionPresentation): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


//    override fun
//            <InteractableType, InteractionType, Result>
//            userDidInteract(
//                interactable: InteractableType,
//                interaction: InteractionType)
//            : Result
//            where InteractableType : Interactable<InteractionType>,
//                  InteractionType : Interaction,
//                  Result : InteractionResult<InteractionType>
//    {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }



    interface Delegate {
        fun userDidPressButton(column: Int8, row: Int8): InteractionResult<GridButtonInputAction>
    }
}



fun <A, B, ElementA, ElementB>
        Tuple2<A, B>
        .forEach(iterator: (ElementA, ElementB) -> Unit)
        where A : Iterable<ElementA>, B : Iterable<ElementB>
        = first.forEach { a ->
            second.forEach { b ->
                iterator(a, b)
            }
        }



interface ButtonInputAction: Interaction



interface GridButtonInputAction: ButtonInputAction {
    val clickedColumn: Int8
    val clickedRow: Int8
}



class ButtonInputAreaModel (
        val columnCount: Int8,
        val rowCount: Int8,
        val buttonModelGenerator: (columnIndex: Int8, rowIndex: Int8) -> GridButtonInputModel
)



open class ButtonInputModel<Action> (
        var titleText: String,
        private var interactionFilter: InteractionFilter,
        var action: Action,
        var presentation: UIPresentation,
        val didPress: (event: UIEvent) -> Unit
)
    where Action : ButtonInputAction
{
    fun matches(filter: InteractionFilter) = when (this.interactionFilter) {
        all -> true
        currentlyAvailable -> filter == visibleToCharacter || filter == currentlyAvailable
        visibleToCharacter -> filter == visibleToCharacter
    }
}



open class GridButtonInputModel(
        val column: Int8,
        val row: Int8,
        titleText: String,
        interactionFilter: InteractionFilter,
        action: GridButtonInputAction,
        presentation: UIPresentation,
        didPress: (event: UIEvent) -> Unit
): ButtonInputModel<GridButtonInputAction>(titleText, interactionFilter, action, presentation, didPress)
