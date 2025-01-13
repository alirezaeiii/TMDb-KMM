package org.example.tmdb.utils

import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import org.example.tmdb.MR

fun getNetworkFailed() = StringDesc.Resource(MR.strings.network_failed).resolve()

fun getRequestFailed() = StringDesc.Resource(MR.strings.request_failed).resolve()

fun getServerFailed() = StringDesc.Resource(MR.strings.sever_failed).resolve()

fun getUnexpectedError() = StringDesc.Resource(MR.strings.unexpected_error).resolve()

fun getTrendingText() = StringDesc.Resource(MR.strings.text_trending)

fun getPopularText() = StringDesc.Resource(MR.strings.text_popular)

fun getHighestRateText() = StringDesc.Resource(MR.strings.text_highest_rate)

fun getDiscoverText() = StringDesc.Resource(MR.strings.text_discover)

fun getNowPlayingText() = StringDesc.Resource(MR.strings.text_now_playing)

fun getUpcomingText() = StringDesc.Resource(MR.strings.text_upcoming)

fun getMoreItemText() = StringDesc.Resource(MR.strings.more_item).resolve()

fun getRetryText() = StringDesc.Resource(MR.strings.retry).resolve()