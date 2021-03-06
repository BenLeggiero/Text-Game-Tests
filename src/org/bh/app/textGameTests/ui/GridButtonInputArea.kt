@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package org.bh.app.textGameTests.ui

import javafx.event.*
import javafx.scene.control.*
import javafx.scene.layout.*
import org.bh.tools.base.abstraction.*
import org.bh.tools.base.collections.extensions.*
import org.bh.tools.base.math.*
import org.bh.tools.base.math.geometry.*
import org.bh.tools.textGame.basics.*
import org.bh.tools.textGame.interaction.*
import org.bh.tools.textGame.interaction.InteractionFilter.*
import org.bh.tools.ui.events.*
import org.bh.tools.ui.visualization.*
import kotlin.DeprecationLevel.*
import javafx.scene.layout.GridPane.setFillWidth
import javafx.scene.layout.Priority
import javafx.scene.layout.ColumnConstraints



/**
 * A grid of buttons, to be used as a generalized user input
 *
 * @author Ben Leggiero
 * @since 2018-03-22
 */
class GridButtonInputArea(
        val model: GridButtonInputAreaModel
) : GridPane(), TextGameUiElement<GridButtonInputUserAction> {

    val models: List<List<GridButtonInputModel>> by lazy {
        val actionTable = MutableList<MutableList<GridButtonInputModel>>()

        model.size.forEach { point, _ ->

            actionTable.getOrSet(at = point.row.int32Value,
                                 newElement = { MutableList() },
                                 paddingGenerator = { MutableList() }
            ).getOrSet(at = point.column.int32Value,
                       newElement = { model.buttonModelGenerator(point) },
                       paddingGenerator = { model.buttonModelGenerator(point) })
        }

        /*return*/ actionTable
    }


    val delegate: Delegate? = null


    init {
        models.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, cell ->
                val button = cell.asButton()
                setFillWidth(button, true)
                setFillHeight(button, true)
                button.setMaxSize(Double.infinity, Double.infinity)
                super.add(button, columnIndex, rowIndex)
            }
        }

        (0 until this.columnCount).forEach {
            val cc = ColumnConstraints()
            cc.hgrow = Priority.ALWAYS // allow column to grow
            cc.isFillWidth = true // ask nodes to fill space for column
            // other settings as needed...
            columnConstraints.add(cc)
        }
    }


    override fun interactionTriggers(filter: InteractionFilter): List<GridButtonInputTrigger> {
        return models
                .flatten()
                .filter { it.matches(filter) }
                .map { it.trigger }
    }


    override fun attemptInteraction(interaction: GridButtonInputUserAction): InteractionResult<GridButtonInputUserAction> {
        return delegate?.userDidPressButton(interaction.clickedCoordinate)
                ?: error("User interacted but programmer didn't encode any reaction")
    }


    override fun textDescription(kind: DescriptionPresentation): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


//    override fun
//            <InteractableType, InteractionType, Result>
//            userDidInteract(
//                interactable: InteractableType,
//                didPress: InteractionType)
//            : Result
//            where InteractableType : Interactable<InteractionType>,
//                  InteractionType : InteractionEvent,
//                  Result : InteractionResult<InteractionType>
//    {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }



    interface Delegate {
        fun userDidPressButton(coordinate: Point<Int8>): InteractionResult<GridButtonInputUserAction>
    }
}


interface ButtonInputUserAction: InteractionEvent, UIEvent



interface GridButtonInputUserAction: ButtonInputUserAction {
    val clickedCoordinate: Point<Int8>
}



typealias GridButtonInputTrigger = InteractionTrigger.coordinates<Int8>



open class GridButtonInputAreaModel (
        val size: Int8Size,
        val buttonModelGenerator: (coordinate: Point<Int8>) -> GridButtonInputModel
) {
    constructor(width: Int8, height: Int8, buttonModelGenerator: (coordinate: Point<Int8>) -> GridButtonInputModel)
            : this(Int8Size(width, height), buttonModelGenerator)
}



open class ButtonInputModel<Trigger, Action> (
        var titleText: String,
        var presentation: UIPresentation,
        private var interactionFilter: InteractionFilter,
        val trigger: Trigger,
        open val didPress: (Action) -> Unit
)
    where
        Trigger : InteractionTrigger,
        Action: ButtonInputUserAction
{
    open fun matches(filter: InteractionFilter) = when (this.interactionFilter) {
        is all -> true
        is currentlyAvailable -> filter is visibleToCharacter || filter is currentlyAvailable
        is visibleToCharacter -> filter is visibleToCharacter
    }

    open fun asButton(eventTranslator: (ActionEvent) -> Action): Button {
        val b = Button(this.titleText)

        b.onAction = EventHandler {
            this.didPress(eventTranslator(it))
        }

        return b
    }
}


/**
 * Models a single button in a [GridButtonInputArea]
 */
open class GridButtonInputModel(
        val coordinate: Point<Int8>,
        titleText: String,
        presentation: UIPresentation = UIPresentation.default,
        interactionFilter: InteractionFilter,
        override val didPress: (GridButtonInputUserAction) -> Unit
): ButtonInputModel<GridButtonInputTrigger, GridButtonInputUserAction>
    (titleText, presentation, interactionFilter, GridButtonInputTrigger(coordinate), didPress = didPress)
{
    @Deprecated("This subclass comes with its own event translator", ReplaceWith("asButton()"), HIDDEN)
    override fun asButton(eventTranslator: (ActionEvent) -> GridButtonInputUserAction): Button {
        return super.asButton(eventTranslator)
    }


    fun asButton(): Button {
        return super.asButton { _ ->
            object : GridButtonInputUserAction {
                override val clickedCoordinate get() = coordinate
                override val trigger get() = GridButtonInputTrigger(coordinate)

                override fun toString() = "{ clickedCoordinate: $clickedCoordinate, trigger: $trigger, model: ${this@GridButtonInputModel} }"
            }
        }
    }


    override fun toString(): String {
        return "{ coordinate: $coordinate, titleText: $titleText, presentation: $presentation }"
    }
}
