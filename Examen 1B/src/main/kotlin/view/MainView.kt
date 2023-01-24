package view

import kotlin.system.exitProcess

class MainView {

    private val peliculaVista = PeliculaVista()
    private val categoriaVista = CategoriaVista()

    fun startUserInterface() {
        printBanner()

        while (true) {
            processEntitySelection(getEntitySelection())
        }
    }

    private fun printBanner() {
        print("==========BIENVENIDOS=========\n"
        )
    }

    private fun getEntitySelection(): String {
        print(
            "\nSeleccione la entidad con la que desea trabajar:\n" +
            "1. Película.\n" +
            "2. Categoria.\n" +
            "0. Sal del programa\n\n" +
            "Ingrese su opción: "
        )

        return readLine()!!
    }

    private fun processEntitySelection(selection: String) {
        when(selection) {
            "1" -> do {
                val loop = peliculaVista.processDeviceOperationSelection(
                    peliculaVista.getDeviceOperationSelection()
                )
            } while(loop)
            "2" -> do {
                val loop = categoriaVista.processComponentOperationSelection(
                    categoriaVista.getComponentOperationSelection()
                )
            } while(loop)
            "0" -> exitProcess(0)
            else -> {
                println("La opción ingresada no existe.")
            }
        }
    }

}