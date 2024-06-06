package live.ditto.demo.kmp

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class GameViewModel(
    ditto: Ditto,
    coroutineScope: CoroutineScope
) {
    enum class GameColor(
        val color: Color
    ) {
        RED(Color.Red),
        YELLOW(Color.Yellow),
        GREEN(Color.Green),
        BLUE(Color.Blue),
        CYAN(Color.Cyan),
        MAGENTA(Color.Magenta),
        BLACK(Color.Black),
        WHITE(Color.White),
        GRAY(Color.Gray),
    }

    private val startState = listOf(
        GameColor.WHITE,
        GameColor.WHITE,
        GameColor.WHITE,
        GameColor.WHITE,
        GameColor.WHITE,
        GameColor.WHITE,
        GameColor.WHITE,
        GameColor.WHITE,
        GameColor.WHITE,
    )

    data class GameState(
        var currentState: MutableList<GameColor> = mutableListOf(
            GameColor.WHITE,
            GameColor.WHITE,
            GameColor.WHITE,
            GameColor.WHITE,
            GameColor.WHITE,
            GameColor.WHITE,
            GameColor.WHITE,
            GameColor.WHITE,
            GameColor.WHITE,
        )
    ) {
        companion object {
            /** Deserialize GameState from a Map. */
            fun fromMap(map: Map<String, Any?>): GameState {
                val list = mutableListOf<GameColor>()
                for (i in 0..8) {
                    val stringColor = map["$i"] as String
                    list.add(GameColor.valueOf(stringColor))
                }
                return GameState(list)
            }
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

    fun buttonTapped(buttonNumber: Int, newColor: GameColor) =
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