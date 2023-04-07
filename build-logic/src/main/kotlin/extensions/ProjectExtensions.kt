package extensions

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

internal fun Project.android(configure: BaseExtension.() -> Unit) =
    extensions.configure("android", configure)
