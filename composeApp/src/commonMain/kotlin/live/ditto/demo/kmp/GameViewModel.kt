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

    fun buttonTapped(buttonNumber: Int) {
        when (buttonNumber) {
            0 -> _button1Color.value = myColor
            1 -> _button2Color.value = myColor
            2 -> _button3Color.value = myColor
            3 -> _button4Color.value = myColor
            4 -> _button5Color.value = myColor
            5 -> _button6Color.value = myColor
            6 -> _button7Color.value = myColor
            7 -> _button8Color.value = myColor
            8 -> _button9Color.value = myColor
        }
        _gameState.buttonTapped(buttonNumber, myColor)
    }

    private var _button1Color = MutableStateFlow(WHITE)
    private var _button2Color = MutableStateFlow(WHITE)
    private var _button3Color = MutableStateFlow(WHITE)
    private var _button4Color = MutableStateFlow(WHITE)
    private var _button5Color = MutableStateFlow(WHITE)
    private var _button6Color = MutableStateFlow(WHITE)
    private var _button7Color = MutableStateFlow(WHITE)
    private var _button8Color = MutableStateFlow(WHITE)
    private var _button9Color = MutableStateFlow(WHITE)

    var button1Color = _button1Color.asStateFlow()
    var button2Color = _button2Color.asStateFlow()
    var button3Color = _button3Color.asStateFlow()
    var button4Color = _button4Color.asStateFlow()
    var button5Color = _button5Color.asStateFlow()
    var button6Color = _button6Color.asStateFlow()
    var button7Color = _button7Color.asStateFlow()
    var button8Color = _button8Color.asStateFlow()
    var button9Color = _button9Color.asStateFlow()

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
