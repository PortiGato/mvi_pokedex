package com.example.mvi_pokedex.data.network.repository

import com.example.mvi_pokedex.data.network.response.PokemonResponse
import com.example.mvi_pokedex.data.network.response.PokemonListResponse
import com.example.mvi_pokedex.data.network.service.PokemonListService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
class PokemonListRepository @Inject constructor(
    var pokemonListService: PokemonListService,
    //var warehouseDAO: WarehouseDAO
) {

//    suspend fun syncWarehouses(
//        limit: Int = 20,offset: Int = 20
//    ): Result<Boolean> {
//        val response = pokemonListService.getWarehouseList(minDate)
//        return if (response.code == 200) {
//            val itemType = object : TypeToken<List<WarehouseResponse>>() {}.type
//            val listWarehouses =
//                Gson().fromJson<List<WarehouseResponse>>(
//                    response.responseBody,
//                    itemType
//                )
//            Result.success(true)
//        } else {
//            Result.failure(Exception("Error en la solicitud " + response.message))
//        }
//    }


    suspend fun getPokemonListOnline(
        limit: Int = 20,offset: Int = 20
    ): Result<PokemonListResponse> {

        val response = pokemonListService.getPokemonList(limit,offset)
        return if (response != null) {
            Result.success(response)
        } else {
            Result.failure(Exception("Error en la solicitud"))
        }
    }


////ROOM methods
//
//    /**
//     * Insert warehouseEntity in local database
//     *
//     * @param warehouseEntity Entity to insert
//     * @return Result of insert
//     */
//    fun insert(warehouseEntity: WarehouseEntity) =
//        warehouseDAO.insert(warehouseEntity)
//
//
//    /**
//     * Insert a list of WarehouseEntity in local database
//     *
//     * @param listWarehouseEntity List of entities to insert
//     * @return Result of insert
//     */
//    fun insertList(listWarehouseEntity: List<WarehouseEntity>) =
//        warehouseDAO.insert(listWarehouseEntity)
//
//
//    /**
//     * Delete WarehouseEntity from local database
//     *
//     * @param warehouseEntity  Entity to delete
//     * @return Result of delete
//     */
//    fun deleteList(warehouseEntity: WarehouseEntity) =
//        warehouseDAO.delete(warehouseEntity)
//
//
//    /**
//     * Delete a list of Warehouse from local database
//     *
//     * @param listWarehouseEntity List of entities to delete
//     * @return Result of delete
//     */
//    fun deleteList(listWarehouseEntity: List<WarehouseEntity>) =
//        warehouseDAO.delete(listWarehouseEntity)
//
//
//    /**
//     * Get a WarehouseEntity by id
//     *
//     * @param id Id of Warehouse
//     * @return  Warehouse with id
//     */
//    fun getById(id: Long): Flow<WarehouseEntity> =
//        warehouseDAO.getById(idWarehouse = id)
//
//
//    /**
//     * Get all WarehouseEntities from local database
//     *
//     * @return All Warehouses from local database
//     */
//    fun getAll(): List<WarehouseEntity> = warehouseDAO.getAll()
//
//
//    /**
//     * Get count WarehouseEntity in local database
//     *
//     * @return Operation Result
//     */
//    fun getCount(): Flow<Int> =
//        warehouseDAO.getCount()
//
//    /**
//     * Get lastest update date of WarehouseEntity in local database
//     *
//     * @return Operation Result
//     */
//    fun getLastDownloadDate(): Long = warehouseDAO.getMaxDate() ?: 0L
}