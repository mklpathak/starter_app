package com.example.core.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

class Ticker(private var delay: Long) {

    private var channel: ReceiveChannel<Unit> = Channel()

    var isStarted: Boolean = false
        private set

    fun getTicker(): ReceiveChannel<Unit> {
        return channel
    }

    fun start(scope: CoroutineScope) {
        isStarted = true
        channel.cancel()
        channel = scope.produce(capacity = 0) {
            while (true) {
                channel.send(Unit)
                delay = delay
            }
        }
    }

    fun stop() {
        isStarted = false
        channel.cancel()
        channel = Channel()
    }

    fun cancel() {
        isStarted = false
        channel.cancel()
    }
}