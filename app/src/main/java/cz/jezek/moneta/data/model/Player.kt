package cz.jezek.moneta.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing a player.
 */
@Serializable
data class Player(
    @SerialName("id")
    val id: Int,

    @SerialName("first_name")
    val first_name: String,

    @SerialName("last_name")
    val last_name: String,

    @SerialName("position")
    val position: String,

    @SerialName("height")
    val height: String,

    @SerialName("weight")
    val weight: String,

    @SerialName("jersey_number")
    val jersey_number: String,

    @SerialName("college")
    val college: String?,

    @SerialName("country")
    val country: String,

    @SerialName("draft_year")
    val draft_year: Int,

    @SerialName("draft_round")
    val draft_round: Int,

    @SerialName("draft_number")
    val draft_number: Int,

    @SerialName("team")
    val team: Team
)
