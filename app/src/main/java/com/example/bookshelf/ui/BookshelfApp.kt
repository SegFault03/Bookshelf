package com.example.bookshelf.ui

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.R
import com.example.bookshelf.ui.screens.HomeScreen


@OptIn(ExperimentalMaterial3Api::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun BookShelfApp(modifier: Modifier = Modifier){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            BookshelfTopAppBar(scrollBehavior = scrollBehavior)
        }
    ) { paddingValues ->
        val bookshelfViewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.bookshelfViewModelFactory)
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HomeScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.medium_large))
                    .wrapContentSize(Alignment.Center),
                bookshelfUiState = bookshelfViewModel.bookshelfUiState,
                onRetryAction = { bookshelfViewModel.loadSearchResults() },
                searchTerm = bookshelfViewModel.currentSearchQuery,
                onSearchAction = { bookshelfViewModel.initiateSearch() },
                onValueChanged = { bookshelfViewModel.updateSearchQuery(it)}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name)
            )
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior
    )
}

