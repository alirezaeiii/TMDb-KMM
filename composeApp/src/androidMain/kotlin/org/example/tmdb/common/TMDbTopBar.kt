package org.example.tmdb.common

import androidx.compose.foundation.layout.PaddingValues
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TMDbTopBar(scrollContent: @Composable (PaddingValues) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopBar("TMDb", scrollBehavior)
        }
    ) {
        scrollContent(it)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CenterAlignedTopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIcon: @Composable () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colors.background,
            scrolledContainerColor = MaterialTheme.colors.background
        ),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface
            )
        },
        navigationIcon = {
            navigationIcon.invoke()
        },
        scrollBehavior = scrollBehavior
    )
}