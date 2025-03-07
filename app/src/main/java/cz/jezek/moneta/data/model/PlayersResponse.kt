package cz.jezek.moneta.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing the response for players.
 *
 * @property data List of players.
 * @property meta Metadata about the response.
 *
 * @see Player
 * @see MetaData
 */
@Serializable
data class PlayersResponse(
    @SerialName("data")
    val data: List<Player>,

    @SerialName("meta")
    val meta: MetaData,
)

