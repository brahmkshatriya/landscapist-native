package com.skydoves.landscapist.core.event

private fun dateNow(): Double = js("Date.now()")

internal actual fun currentTimeMillis(): Long = dateNow().toLong()
