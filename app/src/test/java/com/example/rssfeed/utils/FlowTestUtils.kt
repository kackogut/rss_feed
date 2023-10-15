package com.example.rssfeed.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent

@OptIn(ExperimentalCoroutinesApi::class)
fun <T : Any> TestScope.assertStateFlowValues(
    stateFlow: Flow<T>,
    checkBlock: () -> Unit,
    assertBlock: (states: List<T>) -> Unit
) {
    val states = mutableListOf<T>()
    val job = launch(UnconfinedTestDispatcher()) {
        stateFlow.toList(states)
    }

    checkBlock.invoke()
    runCurrent()

    assertBlock.invoke(states)
    job.cancel()
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T : Any> TestScope.assertStateFlowLastValue(
    stateFlow: Flow<T>,
    checkBlock: () -> Unit,
    assertBlock: (state: T) -> Unit
) {
    val states = mutableListOf<T>()
    val job = launch(UnconfinedTestDispatcher()) {
        stateFlow.toList(states)
    }

    checkBlock.invoke()
    runCurrent()

    assertBlock.invoke(states.last())
    job.cancel()
}
