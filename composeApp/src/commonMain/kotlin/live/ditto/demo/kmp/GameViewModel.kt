package live.ditto.demo.kmp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class GameViewModel(
    ditto: Ditto,
    coroutineScope: CoroutineScope
) {
    enum class Colors {
        RED,
        ORANGE,
        YELLOW,
        GREEN,
        BLUE,
        PURPLE,
        BLACK,
        WHITE,
    }

    private val startState = listOf(
        Colors.WHITE,
        Colors.WHITE,
        Colors.WHITE,
        Colors.WHITE,
        Colors.WHITE,
        Colors.WHITE,
        Colors.WHITE,
        Colors.WHITE,
        Colors.WHITE,
    )

    data class GameState(
        var currentState: MutableList<Colors> = mutableListOf(
            Colors.WHITE,
            Colors.WHITE,
            Colors.WHITE,
            Colors.WHITE,
            Colors.WHITE,
            Colors.WHITE,
            Colors.WHITE,
            Colors.WHITE,
            Colors.WHITE,
        )
    ) {
        companion object {
            /** Deserialize GameState from a Map. */
            fun fromMap(map: Map<String, Any?>): GameState {
                val list = mutableListOf<Colors>()
                for (i in 0..8) {
                    val stringColor = map["$i"] as String
                    list.add(Colors.valueOf(stringColor))
                }
                return GameState(list)
            }
        }
        fun buttonTapped(buttonNumber: Int, newColor: Colors) {
            currentState[buttonNumber] = newColor
            observer(this)
        }

        /** Only one observer at a time. */
        private var observer: (GameState) -> Unit = {}

        fun observe(callback: (GameState) -> Unit) {
            observer = callback
        }
    }

    fun buttonTapped(buttonNumber: Int, newColor: Colors) =
        _gameState.buttonTapped(buttonNumber, newColor)

    private val _gameState = GameState()

    private val dittoState = ditto.startObserver()

    var gameState: StateFlow<GameState> =
        dittoState
            .stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(stopTimeoutMillis = 1000L),
                _gameState,
            )

    companion object {
        const val TAG = "GameViewModel"
    }
}