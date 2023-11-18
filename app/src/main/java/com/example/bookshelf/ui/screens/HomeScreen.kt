package com.example.bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bookshelf.R
import com.example.bookshelf.ui.BookshelfUiState

@Composable
fun HomeScreen(
    bookshelfUiState: BookshelfUiState,
    onRetryAction: ()->Unit,
    searchTerm: String,
    onValueChanged: (String)->Unit,
    onSearchAction: ()->Unit,
    modifier: Modifier = Modifier
){
    when(bookshelfUiState){
        is BookshelfUiState.Idle -> {
            SearchScreen(
                painterRes = R.drawable.book_stack,
                description = R.string.search_screen_idle_text,
                searchTerm = searchTerm,
                onValueChanged = onValueChanged,
                onSearchAction = onSearchAction,
                modifier = modifier
                )
        }
        is BookshelfUiState.Failure -> {
            SearchScreen(
                painterRes = R.drawable.search_failed,
                description = R.string.search_screen_no_results_returned,
                searchTerm = searchTerm,
                onValueChanged = onValueChanged,
                onSearchAction = onSearchAction,
                modifier = modifier
            )
        }
        is BookshelfUiState.Error -> {
            ErrorScreen(
                errorText = bookshelfUiState.errorInfo,
                onRetryAction = onRetryAction,
                modifier = modifier
            )
        }
        is BookshelfUiState.Success -> {
            SearchResultsScreen(
                listOfVolumes = bookshelfUiState.searchResults,
                isLoading = false,
                searchTerm = searchTerm,
                onValueChanged = onValueChanged,
                onSearchAction = onSearchAction,
                modifier = modifier
            )
        }
        else -> {
            SearchResultsScreen(
                isLoading = true,
                searchTerm = searchTerm,
                onValueChanged = onValueChanged,
                onSearchAction = onSearchAction,
                modifier = modifier
            )
        }
    }
}



@Composable
fun ErrorScreen(
    errorText: String,
    onRetryAction: ()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = stringResource(id = R.string.error_text),
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = stringResource(id = R.string.error_text),
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.medium))
        )
        Text(
            text = errorText,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.medium))
        )
        Button(
            onClick = onRetryAction,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.medium))
            ) {
            Text(
                text = stringResource(id = R.string.retry_button),
            )
        }
    }

}