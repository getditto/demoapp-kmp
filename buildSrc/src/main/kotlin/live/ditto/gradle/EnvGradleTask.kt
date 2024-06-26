package live.ditto.gradle

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Custom gradle task that generates an Env class similar to the Android Gradle Plugin's BuildConfig.
 */
open class EnvGradleTask : DefaultTask() {
    @Input
    var className = "BuildConfig"

    /** This will create a subdir tree under sourceDir. */
    @Input
    var packageName = "com.example"

    /** Output directory for the generated source code. */
    @OutputDirectory
    var sourceDir: File = project.file("src/commonMain/kotlin")

    /** Debug mode passed in from gradle. */
    @Input
    @Suppress("PropertyName")
    var DEBUG = false

    /** Project version passed in from gradle property. */
    @Input
    @Suppress("PropertyName")
    var VERSION: String = ""

    /** App Identifier from the Ditto Cloud Portal. */
    @Input
    @Suppress("PropertyName")
    var DITTO_APP_ID: String = ""

    /** Offline license token from the Ditto Cloud Portal. */
    @Input
    @Suppress("PropertyName")
    var DITTO_OFFLINE_TOKEN: String = ""

    /** Online playground token from the Ditto Cloud Portal. */
    @Input
    @Suppress("PropertyName")
    var DITTO_PLAYGROUND_TOKEN: String = ""

    init {
        group = "Java"
        description = "Generates a BuildConfig class for the Java SDK"
    }

    @TaskAction
    fun generateClass() {
        if (!sourceDir.exists()) {
            sourceDir.mkdirs()
        }

        val file =
            FileSpec
                .builder(packageName, className)
                .addType(
                    TypeSpec.objectBuilder(className)
                        .addProperty(
                            PropertySpec.builder("VERSION", String::class)
                                .addModifiers(KModifier.CONST)
                                .initializer("\"$VERSION\"")
                                .build(),
                        )
                        .addProperty(
                            PropertySpec.builder("DITTO_APP_ID", String::class)
                                .addModifiers(KModifier.CONST)
                                .initializer("\"$DITTO_APP_ID\"")
                                .build(),
                        )
                        .addProperty(
                            PropertySpec.builder("DITTO_OFFLINE_TOKEN", String::class)
                                .addModifiers(KModifier.CONST)
                                .initializer("\"$DITTO_OFFLINE_TOKEN\"")
                                .build(),
                        )
                        .addProperty(
                            PropertySpec.builder("DITTO_PLAYGROUND_TOKEN", String::class)
                                .addModifiers(KModifier.CONST)
                                .initializer("\"$DITTO_PLAYGROUND_TOKEN\"")
                                .build(),
                        )
                        .build(),
                )
                // ignore autogenerated `public` modifier
                .suppressWarningTypes("RedundantVisibilityModifier")
                .indent("    ")
                .build()

        file.writeTo(sourceDir)
    }
}

// KotlinPoet emits redundant `public` and `final` modifiers. This suppresses warnings in the
// generated code. See https://stackoverflow.com/a/65846801/39207
internal fun FileSpec.Builder.suppressWarningTypes(vararg types: String): FileSpec.Builder {
    if (types.isEmpty()) {
        return this
    }

    val format = "%S,".repeat(types.count()).trimEnd(',')
    addAnnotation(
        AnnotationSpec.builder(ClassName("", "Suppress"))
            .addMember(format, *types)
            .build(),
    )

    return this
}
