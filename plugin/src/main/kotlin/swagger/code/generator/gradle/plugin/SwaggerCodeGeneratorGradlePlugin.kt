/*
 * Author: Oscar Devora
 */
package swagger.code.generator.gradle.plugin

import io.swagger.codegen.languages.SwaggerGenerator
import io.swagger.codegen.v3.ClientOptInput
import io.swagger.codegen.v3.CodegenConfig
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import io.swagger.codegen.v3.DefaultGenerator
open class SwaggerCodeGeneratorGradlePluginExtension(project: Project) {
    val swaggerInputFiles: NamedDomainObjectContainer<SwaggerInputFile>
    init {
        swaggerInputFiles = project.container(SwaggerInputFile::class.java) { name ->
            SwaggerInputFile(name)
        }
    }
    val swaggerOutputDir: File = File("build/generated-src/swagger")
    val language: String = "kotlin"
}


// Define a class for input files
open class SwaggerInputFile(val name: String) {
    var file: File? = null
}

class SwaggerCodeGeneratorGradlePlugin: Plugin<Project> {
    override fun apply(project: Project) {

        val extension = project.extensions.create("swaggerCodeGeneratorGradlePluginExtension", SwaggerCodeGeneratorGradlePluginExtension::class.java)

        project.tasks.register("genApiClient") { task ->
            task.group = "swagger"
            task.description = "Generate Api Client"
            task.inputs.files(extension.swaggerInputFiles.map { it.file })
            task.outputs.dir(extension.swaggerOutputDir)

            task.doFirst{
                project.delete(extension.swaggerOutputDir)
            }

            task.doLast {
                val swaggerInputFiles = extension.swaggerInputFiles.map { it.file }
                val swaggerOutputDir = extension.swaggerOutputDir
//                val language = extension.language

                val config = SwaggerGenerator()
                config.outputDir = swaggerOutputDir.absolutePath
                config.inputSpec

            }
        }
    }
}
