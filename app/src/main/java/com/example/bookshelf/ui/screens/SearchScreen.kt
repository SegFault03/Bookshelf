package com.example.bookshelf.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import com.example.bookshelf.R
@Composable
fun SearchScreen(
    @DrawableRes painterRes: Int,
    @StringRes description: Int,
    searchTerm: String,
    onValueChanged: (String)->Unit,
    onSearchAction: ()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.wrapContentSize(),
            painter = painterResource(id = painterRes),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Text(
            text = stringResource(id = description),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.medium)),
            style = MaterialTheme.typography.titleLarge
        )
        SearchBar(
            searchTerm = searchTerm,
            onValueChanged = onValueChanged,
            onSearchAction = onSearchAction
        )
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchTerm: String,
    onValueChanged: (String)->Unit,
    onSearchAction: ()->Unit
){
    OutlinedTextField(
        modifier = modifier,
        label = {
            Text(text = stringResource(id = R.string.search_text_field_value))
        },
        value = searchTerm,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.large)),
        trailingIcon = {
            IconButton(
                onClick = onSearchAction,
                enabled = searchTerm.isNotEmpty()
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    contentDescription = null
                )
            }

        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if(searchTerm.isNotEmpty()){ onSearchAction() }
            }
        ),
        onValueChange = onValueChanged
    )
}

