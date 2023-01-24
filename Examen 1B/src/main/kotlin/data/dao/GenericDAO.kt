package data.dao

interface GenericDAO<T, PK> {

    fun create(entity: T)
    fun read(code: PK): T
    fun update(entity: T)
    fun delete(code: PK)

}