package live.ditto.demo.kmp

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import live.ditto.demo.kmp.GameViewModel.GameColor.WHITE

class GameViewModel(
    val ditto: Ditto,
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

    fun buttonTapped(squareIndex: Int) {
        _gameState.buttonTapped(squareIndex, myColor)
        ditto.updateDocument(_gameState, squareIndex)
    }

    private val _gameState = GameState()

    private val dittoLiveQueryState: Flow<GameState> = ditto.startObserver()

    var gameState: StateFlow<GameState> =
        dittoLiveQueryState
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
