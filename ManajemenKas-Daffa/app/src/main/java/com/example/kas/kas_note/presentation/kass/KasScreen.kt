package com.example.kas.kas_note.presentation.kass

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.kas.kas_note.presentation.kass.components.OrderSection
import com.example.kas.kas_note.presentation.kass.components.KasItem
import com.example.kas.kas_note.presentation.util.Screen
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun KasScreen(
    navController: NavController,
    viewModel: KasesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditKasScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primaryVariant
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add kas", tint = Color.White)
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.DarkGray)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Manajemen Kas",
                    style = MaterialTheme.typography.h4.copy(color = Color.White),
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                )
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    kasOrder = state.kasesOrder,
                    onOrderChange = {
                        viewModel.onEvent(KasesEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.kases) { kas ->
                    KasItem(
                        kas = kas,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp,)
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditKasScreen.route +
                                            "?kasId=${kas.id}&kasColor=${kas.color}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(KasesEvent.DeleteKas(kas))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(KasesEvent.RestoreKas)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}