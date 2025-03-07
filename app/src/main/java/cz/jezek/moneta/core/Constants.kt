package cz.jezek.moneta.core

/**
 * Uloziste aplikacnich konstant
 *
 * URL pro Ball don't lie API
 * @property BASE_URL URL pro Ball don't lie API
 * @property PLAYERS_PER_PAGE Pocet hracu na strance
 */
object Constants {
    // URL pro Ball don't lie API (https://www.balldontlie.io/api/v1/)
    const val BASE_URL = "https://api.balldontlie.io/v1/"
    // API key pro Ball don't lie API
    // lepe by ho bylo umistit do gradle.properties a docitat jej v build.gradle
    const val API_KEY = "51b6581f-528e-48ef-8384-1ffc0d4506b0"
    // Pocet hracu na strance (35)
    const val PLAYERS_PER_PAGE = 35
}
