package data.csv

import data.dao.CategoriaDAO
import data.entities.Categoria
import java.io.File
import java.io.FileOutputStream

class CSVCategoriaDAO: CategoriaDAO {

    private val filePath: String = "src/main/resources/component.csv"

    private fun getComponentsFromFile(): List<Categoria> {
        val fileReader = File(filePath).inputStream().bufferedReader()
        fileReader.readLine()  // reading the header of the CSV file

        return ArrayList(
            fileReader.lineSequence()
                .filter {
                    it.isNotBlank() // processing only lines that are not in blank
                }
                .map {
                    // splitting values of each line
                    val (code, name, description, discontinued, device) = it.split(',')

                    // creating a new object with the split values
                    Categoria(code.toInt(), name, description, discontinued.toBoolean(), device.toInt())
                }
                .toMutableList()
        )
    }

    private fun setComponentsToFile(categorias: ArrayList<Categoria>) {
        FileOutputStream(filePath).apply {
            val fileWriter = bufferedWriter()

            // writing the header of the CSV file
            fileWriter.write("code,name,description,discontinued,device_code_fk")
            fileWriter.newLine()

            categorias.forEach {
                fileWriter.write(
                    "${it.getCode()},${it.getName()},${it.getDescription()}," +
                            "${it.getDiscontinued()},${it.getDeviceCode()}"
                )
                fileWriter.newLine()
            }

            fileWriter.flush()
        }
    }

    override fun getComponents(): ArrayList<Categoria> {
        return ArrayList(getComponentsFromFile())
    }

    override fun getComponentsFromDeviceCode(deviceCode: Int): ArrayList<Categoria> {
        return ArrayList(
            getComponentsFromFile()
                .filter {
                    it.getDeviceCode() == deviceCode
                }
        )
    }

    override fun create(entity: Categoria) {
        setComponentsToFile(ArrayList(getComponentsFromFile() + entity))
    }

    override fun read(code: Int): Categoria {
        return ArrayList(
            getComponentsFromFile()
                .filter {
                    code == it.getCode()
                }
        )[0]
    }

    override fun update(entity: Categoria) {
        val currentComponents = ArrayList(getComponentsFromFile())
        var counter = 0

        currentComponents.forEach {
            if (it.getCode() == entity.getCode()) {
                currentComponents[counter] = entity
            }

            counter++
        }

        setComponentsToFile(currentComponents)
    }

    override fun delete(code: Int) {
        val currentComponents = ArrayList(getComponentsFromFile())
        var foundCategoria: Categoria? = null
        var counter = 0

        currentComponents.forEach {
            if (it.getCode() == code) {
                foundCategoria = currentComponents[counter]
            }

            counter++
        }

        currentComponents.remove(foundCategoria)
        setComponentsToFile(currentComponents)
    }

}