package view

import data.dao.DAOFactory
import data.entities.Categoria

class CategoriaVista {

    fun getComponentOperationSelection(): String {
        print(
            "\nSelect the operation you want to do:\n" +
                    "1. Insertar una nueva categoría (CREAR).\n" +
                    "2. Leer categoría por código (LEER).\n" +
                    "3. Actualizar categoría por código (ACTUALIZAR).\n" +
                    "4. Eliminar categoría por código (ELIMINAR).\n" +
                    "5. Obtener todas las categorías.\n" +
                    "6. Obtener libros por categoría.\n" +
                    "7. Obtener libros por precio máximo .\n" +
                    "0. Volver al menú inicial.\n\n" +
                    "Ingrese su opción: "
        )

        return readLine()!!
    }

    fun processComponentOperationSelection(operation: String): Boolean {
        when(operation) {
            "1" -> processComponentInsertion()
            "2" -> processComponentReading()
            "3" -> processComponentUpdate()
            "4" -> processComponentDeletion()
            "5" -> processComponentCompleteReading()
            "6" -> processComponentReadingByDeviceCode()
            "0" -> return false
            else -> {
                println("La opción ingresada no existe.")
            }
        }

        return true
    }

    private fun processComponentInsertion() {
        val componentCode: Int?
        val componentContinuity: Boolean?
        val deviceCode: Int?
        val categoria: Categoria?

        println("\n===   INSERCIÓN DE CATEGORÍA   ===\n")
        print("Ingresar el código de la categoría: ")
        componentCode = readLine()!!.toInt()

        print("Ingresar el nombre del categoría: ")
        val componentName: String? = readLine()!!

        print("Ingresar la descripción de la categoría: ")
        val componentDescription: String? = readLine()!!

        print("Ingresar la continuidad de la categoría: ")
        componentContinuity = readLine()!!.toBoolean()

        print("Ingresar el código de la película de la categoría: ")
        deviceCode = readLine()!!.toInt()

        categoria = Categoria(componentCode, componentName!!, componentDescription!!, componentContinuity, deviceCode)

        DAOFactory.factory.getComponentDAO().create(categoria)

        println("\nSe insertó correctamente una nueva categoría.")
    }

    private fun processComponentReading() {
        val componentCode: Int?

        println("\n===   LISTAR CATEGORIAS   ===\n")
        print("Ingrese el código de cátegoria que desee ver: ")
        componentCode = readLine()!!.toInt()

        println("\nCÓDIGO\tPELÍCULA\t\t\t\t\t\t   DESCRIPCIÓN\t\t\t\t\t\t\t\tDESCONTINUADO\t\tCÓDIGO DE CATEGORÍA")
        println(DAOFactory.factory.getComponentDAO().read(componentCode))
    }

    private fun processComponentUpdate() {
        val componentCode: Int?
        val componentContinuity: Boolean?
        val categoria: Categoria?

        println("\n===   ACTUALIZACIÓN DE CATEGORÍA   ===\n")
        print("Ingresar el código de categoría que desea actualizaar: ")
        componentCode = readLine()!!.toInt()

        print("Ingresar la continuidad de la cátegoria: ")
        componentContinuity = readLine()!!.toBoolean()

        categoria = DAOFactory.factory.getComponentDAO().read(componentCode)
        categoria.setDiscontinued(componentContinuity)

        DAOFactory.factory.getComponentDAO().update(categoria)

        println("\nLa categoría se actualizó correctamente.")
    }

    private fun processComponentDeletion() {
        val componentCode: Int?

        println("\n===   ELIMINACION DE CATEGORÍA   ===\n")
        print("Ingresar el código de la categoría : ")
        componentCode = readLine()!!.toInt()

        DAOFactory.factory.getComponentDAO().delete(componentCode)

        println("\n La categoría se eliminó con éxito.")
    }

    private fun processComponentCompleteReading() {
        println("\n===   LEER CATEGORÍA COMPLETAS  ===")

        printTable(DAOFactory.factory.getComponentDAO().getComponents())
    }

    private fun processComponentReadingByDeviceCode() {
        println("\n===   LEER CATEGORÍA POR CÓDIGO DE PELÍCULA   ===\n")
        print("Ingresar el código de la película de las categorías que deseea leer: ")
        val deviceCode = readLine()!!.toInt()

        printTable(DAOFactory.factory.getComponentDAO().getComponentsFromDeviceCode(deviceCode))
    }

    private fun printTable(componentsRows: ArrayList<out Any>) {
        println("\nCÓDIGO\tCATEGORÍA\t\t\t\t\t\t   DESCRIPCIÓN\t\t\t\t\t\t\t\tDESCONTINUO\t\tCÓDIGO DE PELÍCULA")
        componentsRows.forEach{
                current: Any -> println(current)
        }
    }

}