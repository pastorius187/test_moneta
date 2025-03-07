package cz.jezek.moneta.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.jezek.moneta.core.Constants
import cz.jezek.moneta.data.model.Player
import cz.jezek.moneta.data.repository.BalldontlieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * ViewModel pro hlavni aktivitu [MainActivity]
 *
 * Dodava seznam hracu
 *
 * @see MainActivity
 */
@HiltViewModel
open class MainViewModel @Inject constructor(
    private var networkRepository: BalldontlieRepository
) : ViewModel() {

    // privatni promenna pro seznam hracu
    private val _players = MutableStateFlow<List<Player>>(emptyList())

    // publikovana promenna seznam hracu / read only
    val players: StateFlow<List<Player>> = _players.asStateFlow()

    // aktualni cislo stranky
    private var currentPage = 1

    init {

        loadPlayers()
    }

    /**
     * Nacteni seznamu hracu
     *
     * Po kazdem zavolani se zvysi cislo stranky o 1
     * a proto tato funkce stale docita dalsi a dalsi zaznamy z webu
     *
     * Nactene zaznamy lze odebirat pomoci promenne [players]
     */
    fun loadPlayers() {
        // asynchronni nacteni seznamu hracu
        Log.i("MainViewModel", "Nacteni seznamu hracu page $currentPage")
        viewModelScope.launch {
            try {
                val response = networkRepository.getPlayers(
                    page = currentPage,
                    perPage = Constants.PLAYERS_PER_PAGE
                )
                val responsePlayers = response.data
                _players.update { currentList ->
                    currentList + responsePlayers
                }
                currentPage++
            } catch (e: Exception) {
                // TODO dodelat logovani do Crashlitics nebo zobrazit hlasku uzivateli
                e.printStackTrace()
            }
        }
    }
}