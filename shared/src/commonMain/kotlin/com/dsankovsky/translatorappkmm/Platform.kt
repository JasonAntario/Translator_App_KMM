package com.dsankovsky.translatorappkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform