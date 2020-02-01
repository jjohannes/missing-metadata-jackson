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
package de.jjohannes.gradle.missingmetadata.jackson;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.util.VersionNumber;

class JacksonMetadataPlugin implements Plugin<Project> {
    
    public static final String[] ALL_JACKSON_MODULES = {
            // Core
            "com.fasterxml.jackson.core:jackson-annotations",
            "com.fasterxml.jackson.core:jackson-core",
            "com.fasterxml.jackson.core:jackson-databind",

            // Data Formats
            "com.fasterxml.jackson.dataformat:jackson-dataformat-avro",
            "com.fasterxml.jackson.dataformat:jackson-dataformat-cbor",
            "com.fasterxml.jackson.dataformat:jackson-dataformat-csv",
            "com.fasterxml.jackson.dataformat:jackson-dataformat-ion",
            "com.fasterxml.jackson.dataformat:jackson-dataformat-properties",
            "com.fasterxml.jackson.dataformat:jackson-dataformat-protobuf",
            "com.fasterxml.jackson.dataformat:jackson-dataformat-smile",
            "com.fasterxml.jackson.dataformat:jackson-dataformat-xml",
            "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml",

            // Data Types
            "com.fasterxml.jackson.datatype:jackson-datatype-guava",
            "com.fasterxml.jackson.datatype:jackson-datatype-hibernate3",
            "com.fasterxml.jackson.datatype:jackson-datatype-hibernate4",
            "com.fasterxml.jackson.datatype:jackson-datatype-hibernate5",
            "com.fasterxml.jackson.datatype:jackson-datatype-hppc",
            "com.fasterxml.jackson.datatype:jackson-datatype-jaxrs",
            "com.fasterxml.jackson.datatype:jackson-datatype-joda",
            "com.fasterxml.jackson.datatype:jackson-datatype-jdk8",
            "com.fasterxml.jackson.datatype:jackson-datatype-json-org",
            "com.fasterxml.jackson.datatype:jackson-datatype-jsr310",
            "com.fasterxml.jackson.datatype:jackson-datatype-jsr353",
            "com.fasterxml.jackson.datatype:jackson-datatype-pcollections",

            // JAX-RS
            "com.fasterxml.jackson.jaxrs:jackson-jaxrs-base",
            "com.fasterxml.jackson.jaxrs:jackson-jaxrs-cbor-provider",
            "com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider",
            "com.fasterxml.jackson.jaxrs:jackson-jaxrs-smile-provider",
            "com.fasterxml.jackson.jaxrs:jackson-jaxrs-xml-provider",
            "com.fasterxml.jackson.jaxrs:jackson-jaxrs-yaml-provider",

            // Jackson Jr.
            "com.fasterxml.jackson.jr:jackson-jr-all",
            "com.fasterxml.jackson.jr:jackson-jr-objects",
            "com.fasterxml.jackson.jr:jackson-jr-retrofit2",
            "com.fasterxml.jackson.jr:jackson-jr-stree",

            // Modules, basic
            "com.fasterxml.jackson.module:jackson-module-afterburner",
            "com.fasterxml.jackson.module:jackson-module-guice",
            "com.fasterxml.jackson.module:jackson-module-jaxb-annotations",
            "com.fasterxml.jackson.module:jackson-module-jsonSchema",
            "com.fasterxml.jackson.module:jackson-module-kotlin",
            "com.fasterxml.jackson.module:jackson-module-mrbean",
            "com.fasterxml.jackson.module:jackson-module-osgi",
            "com.fasterxml.jackson.module:jackson-module-parameter-names",
            "com.fasterxml.jackson.module:jackson-module-paranamer",
            "com.fasterxml.jackson.module:jackson-module-scala_2.10",
            "com.fasterxml.jackson.module:jackson-module-scala_2.11",
            "com.fasterxml.jackson.module:jackson-module-scala_2.12",
            "com.fasterxml.jackson.module:jackson-module-scala_2.13"
    };

    @Override
    public void apply(Project project) {
        if (VersionNumber.parse(project.getGradle().getGradleVersion()).compareTo(VersionNumber.parse("6.1")) < 0) {
            throw new RuntimeException("This plugin requires Gradle 6.1+");
        }
        for (String moduleId : ALL_JACKSON_MODULES) {
            project.getDependencies().getComponents().withModule(moduleId, JacksonMetadataRule.class);
        }

    }
}