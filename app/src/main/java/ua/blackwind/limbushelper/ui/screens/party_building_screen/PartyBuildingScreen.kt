package ua.blackwind.limbushelper.ui.screens.party_building_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.CoroutineScope
import ua.blackwind.limbushelper.domain.sinner.model.Identity
import ua.blackwind.limbushelper.ui.screens.party_building_screen.model.PartyBuildingInfoPanelState
import ua.blackwind.limbushelper.ui.screens.party_building_screen.model.PartySinnerModel

@Destination
@Composable
fun PartyBuildingScreen(showSnackBar: suspend (String, String) -> SnackbarResult) {
    val viewModel = hiltViewModel<PartyBuildingScreenViewModel>()
    val party = viewModel.party.collectAsState()
    val infoPanelState by viewModel.infoPanelState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.Center) {

            PartyBuildingScreenUi(
                coroutineScope,
                party.value,
                infoPanelState,
                viewModel::onIdentitySwipe,
                viewModel::onIdentityClick,
                viewModel::onIdentityLongPress,
                showSnackBar,
                viewModel::undoDelete
            )
        }
    }
}

@Composable
fun PartyBuildingScreenUi(
    coroutineScope: CoroutineScope,
    party: List<PartySinnerModel>,
    infoPanelState: PartyBuildingInfoPanelState,
    onIdentityItemSwipe: (Identity) -> Unit,
    onIdentityItemClick: (Int) -> Unit,
    onIdentityItemLongPress: (Int, Int) -> Unit,
    showSnackBar: suspend (String, String) -> SnackbarResult,
    undoDelete: (Identity) -> Unit
) {
    if (party.isEmpty()) {
        Text(text = "Your party is empty.\nStart with filter screen and something here.")
    } else {
        Column {
            PartyBuildingInfoPanel(infoPanelState)
            Divider(
                thickness = 3.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                items(party.size, key = { it }) { index ->
                    val sinner = party[index].sinner
                    val identities = party[index].identities.sortedByDescending { it.identity.id }
                    if (identities.isNotEmpty()) {
                        PartySinnerItem(
                            coroutineScope,
                            sinner = sinner,
                            identities = identities,
                            onIdentityItemSwipe = onIdentityItemSwipe,
                            onIdentityItemClick,
                            onIdentityItemLongPress,
                            showSnackBar,
                            undoDelete
                        )
                    }
                }
            }
        }
    }
}


