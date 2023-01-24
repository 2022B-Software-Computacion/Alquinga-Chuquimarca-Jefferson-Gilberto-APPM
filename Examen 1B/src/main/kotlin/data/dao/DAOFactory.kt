package data.dao

import data.csv.CSVDAOFactor

abstract class DAOFactory {

    companion object {
        var factory: DAOFactory = CSVDAOFactor()
    }

    abstract fun getDeviceDAO(): PeliculaDAO
    abstract fun getComponentDAO(): CategoriaDAO

}