package com.example.myapplication.network

import android.util.Log
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class StompManager {

    private val stompClient: StompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8080/ws/websocket")
    private val compositeDisposable = CompositeDisposable()

    fun connect(memberId: String, onMessageReceived: (String) -> Unit) {
        stompClient.lifecycle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                when (event.type) {
                    LifecycleEvent.Type.OPENED -> Log.d("StompManager", "WebSocket Connected")
                    LifecycleEvent.Type.CLOSED -> Log.d("StompManager", "WebSocket Disconnected")
                    LifecycleEvent.Type.ERROR -> Log.e("StompManager", "Error", event.exception)
                    LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> Log.w("StompManager", "Failed server heartbeat")
                }
            }.let { compositeDisposable.add(it) }

        stompClient.topic("/room/$memberId")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ message ->
                Log.d("StompManager", "Message received: ${message.payload}")
                onMessageReceived(message.payload)
            }, { error ->
                Log.e("StompManager", "Error subscribing", error)
            }).let { compositeDisposable.add(it) }

        stompClient.connect()
    }

    fun sendMessage(destination: String, message: String) {
        stompClient.send(destination, message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("StompManager", "Message sent successfully")
            }, { error ->
                Log.e("StompManager", "Error sending message", error)
            }).let { compositeDisposable.add(it) }
    }

    fun disconnect() {
        compositeDisposable.dispose()
        stompClient.disconnect()
    }
}