package com.example.weatherapplication.model

import android.os.Parcel
import android.os.Parcelable

//data class Forecast(val date: Long, val sunrise: Long, val sunset: Long, val temp: ForecastTemp, val pressure: Float, val humidity: Int)

data class Forecast(
    val city: City?,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastList>?,
    val message: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(City::class.java.classLoader),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.createTypedArrayList(ForecastList),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(city, flags)
        parcel.writeInt(cnt)
        parcel.writeString(cod)
        parcel.writeTypedList(list)
        parcel.writeDouble(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Forecast> {
        override fun createFromParcel(parcel: Parcel): Forecast {
            return Forecast(parcel)
        }

        override fun newArray(size: Int): Array<Forecast?> {
            return arrayOfNulls(size)
        }
    }
}

data class City(
    val coord: Coord?,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Coord::class.java.classLoader),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(coord, flags)
        parcel.writeString(country)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(population)
        parcel.writeInt(timezone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(parcel: Parcel): City {
            return City(parcel)
        }

        override fun newArray(size: Int): Array<City?> {
            return arrayOfNulls(size)
        }
    }
}

data class ForecastList(
    val clouds: Int,
    val deg: Int,
    val dt: Long,
    val feels_like: FeelsLike?,
    val gust: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val snow: Double,
    val speed: Double,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp?,
    val weather: List<Weather>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readParcelable(FeelsLike::class.java.classLoader),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readParcelable(Temp::class.java.classLoader),
        parcel.createTypedArrayList(Weather)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(clouds)
        parcel.writeInt(deg)
        parcel.writeLong(dt)
        parcel.writeParcelable(feels_like, flags)
        parcel.writeDouble(gust)
        parcel.writeInt(humidity)
        parcel.writeDouble(pop)
        parcel.writeInt(pressure)
        parcel.writeDouble(rain)
        parcel.writeDouble(snow)
        parcel.writeDouble(speed)
        parcel.writeLong(sunrise)
        parcel.writeLong(sunset)
        parcel.writeParcelable(temp, flags)
        parcel.writeTypedList(weather)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ForecastList> {
        override fun createFromParcel(parcel: Parcel): ForecastList {
            return ForecastList(parcel)
        }

        override fun newArray(size: Int): Array<ForecastList?> {
            return arrayOfNulls(size)
        }
    }
}


data class FeelsLike(
    val day: Double,
    val eve: Double,
    val morn: Double,
    val night: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(day)
        parcel.writeDouble(eve)
        parcel.writeDouble(morn)
        parcel.writeDouble(night)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeelsLike> {
        override fun createFromParcel(parcel: Parcel): FeelsLike {
            return FeelsLike(parcel)
        }

        override fun newArray(size: Int): Array<FeelsLike?> {
            return arrayOfNulls(size)
        }
    }
}

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(day)
        parcel.writeDouble(eve)
        parcel.writeDouble(max)
        parcel.writeDouble(min)
        parcel.writeDouble(morn)
        parcel.writeDouble(night)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Temp> {
        override fun createFromParcel(parcel: Parcel): Temp {
            return Temp(parcel)
        }

        override fun newArray(size: Int): Array<Temp?> {
            return arrayOfNulls(size)
        }
    }
}
