package com.cityxcape.cityxcape.models

data class User(
    val id: String,
    val username: String,
    val imageUrl: String,
    val gender: Boolean,
    val city: String,
    val streetCred: Int,
    val fcmToken: String
) {
    companion object {
        fun CreateFromMap(data: Map<String, Any?>) : User {
            return User(
                id = data["id"] as? String ?: "",
                username = data["username"] as? String ?:  "",
                imageUrl = data["imageUrl"] as?  String ?: "",
                gender = data["gender"] as? Boolean ?: false,
                city = data["city"] as? String ?: "",
                streetCred = data["streetcred"] as? Int ?: 0,
                fcmToken = data["fcmToken"] as? String ?: ""
            )
        }

        fun guest() : User {
            return User(
                id = "PlMt3eOkyseQIG9VDct6",
                username = "Erica",
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/cityxcape-70313.appspot.com/o/Users%2FNwE1WVJY83RcQw4tttAkZ0Vg53Y2%2FprofileImage?alt=media&token=f1a4fd4d-f255-46a3-9d9a-89e7500b9cd1",
                gender = false,
                city = "Indianapolis",
                streetCred = 10,
                fcmToken = "dhsjishihsuhfuhfufhf"
            )
        }

        fun sampleList() : List<User> {
            var user1 = User(
                id = "orjoifhohd984308240984098uihffo",
                username = "Erica",
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/cityxcape-70313.appspot.com/o/Users%2FNwE1WVJY83RcQw4tttAkZ0Vg53Y2%2FprofileImage?alt=media&token=f1a4fd4d-f255-46a3-9d9a-89e7500b9cd1",
                gender = false,
                city = "Indianapolis",
                streetCred = 10,
                fcmToken = "dhsjishihsuhfuhfufhf"
            )

            var user2 = User(
                id = "PlMt3eOkyseQIG9VDct6",
                username = "Adam",
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/cityxcape-70313.appspot.com/o/Users%2FJAv8CbZcZDUKNQx0PeGusp8cINC2%2FprofileImage?alt=media&token=eb587fbc-3461-4060-b6fb-73c0d10b7749",
                gender = true,
                city = "Los Angeles",
                streetCred = 50,
                fcmToken = "dhsjishihsiudgiufgigfsuhfuhfufhf"
            )

            var user3 = User(
                id = "orjoif83797947hohduihffo",
                username = "James",
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/cityxcape-70313.appspot.com/o/Users%2FzuCiRbMq8EXbQmzJwP92OjjVeBZ2%2FprofileImage?alt=media&token=462898db-8a12-4296-a132-77f362763dfa",
                gender = true,
                city = "Minneapolis",
                streetCred = 100,
                fcmToken = "dhsdsyggdhgsdhgfjishihsuhfuhfufhf"
            )
            return listOf(user1, user2, user3)
        }

    }
}