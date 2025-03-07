package cz.jezek.moneta.ui.player

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cz.jezek.moneta.data.model.Player
import cz.jezek.moneta.data.repository.BalldontlieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model pro [PlayerActivity]
 *
 * Ma za ukol precist data podle id a predat je aktivite
 *
 * @see PlayerActivity
 */
@HiltViewModel
open class PlayerViewModel @Inject constructor(
    private var networkRepository: BalldontlieRepository,
) : ViewModel() {

    // privatni promenna pro seznam hracu
    private val _player = MutableStateFlow<Player?>(null)

    // publikovana promenna seznam hracu / read only
    val player: StateFlow<Player?> = _player.asStateFlow()

    /**
     * Nacteni hrace podle id
     *
     * Model nacte hrace podle id a vrati
     */
    fun loadPlayer(id: Int) {
        Log.i("PlayerViewModel", "Nacteni hrace podle id $id")

        viewModelScope.launch {
            try {
                val response = networkRepository.getPlayer(id)
                _player.update { response.data }
            } catch (e: Exception) {
                // TODO dodelat logovani do Crashlitics nebo zobrazit hlasku uzivateli
                e.printStackTrace()
            }
        }
    }

}
