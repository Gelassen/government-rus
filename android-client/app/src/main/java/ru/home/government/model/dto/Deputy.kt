package ru.home.government.model.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Deputy() : Parcelable {
    @SerializedName("id")
    @Expose
    var id: String? = ""

    @SerializedName("name")
    @Expose
    var name: String? = ""

    @SerializedName("position")
    @Expose
    var position: String? = ""

    @SerializedName("isCurrent")
    @Expose
    var isCurrent: Boolean? = false

    @SerializedName("factions")
    @Expose
    var factions: List<Faction>? = Collections.emptyList()

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        position = parcel.readString()
        isCurrent = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        factions = parcel.createTypedArrayList(Faction)
    }

    constructor(id: String, name: String, position: String, isCurrent: Boolean, fractions: List<Faction>): this() {
        this.id = id
        this.name = name
        this.position = position
        this.isCurrent = isCurrent
        this.factions = fractions
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(position)
        parcel.writeValue(isCurrent)
        parcel.writeTypedList(factions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Deputy> {
        override fun createFromParcel(parcel: Parcel): Deputy {
            return Deputy(parcel)
        }

        override fun newArray(size: Int): Array<Deputy?> {
            return arrayOfNulls(size)
        }
    }

}