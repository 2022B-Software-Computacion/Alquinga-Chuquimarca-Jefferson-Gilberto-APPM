package data.csv

import data.dao.CategoriaDAO
import data.dao.DAOFactory
import data.dao.PeliculaDAO

class CSVDAOFactor: DAOFactory() {

    override fun getDeviceDAO(): PeliculaDAO {
        return CSVPeliculaDAO()
    }

    override fun getComponentDAO(): CategoriaDAO {
        return CSVCategoriaDAO()
    }

}