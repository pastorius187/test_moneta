package cz.jezek.moneta.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing the response for players.
 *
 * @property data Player
 * @property meta Metadata about the response.
 *
 * @see Player
 * @see MetaData
 */
@Serializable
data class PlayerResponse(
    @SerialName("data")
    val data: Player,
)

