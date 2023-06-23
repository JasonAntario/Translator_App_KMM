package com.dsankovsky.translatorappkmm.core.domain.utils

import kotlinx.coroutines.flow.MutableStateFlow

class IOSMutableStateFlow<T>(
    initialValue: T
) : CommonMutableStateFlow<T>(MutableStateFlow(initialValue))