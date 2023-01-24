package data.dao

import data.entities.Categoria

interface CategoriaDAO: GenericDAO<Categoria, Int> {

    fun getComponents(): ArrayList<Categoria>
    fun getComponentsFromDeviceCode(deviceCode: Int): ArrayList<Categoria>

}