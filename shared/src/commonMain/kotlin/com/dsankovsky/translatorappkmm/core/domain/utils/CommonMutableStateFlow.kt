package com.dsankovsky.translatorappkmm.core.domain.utils

import kotlinx.coroutines.flow.MutableStateFlow


expect open class CommonMutableStateFlow<T>(flow: MutableStateFlow<T>) : MutableStateFlow<T>

fun <T> MutableStateFlow<T>.toCommonMutableStateFlow() = CommonMutableStateFlow(this)