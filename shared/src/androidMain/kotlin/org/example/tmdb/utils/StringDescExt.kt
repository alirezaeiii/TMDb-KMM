package org.example.tmdb.utils

import android.content.Context
import dev.icerock.moko.resources.desc.StringDesc
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StringResolver : KoinComponent {
    private val context: Context by inject()

    fun resolve(stringDesc: StringDesc): String {
        return stringDesc.toString(context)
    }
}

actual fun StringDesc.resolve(): String {
    return StringResolver().resolve(this)
}