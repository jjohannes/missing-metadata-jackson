/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.jjohannes.gradle.missingmetadata.jackson

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Unroll

import static de.jjohannes.gradle.missingmetadata.jackson.JacksonMetadataPlugin.ALL_JACKSON_MODULES

class JacksonClasspathTest extends Specification {

    @Rule
    TemporaryFolder testFolder = new TemporaryFolder()

    public static final String[] JACKSON_MODULES = ALL_JACKSON_MODULES -
            'com.fasterxml.jackson.module:jackson-module-scala_2.13' - // only in 2.10+
            'com.fasterxml.jackson.dataformat:jackson-dataformat-ion'  // only in 2.9+

    static final String[] JACKSON_VERSIONS = [
        // '2.6.7',
        // '2.7.8',
        // '2.7.9',
        // '2.8.3',
        '2.8.4',
        '2.8.5',
        '2.8.6',
        '2.8.7',
        '2.8.8',
        '2.8.9',
        '2.8.10',
        '2.8.11',
        '2.9.0',
        '2.9.1',
        '2.9.2',
        // '2.9.3', <-- some scala modules are not published
        '2.9.4',
        '2.9.5',
        '2.9.6',
        '2.9.7',
        '2.9.8',
        '2.9.9',
        '2.9.10',
        '2.10.0',
        '2.10.1',
        '2.10.2',
        '2.10.3',
        '2.10.4',
        '2.10.5',
        '2.11.1',
        '2.11.2',
        '2.11.3',
     ]

    def setup() {
        testFolder.newFile('settings.gradle.kts') << 'rootProject.name = "test-project"'
    }

    @Unroll
    def "Jackson #version with version constraint for #module"() {
        given:
        testFolder.newFile("build.gradle.kts") << """
            plugins {
                `java-library`
                id("de.jjohannes.missing-metadata-jackson")
            }

            repositories {
                mavenCentral()
            }

            dependencies {
                constraints {
                    implementation("$module:$version") 
                }
                ${JACKSON_MODULES.collect { """implementation("$it")\n                """}.join('')}
            }

            tasks.register("printJars") {
                doLast {
                    configurations.compileClasspath.files.filter { it.name.startsWith("jackson-") }.forEach { println(it.name) }
                }
            }
        """

        expect:
        expectedClasspath(version.toString()) == buildClasspath()

        where:
        [module, version] << [ JACKSON_MODULES, JACKSON_VERSIONS ].combinations()
    }

    static Set<String> expectedClasspath(String version) {
        return JACKSON_MODULES.collect {
            "${it.split(':')[1]}-${version}.jar".toString()
        } + 'jackson-core-asl-1.9.13.jar' + 'jackson-mapper-asl-1.9.13.jar' as SortedSet
    }

    Set<String> buildClasspath() {
        build('printJars').output.split('\n') as SortedSet
    }

    BuildResult build(String... args) {
        gradleRunnerFor(args as List).build()
    }

    GradleRunner gradleRunnerFor(List<String>  args) {
        GradleRunner.create()
                .forwardOutput()
                .withPluginClasspath()
                .withProjectDir(testFolder.root)
                .withArguments(args + ['-q', '-s'])
    }
}
