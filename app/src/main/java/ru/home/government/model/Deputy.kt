package ru.home.government.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Deputy {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("position")
    @Expose
    var position: String? = null

    @SerializedName("isCurrent")
    @Expose
    var isCurrent: Boolean? = null

    @SerializedName("factions")
    @Expose
    var factions: List<Faction>? = null

}