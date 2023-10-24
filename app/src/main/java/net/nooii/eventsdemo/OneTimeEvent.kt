package net.nooii.eventsdemo

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface OneTimeEvent {
    @Composable
    fun collect(): StateEvent
    fun consume()
}

interface OneTimeDataEvent<T> {
    @Composable
    fun collect(): StateEventWithContent<T>
    fun consume()
}

class MutableOneTimeEvent: OneTimeEvent {

    private val stateStream = MutableStateFlow<StateEvent>(consumed)
    private val stateFlow = stateStream.asStateFlow()

    @Composable
    override fun collect() = stateFlow.collectAsStateWithLifecycle().value

    fun emit() {
        stateStream.value = triggered
    }

    override fun consume() {
        stateStream.value = consumed
    }
}

class MutableOneTimeDataEvent<T> : OneTimeDataEvent<T> {

    private val stateStream = MutableStateFlow<StateEventWithContent<T>>(consumed())
    private val stateFlow = stateStream.asStateFlow()

    @Composable
    override fun collect() = stateFlow.collectAsStateWithLifecycle().value

    fun emit(data: T) {
        stateStream.value = triggered(data)
    }

    override fun consume() {
        stateStream.value = consumed()
    }
}

@Composable
fun EventEffect(event: OneTimeEvent, action: suspend () -> Unit) {
    EventEffect(
        event = event.collect(),
        onConsumed = event::consume,
        action = action
    )
}

@Composable
fun <T> EventEffect(event: OneTimeDataEvent<T>, action: suspend (T) -> Unit) {
    EventEffect(
        event = event.collect(),
        onConsumed = event::consume,
        action = action
    )
}