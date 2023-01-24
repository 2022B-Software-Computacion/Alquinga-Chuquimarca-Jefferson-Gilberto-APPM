package data.entities

class Categoria (
    private val code: Int,
    private var name: String,
    private var description: String,
    private var discontinued: Boolean,
    private var deviceCode: Int
    ) {

    fun getCode(): Int {
        return code
    }

    fun getName(): String {
        return name
    }

    fun getDescription(): String {
        return description
    }

    fun getDiscontinued(): Boolean {
        return discontinued
    }

    fun getDeviceCode(): Int {
        return deviceCode
    }

    fun setDiscontinued(newDiscontinued: Boolean) {
        discontinued = newDiscontinued
    }

    override fun toString(): String {
        var nameSpaces = ""
        var descriptionSpaces = ""

        for (i in (30 - name.length) downTo 0) nameSpaces += " "
        for (i in (40 - description.length) downTo 0) descriptionSpaces += " "

        return code.toString() + "\t\t" + name + nameSpaces + description + descriptionSpaces +
                discontinued + "\t\t\t\t" + deviceCode.toString()
    }

}