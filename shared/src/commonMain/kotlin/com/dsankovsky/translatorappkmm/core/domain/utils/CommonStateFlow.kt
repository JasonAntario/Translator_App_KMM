package com.dsankovsky.translatorappkmm.core.domain.utils

import kotlinx.coroutines.flow.StateFlow


expect open class CommonStateFlow<T>(flow: StateFlow<T>) : StateFlow<T>

fun <T> StateFlow<T>.toCommonStateFlow() = CommonStateFlow(this)