package live.ditto.demo.kmp

import live.ditto.demo.kmp.GameViewModel.GameColor
import live.ditto.demo.kmp.GameViewModel.GameColor.WHITE

data class GameState(
    val _id: String = DOCUMENT_ID,
    var currentState: MutableList<GameColor> = mutableListOf(
        WHITE,
        WHITE,
        WHITE,
        WHITE,
        WHITE,
        WHITE,
        WHITE,
        WHITE,
        WHITE,
    )
) {
    companion object {
        val startState = listOf(
            WHITE,
            WHITE,
            WHITE,
            WHITE,
            WHITE,
            WHITE,
            WHITE,
            WHITE,
            WHITE,
        )

        /** Deserialize GameState from a Map. */
        fun fromMap(map: Map<String, Any?>): GameState {
            val list = mutableListOf<GameColor>()
            for (i in 0..8) {
                val stringColor = map["$i"] as String
                list.add(GameColor.valueOf(stringColor))
            }
            return GameState(
                currentState = list,
            )
        }
    }

    fun toMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        currentState.toTypedArray().forEachIndexed { index, color ->
            map["$index"] = color.name
        }
        return map
    }

    fun buttonTapped(buttonNumber: Int, newColor: GameColor) {
        currentState[buttonNumber] = newColor
        observer(this)
    }

    /** Only one observer at a time. */
    private var observer: (GameState) -> Unit = {}

    fun observe(callback: (GameState) -> Unit) {
        observer = callback
    }
}
