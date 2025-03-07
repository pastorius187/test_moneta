package cz.jezek.moneta.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing team information.
 */
@Serializable
data class Team(
    @SerialName("id")
    val id: Int,

    @SerialName("conference")
    val conference: String,

    @SerialName("division")
    val division: String,

    @SerialName("city")
    val city: String,

    @SerialName("name")
    val name: String,

    @SerialName("full_name")
    val full_name: String,

    @SerialName("abbreviation")
    val abbreviation: String
) {}