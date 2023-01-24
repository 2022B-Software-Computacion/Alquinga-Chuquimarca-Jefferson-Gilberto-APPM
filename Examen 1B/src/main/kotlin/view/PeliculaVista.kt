package view

import data.dao.DAOFactory
import data.entities.Pelicula
import java.time.LocalDate

class PeliculaVista {

    fun getDeviceOperationSelection(): String {
        print(
            "\nSelect the operation you want to do:\n" +
                    "1. Insertar una nueva pelicula (CREAR).\n" +
                    "2. Leer pelicula por código (LEER).\n" +
                    "3. Actualizar pelicula por código (ACTUALIZAR).\n" +
                    "4. Eliminar pelicula por código (ELIMINAR).\n" +
                    "5. Obtener todas las peliculas.\n" +
                    "6. Obtener película por categoría.\n" +
                    "7. Obtener película por precio máximo .\n" +
                    "0. Volver al menú inicial.\n\n" +
                    "Ingrese su opción: "
        )

        return readLine()!!
    }

    fun processDeviceOperationSelection(operation: String): Boolean {
        when(operation) {
            "1" -> processDeviceInsertion()
            "2" -> processDeviceReading()
            "3" -> processDeviceUpdate()
            "4" -> processDeviceDeletion()
            "5" -> processDevicesCompleteReading()
            "6" -> processDevicesReadingByCategory()
            "7" -> processDevicesReadingByMaximumPrice()
            "0" -> return false
            else -> {
                println("La opción ingresada no existe.")
            }
        }

        return true
    }

    private fun processDeviceInsertion() {
        val deviceCode: Int?
        val devicePrice: Double?
        val pelicula: Pelicula?

        println("\n===   DEVICE INSERTION   ===\n")
        print("Ingrese el código de la Película: ")
        deviceCode = readLine()!!.toInt()

        print("Ingrese el nombre de la Película: ")
        val deviceName: String? = readLine()!!

        print("Ingrese la categoría de la Película: ")
        val deviceCategory: String? = readLine()!!

        print("Ingrese la fecha de lanzamiento de la Película: ")
        val deviceReleaseDate: LocalDate? = LocalDate.parse(readLine()!!)

        print("Ingrese el precio de la Película: ")
        devicePrice = readLine()!!.toDouble()

        pelicula = Pelicula(deviceCode, deviceName!!, deviceCategory!!, deviceReleaseDate!!, devicePrice)

        DAOFactory.factory.getDeviceDAO().create(pelicula)

        println("\n Se insertó correctamente un nueva Película.")
    }

    private fun processDeviceReading() {
        val deviceCode: Int?

        println("\n===   PELICULA LEER   ===\n")
        print("Ingrese el código de la película que desea ver: ")



            deviceCode = readLine()!!.toInt()

        if( deviceCode == readLine()!!.toInt()) {

            println("\nCÓDIGO\tPELÍCULA\t\t\t\t\t\t   CATEGORÍA\t\t\t\tFECHA DE LANZAMIENTO\t\t\tPRECIO")
            println(DAOFactory.factory.getDeviceDAO().read(deviceCode))

        if(deviceCode !== readLine()!!.toInt()){
            println("No existe Pelicula")
        }

        }
    }

    private fun processDeviceUpdate() {
        val deviceCode: Int?
        val devicePrice: Double?
        val pelicula: Pelicula?

        println("\n===   ACTUALIZAR PELÍCULA   ===\n")
        print("Ingrese el código de la Película que desea actualizar: ")
        deviceCode = readLine()!!.toInt()

        print("Ingrese el precio de la nueva Película: ")
        devicePrice = readLine()!!.toDouble()

        pelicula = DAOFactory.factory.getDeviceDAO().read(deviceCode)
        pelicula.setPrice(devicePrice)

        DAOFactory.factory.getDeviceDAO().update(pelicula)

        println("\nLa Película se actualizó correctamente.")
    }

    private fun processDeviceDeletion() {
        val deviceCode: Int?

        println("\n===   ELIMINAR PELÍCULA   ===\n")
        print("Eliminar el código de la película que desea eliminar: ")
        deviceCode = readLine()!!.toInt()

        DAOFactory.factory.getDeviceDAO().delete(deviceCode)

        println("\n La película se eliminó con éxito.")
    }

    private fun processDevicesCompleteReading() {
        println("\n===  LECTURA DE PELÍCULA COMPLETA   ===")

        printTable(DAOFactory.factory.getDeviceDAO().getDevices())
    }

    private fun processDevicesReadingByCategory() {
        println("\n===   LECTURA DE PELICULAS POR CATEGORIA  ===\n")
        print("Ingrear la categoría de películas que desea ver: ")
        val devicesCategory = readLine()!!

        printTable(DAOFactory.factory.getDeviceDAO().getDevicesFromCategory(devicesCategory))
    }

    private fun processDevicesReadingByMaximumPrice() {
        println("\n===   LISTAR PELÍCULAS POR PRECIO MÁXIMO   ===\n")
        print("Ingrese el precio máximo de las películas que desea ver: ")
        val devicesMaximumPrice = readLine()!!.toDouble()

        printTable(DAOFactory.factory.getDeviceDAO().getDevicesFromMaximumPrice(devicesMaximumPrice))
    }

    private fun printTable(devicesRows: ArrayList<out Any>) {
        println("\nCÓDIGO\tPELÍCULA\t\t\t\t\t\t   CATEGORÍA\t\t\t\tFECHA DE LANZAMIENTO\tPRECIO")
        devicesRows.forEach{
                current: Any -> println(current)
        }
    }
}