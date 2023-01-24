package data.entities

import java.time.LocalDate

class Pelicula(

    private val code: Int,
    private var name: String,
    private var category: String,
    private val releaseDate: LocalDate,
    private var price: Double
    ) {

    fun getCode(): Int {
        return code
    }

    fun getName(): String {
        return name
    }

    fun getCategory(): String {
        return category
    }

    fun getReleaseDate(): LocalDate {
        return releaseDate
    }

    fun getPrice(): Double {
        return price
    }

    fun setPrice(newPrice: Double) {
        price = newPrice
    }

    override fun toString(): String {
        var nameSpaces = ""
        var categorySpaces = ""

        for (i in (30 - name.length) downTo 0) nameSpaces += " "
        for (i in (20 - category.length) downTo 0) categorySpaces += " "

        return code.toString() + "\t\t" + name + nameSpaces + category + categorySpaces + releaseDate + "\t\t" + price
    }

}