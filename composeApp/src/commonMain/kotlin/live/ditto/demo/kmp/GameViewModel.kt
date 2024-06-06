package live.ditto.demo.kmp

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import live.ditto.demo.kmp.GameViewModel.GameColor.WHITE

class GameViewModel(
    ditto: Ditto,
    coroutineScope: CoroutineScope,
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
        WHITE(Color.White),
        GRAY(Color.Gray),
    }

    private val startState = listOf(
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

    data class GameState(
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

    fun buttonTapped(buttonNumber: Int) {
        when (buttonNumber) {
            0 -> _button1Color.value = myColor
            1 -> button2Color = myColor
            2 -> button3Color = myColor
            3 -> button4Color = myColor
            4 -> button5Color = myColor
            5 -> button6Color = myColor
            6 -> button7Color = myColor
            7 -> button8Color = myColor
            8 -> button9Color = myColor
        }
        _gameState.buttonTapped(buttonNumber, myColor)
    }

    private var _button1Color = MutableStateFlow(WHITE)
    var button1Color = _button1Color.asStateFlow()
    var button2Color = WHITE
    var button3Color = WHITE
    var button4Color = WHITE
    var button5Color = WHITE
    var button6Color = WHITE
    var button7Color = WHITE
    var button8Color = WHITE
    var button9Color = WHITE

    private val _gameState = GameState()

    private val dittoState = ditto.startObserver()

    var gameState: StateFlow<GameState> =
        dittoState
            .stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(stopTimeoutMillis = 1000L),
                _gameState,
            )

    var myColor = randomColor()
        private set

    fun randomColor(): GameColor {
        myColor = GameColor.entries.toTypedArray().random()
        return myColor
    }

    companion object {
        const val TAG = "GameViewModel"
    }
}
