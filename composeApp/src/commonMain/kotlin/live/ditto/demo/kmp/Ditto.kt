package live.ditto.demo.kmp

import kotlinx.coroutines.flow.Flow
import live.ditto.demo.kmp.GameViewModel.GameState

const val COLLECTION_NAME = "game_state"
const val DOCUMENT_ID = "32760333-D9FE-4023-89C5-6646C5E3C615"

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect open class Ditto() {
    open val version: String
    open val presence: DittoPresence

    open fun startSync()

    open fun stopSync()

    open fun startObserver(): Flow<GameState>
}
