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

import org.gradle.api.artifacts.CacheableRule;
import org.gradle.api.artifacts.ComponentMetadataContext;
import org.gradle.api.artifacts.ComponentMetadataRule;
import org.gradle.api.attributes.Category;
import org.gradle.api.model.ObjectFactory;
import org.gradle.util.VersionNumber;

import javax.inject.Inject;

@CacheableRule
public abstract class JacksonMetadataRule implements ComponentMetadataRule {

    private static final String PLATFORM_ID = "com.fasterxml.jackson:jackson-bom";
    private static final VersionNumber FIRST_PLATFORM_VERSION = VersionNumber.parse("2.6.7");

    public void execute(ComponentMetadataContext ctx) {
        if (VersionNumber.parse(ctx.getDetails().getId().getVersion()).compareTo(FIRST_PLATFORM_VERSION) < 0) {
            return;
        }
        addPlatformDependency(ctx);
    }

    @Inject
    abstract public ObjectFactory getObjects();

    private void addPlatformDependency(ComponentMetadataContext ctx) {
        String version = ctx.getDetails().getId().getVersion();
        ctx.getDetails().allVariants(variant -> variant.withDependencies(dependencies ->
                dependencies.add(PLATFORM_ID + ":" + version,
                        d -> d.attributes(a -> a.attribute(Category.CATEGORY_ATTRIBUTE,
                            getObjects().named(Category.class, Category.REGULAR_PLATFORM))))));
    }
}