package data.csv

import data.dao.PeliculaDAO
import data.entities.Pelicula
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate

class CSVPeliculaDAO: PeliculaDAO {

    private val filePath: String = "src/main/resources/device.csv"

    private fun getDevicesFromFile(): List<Pelicula> {
        val fileReader = File(filePath).inputStream().bufferedReader()
        fileReader.readLine()  // reading the header of the CSV file

        return ArrayList(
            fileReader.lineSequence()
                .filter {
                    it.isNotBlank() // processing only lines that are not in blank
                }
                .map {
                    // splitting values of each line
                    val (code, name, device_category, date, price) = it.split(',')
                    val releaseDate = LocalDate.parse(date)

                    // creating a new object with the split values
                    Pelicula(code.toInt(), name, device_category, releaseDate, price.toDouble())
                }
                .toMutableList()
        )
    }

    private fun setDevicesToFile(peliculas: ArrayList<Pelicula>) {
        FileOutputStream(filePath).apply {
            val fileWriter = bufferedWriter()

            fileWriter.write("code,name,category,release_date,price")   // writing the header of the CSV file
            fileWriter.newLine()

            peliculas.forEach {
                fileWriter.write(
                    "${it.getCode()},${it.getName()},${it.getCategory()},${it.getReleaseDate()},${it.getPrice()}"
                )
                fileWriter.newLine()
            }

            fileWriter.flush()
        }
    }

    override fun getDevices(): ArrayList<Pelicula> {
        return ArrayList(getDevicesFromFile())
    }

    override fun getDevicesFromCategory(category: String): ArrayList<Pelicula> {
        return ArrayList(
            getDevicesFromFile()
                .filter {
                    it.getCategory().equals(category, true)
                }
        )
    }

    override fun getDevicesFromMaximumPrice(maximumPrice: Double): ArrayList<Pelicula> {
        return ArrayList(
            getDevicesFromFile()
                .filter {
                    maximumPrice >= it.getPrice()
                }
        )
    }

    override fun create(entity: Pelicula) {
        setDevicesToFile(ArrayList(getDevicesFromFile() + entity))
    }

    override fun read(code: Int): Pelicula {
        return ArrayList(
            getDevicesFromFile()
                .filter {
                    code == it.getCode()
                }
        )[0]
    }

    override fun update(entity: Pelicula) {
        val currentDevices = ArrayList(getDevicesFromFile())
        var counter = 0

        currentDevices.forEach {
            if (it.getCode() == entity.getCode()) {
                currentDevices[counter] = entity
            }

            counter++
        }

        setDevicesToFile(currentDevices)
    }

    override fun delete(code: Int) {
        val currentDevices = ArrayList(getDevicesFromFile())
        var foundPelicula: Pelicula? = null
        var counter = 0

        currentDevices.forEach {
            if (it.getCode() == code) {
                foundPelicula = currentDevices[counter]
            }

            counter++
        }

        currentDevices.remove(foundPelicula)
        setDevicesToFile(currentDevices)
    }

}