package cz.jezek.moneta.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cz.jezek.moneta.ui.theme.MonetaTestTheme
import dagger.hilt.android.AndroidEntryPoint
import cz.jezek.moneta.core.Constants
import cz.jezek.moneta.data.model.Player
import cz.jezek.moneta.data.model.Team
import cz.jezek.moneta.ui.player.PlayerActivity

/**
 * Hlavní aktivita aplikace.
 *
 * obsahuje seznam hracu. Hraci jsou nacitany po 35 kusech
 * Pocet hracu je definovan v objektu [Constants]
 *
 * @see MainViewModel
 *
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val players by viewModel.players.collectAsState()
            MonetaTestTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.safeDrawing
                ) { innerPadding ->
                    PlayerList(
                        players = players,
                        onPlayerClick = {
                            Log.d("MainActivity", "Klik na hráče: ${it.first_name}")
                            // otevreni obrazovky s detailnimi informaci o hraci
                            val intent = Intent(this, PlayerActivity::class.java)
                            intent.putExtra("id", it.id)
                            startActivity(intent)
                        },
                        onLoadMore = {
                            viewModel.loadPlayers()
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Seznam hracu podle [MainViewModel.players],
 *
 * Seznam je tvoren hraci, jeden hrac za druhym pod sebou.
 * Je to nekonecny seznam, ktery se automatickz docita po scrolaci
 *
 * @param players seznam hracu
 * @param modifier modifikace
 */
@Composable
fun PlayerList(
    players: List<Player>,
    onPlayerClick: (Player) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {

    // zapamatuj si seznam
    val listState = rememberLazyListState()
    // zapamatuj si pocet polozek v seznamu hracu
    val playerCountState by rememberUpdatedState(players.size)
    // je potreba kontrolovat, ze docitani seznamu se provede pouze jednou
    var isLoading by remember { mutableStateOf(false) }

    // a sleduj seznam, v pripade nutnosti si rekni o dalsi data
    LaunchedEffect(listState, playerCountState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                if (players.isNotEmpty() &&
                    visibleItems.isNotEmpty() &&
                    visibleItems.last().index >= (playerCountState - 5) &&
                    !isLoading
                ) {
                    isLoading = true
                    onLoadMore() // Načti další hráče, když zbývá méně než 5 položek
                }
            }
    }

    // Po úspěšném načtení dat, tzn. po zmene poctu hracu,  resetujeme isLoading
    LaunchedEffect(players.size) {
        isLoading = false
    }

    LazyColumn(
        // sledovani poctu v seznamu
        state = listState,
        modifier = modifier
    ) {
        items(players.size) { index ->
            PlayerItem(player = players[index], index = index, onPlayerClick = onPlayerClick)
        }
    }
}

/**
 * Polozka seznamu hracu
 *
 * Detail o hraci [Player]
 *
 * musi obsahovat - jmeno,  prijemni, pozici a klub
 *
 * @param player hrac
 * @param onPlayerClick akce po kliknuti na hrace
 * @param index index polozky
 */
@Composable
fun PlayerItem(player: Player, onPlayerClick: (Player) -> Unit, index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onPlayerClick(player) })
    {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${player.first_name} ${player.last_name}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Pozice: ${player.position ?: "Neznámá"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Tým: ${player.team.full_name}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
/*

@Composable
fun PlayerItem(player: Player, onPlayerClick: (Player) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onPlayerClick(player) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${player.first_name} ${player.last_name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Pozice: ${player.position ?: "Neznámá"}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Tým: ${player.team.full_name}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun PlayerDetail(player: Player) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "${player.first_name} ${player.last_name}", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Pozice: ${player.position ?: "Neznámá"}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Tým: ${player.team.full_name}", style = MaterialTheme.typography.bodyLarge)
    }
}

// --- Main Activity ---
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: NBAViewModel = hiltViewModel()
            PlayerList(viewModel) { player ->
                startActivity(
                    Intent(this, PlayerDetailActivity::class.java).apply {
                        putExtra("player", player)
                    }
                )
            }
        }
    }
}

// --- PlayerDetailActivity ---
@AndroidEntryPoint
class PlayerDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val player = intent.getParcelableExtra<Player>("player")
        setContent {
            player?.let { PlayerDetail(it) }
        }
    }
}

 */

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp,dpi=240")
@Composable
fun Preview() {
    val players: List<Player> = listOf(
        Player(
            id = 19,
            first_name = "Stephen",
            last_name = "Curry",
            position = "G",
            height = "6-2",
            weight = "185",
            jersey_number = "30",
            college = "Davidson",
            country = "USA",
            draft_year = 2009,
            draft_round = 1,
            draft_number = 7,
            team = Team(
                id = 10,
                conference = "West",
                division = "Pacific",
                city = "Golden State",
                name = "Warriors",
                full_name = "Golden State Warriors",
                abbreviation = "GSW"
            )
        )
    )
    MonetaTestTheme {
        PlayerList(players = players, onPlayerClick = {}, onLoadMore = {})
    }
}


/*

// --- UI ---
@Composable
fun PlayerList(viewModel: NBAViewModel, onPlayerClick: (Player) -> Unit) {
    val players by viewModel.players.collectAsState()

}

@Composable
fun PlayerItem(player: Player, onPlayerClick: (Player) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onPlayerClick(player) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${player.first_name} ${player.last_name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Pozice: ${player.position ?: "Neznámá"}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Tým: ${player.team.full_name}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun PlayerDetail(player: Player) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "${player.first_name} ${player.last_name}", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Pozice: ${player.position ?: "Neznámá"}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Tým: ${player.team.full_name}", style = MaterialTheme.typography.bodyLarge)
    }
}

// --- Main Activity ---
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: NBAViewModel = hiltViewModel()
            PlayerList(viewModel) { player ->
                startActivity(
                    Intent(this, PlayerDetailActivity::class.java).apply {
                        putExtra("player", player)
                    }
                )
            }
        }
    }
}
 */