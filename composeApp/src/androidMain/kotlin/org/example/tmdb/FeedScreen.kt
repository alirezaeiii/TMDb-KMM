package org.example.tmdb

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.example.tmdb.common.TMDbTopBar
import org.example.tmdb.domain.model.FeedWrapper
import org.example.tmdb.domain.model.Movie
import org.example.tmdb.theme.Teal200
import org.example.tmdb.utils.pagerTransition
import org.example.tmdb.utils.resolve


@Composable
fun FeedScreen(
    collection: List<FeedWrapper>,
) {
    TMDbTopBar {
        FeedCollectionList(
            collection,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun FeedCollectionList(collection: List<FeedWrapper>, modifier: Modifier = Modifier) {
    LazyColumn {
        item {
            PagerTMDbItemContainer(
                item = collection.first(),
                modifier = modifier
            )
        }
        itemsIndexed(collection.drop(1)) { index, feedCollection ->
            FeedCollection(
                feedCollection = feedCollection,
                index = index
            )
        }
    }
}

@Composable
private fun PagerTMDbItemContainer(item: FeedWrapper, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { item.feeds.size })
    Header(title = item.sortTypeStringDesc.resolve(), modifier = modifier)
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) { page ->
        with(item.feeds[page]) {
            TrendingItem(
                title = name,
                imageUrl = backdropPath,
                releaseDate = releaseDate,
                modifier =
                Modifier.pagerTransition(
                    pagerState = pagerState,
                    page = page,
                ),
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) MaterialTheme.colors.primary else Teal200
            Box(
                modifier =
                Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(6.dp),
            )
        }
    }
}

@Composable
private fun TrendingItem(
    title: String,
    imageUrl: String?,
    releaseDate: String?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
        modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier =
                Modifier
                    .padding(
                        start = 12.dp,
                        bottom = 6.dp,
                    )
                    .align(Alignment.BottomStart),
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(6.dp))
                releaseDate?.let { releaseDate ->
                    Text(
                        text = releaseDate,
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
private fun FeedCollection(
    feedCollection: FeedWrapper,
    index: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(top = 32.dp)) {
        Header(title = feedCollection.sortTypeStringDesc.resolve())
        Feeds(feedCollection.feeds, index)
    }
}

@Composable
private fun Header(title: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        modifier
            .heightIn(min = 36.dp)
            .padding(start = 12.dp),
    ) {
        Text(
            text = title,
            maxLines = 1,
            color = MaterialTheme.colors.onSurface,
            modifier =
            Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start),
        )
        Text(
            text = "More",
            color = MaterialTheme.colors.onSurface,
            modifier =
            Modifier
                .align(Alignment.CenterVertically)
                .padding(12.dp)
        )
    }
}

@Composable
private fun Feeds(feeds: List<Movie>, index: Int, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 2.dp, end = 2.dp),
    ) {
        items(feeds) { feed ->
            TMDbItem(feed, index)
        }
    }
}

@Composable
fun TMDbItem(movie: Movie, index: Int) {
    val itemWidth: Dp
    val imageUrl: String?
    if (index % 3 == 0) {
        itemWidth = 220.dp
        imageUrl = movie.backdropPath
    } else {
        itemWidth = 120.dp
        imageUrl = movie.posterPath
    }
    TMDbCard(movie, imageUrl, itemWidth)
}

@Composable
fun TMDbCard(
    movie: Movie,
    imageUrl: String? = movie.posterPath,
    itemWidth: Dp = 120.dp,
) {
    Card(
        modifier =
        Modifier
            .padding(6.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = movie.name,
                modifier =
                Modifier
                    .size(width = itemWidth, height = 180.dp),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = movie.name,
                fontSize = 11.sp,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier =
                Modifier
                    .size(width = itemWidth, height = 36.dp)
                    .wrapContentHeight(),
            )
        }
    }
}