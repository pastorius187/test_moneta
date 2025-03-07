package cz.jezek.moneta.data.repository

import cz.jezek.moneta.core.Constants
import cz.jezek.moneta.data.network.NBAApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository pro komunikaci s API a nacteni dat
 * z Ball don't lie API
 *
 * @param api api pro komunikaci s balldontlie API
 *
 */
@Singleton
class BalldontlieRepository @Inject constructor(private val api: NBAApi) {
    /**
     * Seznam hracu, seskupuje cteni po stranach
     *
     * @param page cislo stranky
     */
    suspend fun getPlayers(page: Int, perPage: Int) =
        api.getPlayers(page = page, perPage)

    /**
     * Detailni informace pro konkretniho hrace
     *
     * @param id id hrace
     */
    suspend fun getPlayer(id: Int) = api.getPlayer(id)

    /**
     * Detailni informace pro konkretniho teamu
     *
     * @param id id teamu
     */
    suspend fun getTeam(id: Int) = api.getTeam(id)
}