package com.cityxcape.cityxcape.models

import com.cityxcape.cityxcape.messages.Messages
import com.google.firebase.Timestamp

data class Message(
    val id: String,
    val fromId: String,
    val toId: String,
    val content: String,
    val imageUrl: String,
    val username: String,
    val read: Boolean,
    val date: Timestamp
) {
    companion object {
        fun createFromMap(data: Map<String, Any>) : Message {
            return Message(
                id = data["id"] as? String ?: "",
                fromId = data["fromId"] as? String ?: "",
                toId = data["toId"] as? String ?: "",
                content = data["content"] as? String ?: "",
                imageUrl = data["imageUrl"] as? String ?: "",
                username = data["username"] as? String ?: "",
                read = data["read"] as? Boolean ?: false,
                date = data["date"] as? Timestamp ?: Timestamp.now(),
            )
        }
    }
}