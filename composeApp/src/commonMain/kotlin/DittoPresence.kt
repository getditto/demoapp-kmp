import kotlinx.coroutines.flow.Flow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DittoPresence {
    fun currentGraph(): String

    fun observeAsFlow(): Flow<String>
}
