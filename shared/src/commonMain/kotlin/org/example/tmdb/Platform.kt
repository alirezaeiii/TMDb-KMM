package org.example.tmdb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform