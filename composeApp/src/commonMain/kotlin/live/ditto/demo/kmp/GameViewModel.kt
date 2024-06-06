package live.ditto.demo.kmp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
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

    var gameState: StateFlow<GameState> =
        callbackFlow {
            _gameState.observe {
                println("GameState: ${it}")
                trySend(it)
            }

            awaitClose {}
        }
            .stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(stopTimeoutMillis = 1000L),
                _gameState,
            )

//    val gameStateFlow =
//        _gameState.stateIn(
//            coroutineScope,
//            SharingStarted.WhileSubscribed(stopTimeoutMillis = 1000L),
//            GameState(),
//        )

    companion object {
        const val TAG = "GameViewModel"
    }
}