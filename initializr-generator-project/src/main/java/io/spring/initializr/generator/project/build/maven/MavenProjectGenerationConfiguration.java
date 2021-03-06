/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.initializr.generator.project.build.maven;

import java.util.List;
import java.util.stream.Collectors;

import io.spring.initializr.generator.ProjectDescription;
import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.maven.ConditionalOnMaven;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.packaging.war.ConditionalOnWarPackaging;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.project.build.BuildCustomizer;
import io.spring.initializr.generator.project.scm.git.GitIgnoreContributor;
import io.spring.initializr.generator.util.LambdaSafe;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;

/**
 * Configuration for contributions specific to the generation of a project that will use
 * Maven as its build system.
 *
 * @author Andy Wilkinson
 */
@ProjectGenerationConfiguration
@ConditionalOnMaven
public class MavenProjectGenerationConfiguration {

	@Bean
	public MavenWrapperContributor mavenWrapperContributor() {
		return new MavenWrapperContributor();
	}

	@Bean
	public GitIgnoreContributor mavenGitIgnoreContributor() {
		return new GitIgnoreContributor("classpath:maven/gitignore");
	}

	@Bean
	public MavenBuild mavenBuild(ObjectProvider<BuildCustomizer<?>> buildCustomizers) {
		MavenBuild mavenBuild = new MavenBuild();
		customizeBuild(buildCustomizers, mavenBuild);
		return mavenBuild;
	}

	@Bean
	public BuildCustomizer<MavenBuild> defaultMavenConfigurationContributor(
			ProjectDescription projectDescription) {
		return (mavenBuild) -> {
			mavenBuild.setName(projectDescription.getName());
			mavenBuild.setDescription(projectDescription.getDescription());
			mavenBuild.setProperty("java.version", projectDescription.getJavaVersion());
			mavenBuild.plugin("org.springframework.boot", "spring-boot-maven-plugin");
		};
	}

	@Bean
	public MavenBuildProjectContributor mavenBuildProjectContributor(
			MavenBuild mavenBuild, IndentingWriterFactory indentingWriterFactory) {
		return new MavenBuildProjectContributor(mavenBuild, indentingWriterFactory);
	}

	@SuppressWarnings("unchecked")
	private void customizeBuild(ObjectProvider<BuildCustomizer<?>> buildCustomizers,
			MavenBuild mavenBuild) {
		List<BuildCustomizer<? extends Build>> customizers = buildCustomizers
				.orderedStream().collect(Collectors.toList());
		LambdaSafe.callbacks(BuildCustomizer.class, customizers, mavenBuild)
				.invoke((customizer) -> customizer.customize(mavenBuild));
	}

	@Bean
	@ConditionalOnWarPackaging
	public BuildCustomizer<MavenBuild> mavenWarPackagingConfigurer() {
		return (build) -> build.setPackaging("war");
	}

}
