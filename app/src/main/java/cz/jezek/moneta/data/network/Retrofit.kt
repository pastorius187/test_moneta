package cz.jezek.moneta.data.network

import cz.jezek.moneta.core.Constants
import cz.jezek.moneta.data.model.Player
import cz.jezek.moneta.data.model.PlayerResponse
import cz.jezek.moneta.data.model.PlayersResponse
import cz.jezek.moneta.data.model.Team
import cz.jezek.moneta.data.model.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Endpoint pro cteni seznamu hracu a dalsi informaci z API
 *
 * @see https://www.balldontlie.io/
 */
interface NBAApi {
    /**
     * Precist z API seznam hracu. Ctou se postupne vsichni hraci, na jedno volani
     * se precte tolik hracu, kolik se vejde na jednu stranku [Constants.PLAYERS_PER_PAGE]
     */
    @GET("players")
    suspend fun getPlayers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PlayersResponse

    /**
     * Detailni informace pro konkretniho hrace
     *
     * @param id id hrace
     */
    @GET("players/{id}")
    suspend fun getPlayer(@Path("id") id: Int): PlayerResponse

    /**
     * Detailni informace pro konkretniho teamu
     *
     * @param id id teamu
     */
    @GET("teams/{id}")
    suspend fun getTeam(@Path("id") id: Int): TeamResponse
}
