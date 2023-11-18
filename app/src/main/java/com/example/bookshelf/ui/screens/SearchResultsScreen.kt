@file:Suppress("DEPRECATION")

package com.example.bookshelf.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.data.testVolumeData
import com.example.bookshelf.models.Volume
import com.example.bookshelf.models.VolumeInfo
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun SearchResultsScreen(
    modifier: Modifier = Modifier,
    listOfVolumes: List<Volume> = List(5){ testVolumeData},
    searchTerm: String,
    onValueChanged: (String)->Unit,
    onSearchAction: ()->Unit,
    isLoading: Boolean = true,
){
    Box(
        modifier = modifier
    ) {
        val localDensity = LocalDensity.current
        var heightOfSearchBar by remember{
            mutableStateOf(0.dp)
        }
        SearchResultsList(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = heightOfSearchBar + dimensionResource(id = R.dimen.medium)),
            listOfVolumes = listOfVolumes,
            isLoading = isLoading
        )
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .background(MaterialTheme.colorScheme.background)
                .onGloballyPositioned {
                    heightOfSearchBar = with(localDensity) { it.size.height.toDp() }
                },
            searchTerm = searchTerm,
            onValueChanged = onValueChanged,
            onSearchAction = onSearchAction
        )

    }
}
@Composable
fun SearchResultsList(
    modifier: Modifier = Modifier,
    listOfVolumes: List<Volume> = List(5){ testVolumeData},
    isLoading: Boolean = true,
){
    LazyColumn(
        modifier = modifier,
    ){
        items(listOfVolumes){
            VolumeInfoCard(
                volume = it,
                isLoading =isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}
@Composable
fun VolumeInfoCard(
    modifier: Modifier = Modifier,
    volume: Volume = testVolumeData,
    isLoading: Boolean = true,
){
    Card(
        modifier = modifier.padding(top = dimensionResource(id = R.dimen.medium_large)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.medium)),
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.medium)),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(volume.volumeInfo?.imageLinks?.thumbnail)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.baseline_broken_image_24),
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxSize()
                    .size(24.dp)
                    .placeholder(
                        visible = isLoading,
                        highlight = PlaceholderHighlight.fade()
                    ),
                contentScale = ContentScale.Crop,
                contentDescription = volume.volumeInfo?.title
            )
            VolumeInfoCardDetails(
                volumeInfo = volume.volumeInfo,
                isLoading = isLoading,
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxSize()
                    .padding(start = dimensionResource(id = R.dimen.medium))
            )
        }
    }
}

@Composable
fun VolumeInfoCardDetails(
    modifier: Modifier = Modifier,
    volumeInfo: VolumeInfo? = testVolumeData.volumeInfo,
    isLoading: Boolean = true,
){
    val nullCase = "No info available"
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = volumeInfo?.title?:nullCase,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.fade()
                )
        )
        Text(
            text = if(volumeInfo!=null)  joinAuthors(volumeInfo.authors) else nullCase,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.small))
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.fade()
                )
        )
        Text(
            text = volumeInfo?.subtitle?:"No subtitle available",
            style = MaterialTheme.typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.small))
                .wrapContentHeight()
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.fade()
                )
        )
        Text(
            text = volumeInfo?.description?:nullCase,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.medium),
                    end = dimensionResource(id = R.dimen.small)
                )
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.fade()
                )
        )
    }
}

fun joinAuthors(
    authors: List<String?>
): String{
    var finalText = ""
    for((i, author) in authors.withIndex()){
        if(author!=null){
            finalText += if(i!=authors.size-1)
                "$author ,"
            else
                author
        }
    }
    return finalText.ifEmpty { "No info available" }
}

