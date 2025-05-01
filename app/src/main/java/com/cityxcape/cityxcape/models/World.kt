package com.cityxcape.cityxcape.models


data class World(
    val id: String,
    val name: String,
    val imageUrl: String,
    val memberName: String
) {
    companion object {

        fun CreateFromMap(data: Map<String, Any?>) : World {
            return World(
                id = data["id"] as? String ?: "",
                name = data["name"] as? String ?:  "",
                imageUrl = data["imageUrl"] as?  String ?: "",
                memberName = data["memberName"] as? String ?: "",
            )
        }
        fun sampleWorld() : World {
            return World(
                id = "fdjskdskjhdd",
                name = "Tech",
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/cityxcape-70313.appspot.com/o/Worlds%2Ftech%2FTech.png?alt=media&token=15bfa6bb-9c5f-4d07-b0d0-8a3399035f51",
                memberName = "a techy"
            )
        }

        fun sampleList() : List<World> {
            var world1 = World(
                id = "fdjskdssddsdnsdkjhdd",
                name = "Goth",
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/cityxcape-70313.appspot.com/o/Worlds%2Fgoth%2FGoth.png?alt=media&token=2a5d61e0-7495-44f5-8dae-47434f05ccef",
                memberName = "is gothic"
            )

            var world2 = World(
                id = "ksdihifhwihdwh",
                name = "Artsy",
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/cityxcape-70313.appspot.com/o/Worlds%2Fartsy%2FArtsy.png?alt=media&token=470f6281-c57c-4c73-87a2-1c848f2ec544",
                memberName = "is artsy"
            )

            var world3 = World(
                id = "usiuwhiuh38y89y32iuhd",
                name = "Scout",
                memberName = "is a scout",
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/cityxcape-70313.appspot.com/o/Worlds%2Fscout%2FScout.png?alt=media&token=5c46fe76-3367-4d69-b90b-95032d18539f"
            )

            return listOf(world1, world2, world3)
        }


    }
}