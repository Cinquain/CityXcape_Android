package com.cityxcape.cityxcape.models



data class Location(
    val id: String,
    val name: String,
    val imageUrl: String,
    val ownerId: String,
    val worldId: String,
    val longitude: Double,
    val latitude: Double,
    val city: String,
    val isSocialHub: Boolean,
    val checkinCount: Int,
    val totalSales: Double
) {
    companion object {
        fun createFromMap(data: Map<String, Any?>) : Location {
            return Location(
                id = data["id"] as? String ?: "",
                name = data["name"] as? String ?:"",
                imageUrl = data["imageUrl"] as? String ?: "",
                ownerId = data["ownerId"] as? String ?: "",
                worldId = data["worldId"] as? String ?: "",
                city = data["city"] as? String ?: "",
                isSocialHub = data["isSocialHub"] as? Boolean ?: true,
                longitude = data["longitude"] as? Double ?: 0.0,
                latitude = data["latitude"] as? Double ?: 0.0,
                totalSales = data["totalSales"] as? Double ?: 0.0,
                checkinCount = data["checkinCount"] as? Int ?: 0
            )
        }

        fun demo() : Location {
            return Location(
                id = "ab0948756224cxyz",
                name = "Parlour Bar",
                imageUrl = "https://i.ytimg.com/vi/KM2rRv8b8aU/maxresdefault.jpg",
                ownerId = "fdhfihfdu9q0",
                worldId = "eihjfsjbsjkbdkjb",
                city = "Minneapolis",
                longitude = 44.98896841692649,
                latitude = -93.2783380443693,
                checkinCount = 28,
                totalSales = 28494.2,
                isSocialHub = true
            )
        }
    }
}