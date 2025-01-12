package org.example.tmdb.utils

import dev.icerock.moko.resources.desc.StringDesc

actual fun StringDesc.resolve(): String {
    return this.localized()
}