package swagger.code.generator.gradle.plugin

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.assertTrue

/**
 * A functional test for the 'swagger.code.generator.gradle.plugin' plugin.
 */
class SwaggerCodeGeneratorGradlePluginFunctionalTest {

    @field:TempDir
    lateinit var projectDir: File

    private val buildFile by lazy { projectDir.resolve("build.gradle.kts") } // Adjusted file extension

    @Test
    fun `can run task`() {
        // Set up the test build
        buildFile.writeText("""
            plugins {
                id("swagger.code.generator.gradle.plugin.SwaggerCodeGeneratorGradlePlugin")
            }

            swaggerCodeGeneratorGradlePluginExtension {
                swaggerInputFiles {
                    create("swaggerInputFile1") {
                        file = file("src/test/resources/petstore.yaml")
                    }
                }
            }
        """.trimIndent())

        // Run the build
        val result = GradleRunner.create()
                .withProjectDir(projectDir)
                .withArguments("genApiClient") // Use the name of your task
                .withPluginClasspath()
                .forwardOutput() // This will display the output in the console
                .build()


        // Verify the result
        assertTrue(result.output.contains("BUILD SUCCESSFUL")) // Replace with the expected output message
    }
}
