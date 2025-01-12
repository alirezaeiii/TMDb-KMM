package org.example.tmdb.domain.model

import dev.icerock.moko.resources.desc.StringDesc

class FeedWrapper(
    val feeds: List<Movie>,
    val sortTypeStringDesc: StringDesc,
    val sortType: SortType
)