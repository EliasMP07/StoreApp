@file:OptIn(ExperimentalMaterial3Api::class)

package com.devdroid07.storeapp.store.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devdroid07.storeapp.R
import com.devdroid07.storeapp.core.presentation.designsystem.StoreAppTheme
import com.devdroid07.storeapp.core.presentation.designsystem.components.ShimmerListProductItem
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreScaffold
import com.devdroid07.storeapp.core.presentation.designsystem.components.StoreToolbar
import com.devdroid07.storeapp.core.presentation.designsystem.components.utils.isScrolled
import com.devdroid07.storeapp.store.presentation.home.componets.ItemCardRecomendation
import com.devdroid07.storeapp.store.presentation.home.componets.ItemProduct

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    val lazyGridState = rememberLazyGridState()
    StoreScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topAppBar = {
            StoreToolbar(
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                text = {
                    Text(
                        text = stringResource(R.string.my_cart),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.ShoppingCart,
                        contentDescription = "Icono de agregar nueva materia",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                onClick = {

                },
                expanded = lazyGridState.isScrolled()
            )
        }
    ) {
        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(20.dp),
            contentPadding = PaddingValues(10.dp)
        ) {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Welcome",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "Search product",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    Text(
                        text = "Our Fashions App",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        )
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    ItemCardRecomendation()
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Categories",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(
                        listOf(
                            "electronics",
                            "jewelery",
                            "men's clothing",
                            "women's clothing"
                        )
                    ) { category ->
                        FilterChip(
                            selected = false,
                            shape = CircleShape,
                            onClick = {

                            },
                            label = {
                                Text(category, style = MaterialTheme.typography.titleSmall)
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Text(text = "Top Dresses", style = MaterialTheme.typography.titleMedium)
            }
            items(10) {
                ShimmerListProductItem(
                    isLoading = false,
                    contentAfterLoading = { ItemProduct() }
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    StoreAppTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}