package ru.cookedapp.common.resourceProvider

import android.content.Context

class ResourceProviderImpl(
    private val context: Context,
): ResourceProvider {

    override fun getString(id: Int): String = context.resources.getString(id)

    override fun getString(id: Int, vararg formatArguments: Any): String {
        return context.resources.getString(id, *formatArguments)
    }

    override fun getStringArray(id: Int): List<String> {
        return context.resources.getStringArray(id).toList()
    }
}
