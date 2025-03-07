package cz.jezek.moneta.ui.player

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.jezek.moneta.data.model.Player
import cz.jezek.moneta.data.model.Team
import cz.jezek.moneta.ui.theme.MonetaTestTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Aktivita zaobrazuje detailni informace o hraci
 *
 * Parametr aktivity je id hrace
 *
 * @see PlayerViewModel
 */
@AndroidEntryPoint
class PlayerActivity : ComponentActivity() {

    val viewModel: PlayerViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // prectu si parametr z intentu
        val playerId = intent.getIntExtra("id", 0)

        // TODO asi by bylo lepsi predat parametr do view modelu v konstruktoru
        viewModel.loadPlayer(playerId);

        enableEdgeToEdge()
        setContent {
            val player by viewModel.player.collectAsState()
            MonetaTestTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.systemBars
                ) { innerPadding ->
                    PlayerInfo(
                        player = player,
                        onTeamClick = {
                            // TODO otevreni detail team
                        },
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PlayerInfo(player: Player?, onTeamClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    if (player == null) {
        Text(text = "Hráč nenalezenm")
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
                .statusBarsPadding()

        )
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
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable { onTeamClick(player.team.id) }
                )
                // college
                Text(
                    text = "Škola: ${player.college}",
                )
                // height
                Text(
                    text = "Výška: ${player.height}",
                    style = MaterialTheme.typography.bodyMedium
                )
                // weight
                Text(
                    text = "Váha: ${player.weight}",
                    style = MaterialTheme.typography.bodyMedium
                )
                // jersey_number
                Text(
                    text = "Číslo: ${player.jersey_number}",
                    style = MaterialTheme.typography.bodyMedium
                )
                // country
                Text(
                    text = "Země: ${player.country}",
                    style = MaterialTheme.typography.bodyMedium
                )
                // draft_year
                Text(
                    text = "Rok draftu: ${player.draft_year}",
                    style = MaterialTheme.typography.bodyMedium
                )
                // draft_round
                Text(
                    text = "Kolo draftu: ${player.draft_round}",
                    style = MaterialTheme.typography.bodyMedium
                )
                // draft_number
                Text(
                    text = "Číslo draftu: ${player.draft_number}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MonetaTestTheme {
        PlayerInfo(
            player = Player(
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
            ),
            onTeamClick = {},
        )

    }
    /*
     "id": 19,
        "first_name": "Stephen",
        "last_name": "Curry",
        "position": "G",
        "height": "6-2",
        "weight": "185",
        "jersey_number": "30",
        "college": "Davidson",
        "country": "USA",
        "draft_year": 2009,
        "draft_round": 1,
        "draft_number": 7,
        "team": {
            "id": 10,
            "conference": "West",
            "division": "Pacific",
            "city": "Golden State",
            "name": "Warriors",
            "full_name": "Golden State Warriors",
            "abbreviation": "GSW"
        }
     */
}