package data.dao

import data.entities.Pelicula

interface PeliculaDAO: GenericDAO<Pelicula, Int> {

    fun getDevices(): ArrayList<Pelicula>
    fun getDevicesFromCategory(category: String): ArrayList<Pelicula>
    fun getDevicesFromMaximumPrice(maximumPrice: Double): ArrayList<Pelicula>

}