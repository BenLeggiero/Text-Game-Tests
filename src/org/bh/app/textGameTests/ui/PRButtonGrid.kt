package org.bh.app.textGameTests.ui

import TextGameEngine.*
import org.bh.app.textGameTests.ui.CardinalDirection.*
import org.bh.app.textGameTests.ui.GridButtonRole.*
import org.bh.tools.base.abstraction.*
import org.bh.tools.base.func.*
import org.bh.tools.base.math.geometry.*
import org.bh.tools.textGame.interaction.*
import org.bh.tools.textGame.interaction.InteractionTrigger.*
import org.bh.tools.ui.visualization.*

/**
 * @author Ben Leggiero
 * @since 2018-07-13
 */
@Suppress("FunctionName")
fun PRGridButtonAreaModel(): GridButtonInputAreaModel {
    val manager = PRGridButtonManager()
    return GridButtonInputAreaModel(5, 3) { genButton(it, manager) }
}



private fun genButton(coordinates: Point<Int8>, manager: PRGridButtonManager): PRGridButtonInputModel {
    return PRGridButtonInputModel(coordinates = coordinates,
                                  role = genRole(coordinates),
                                  didPress = { manager.didPress(coordinates, it) },
                                  interactionFilter = InteractionFilter.currentlyAvailable,
                                  presentation = UIPresentation.default)
}


private fun genRole(coordinates: Point<Int8>) = when (coordinates.row.toInt()) {
    0 -> when (coordinates.column.toInt()) {
        0 -> noInteraction
        1 -> cardinalNavigation(north)
        2 -> noInteraction
        else -> noInteraction
    }
    1 -> when (coordinates.column.toInt()) {
        0 -> cardinalNavigation(west)
        1 -> noInteraction
        2 -> cardinalNavigation(east)
        else -> noInteraction
    }
    2 -> when (coordinates.column.toInt()) {
        0 -> noInteraction
        1 -> cardinalNavigation(south)
        2 -> noInteraction
        else -> noInteraction
    }
    else -> noInteraction
}


class PRGridButtonManager {
    fun didPress(coordinates: Point<Int8>, action: GridButtonInputUserAction) {
        println("{ coordinates: $coordinates, action: $action}")
    }
}


open class PRGridButtonInputModel(coordinates: Point<Int8>,
                                  role: GridButtonRole,
                                  presentation: UIPresentation,
                                  interactionFilter: InteractionFilter,
                                  didPress: (GridButtonInputUserAction) -> Unit)
    : GridButtonInputModel(coordinates,
                           role.titleText,
                           presentation,
                           interactionFilter,
                           didPress) {
    @Suppress("unused")
    var role by observing(initialValue = role, didSet = { oldValue, newValue ->
        this.titleText = role.titleText
    })
}


sealed class GridButtonRole(val titleText: String) {
    object noInteraction : GridButtonRole(titleText = "")

    class cardinalNavigation(val direction: CardinalDirection, val specialPlaceName: String? = null)
        : GridButtonRole(titleText = specialPlaceName ?: direction.name)

    class interact(var target: GameObject) : GridButtonRole(target.name ?: "null")
}


enum class CardinalDirection {
    north,
    northEast,
    east,
    southEast,
    south,
    southWest,
    west,
    northWest
}
