package live.ditto.demo.kmp

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import live.ditto.demo.kmp.GameViewModel.GameColor

class MainViewModel {
    val ditto = Ditto()

    val version: String
        get() = ditto.version

    private var _syncEnabled = MutableStateFlow(false)
    var syncEnabled = _syncEnabled.asStateFlow()

    fun toggleSync() =
        when (_syncEnabled.value) {
            true -> {
                ditto.stopSync()
                _syncEnabled.value = false
            }
            false -> {
                ditto.startSync()
                _syncEnabled.value = true
            }
        }
}
