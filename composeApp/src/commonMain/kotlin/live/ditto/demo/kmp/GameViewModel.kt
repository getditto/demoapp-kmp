package live.ditto.demo.kmp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
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
        var currentState: List<Colors> = listOf(
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
    )

    private var _gameState: Flow<GameState> =
        callbackFlow {
            GameState()

            awaitClose {}
        }

    val gameState =
        _gameState.stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            GameState(),
        )

    companion object {
        const val TAG = "GameViewModel"
    }
}