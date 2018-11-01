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

package io.spring.initializr.generator.maven;

import io.spring.initializr.generator.BuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.git.GitIgnoreContributor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Configuration for contributions specific to the generation of a project that will use
 * Maven as its build system.
 *
 * @author Andy Wilkinson
 */
@Configuration
@ConditionalOnBuildSystem(BuildSystem.MAVEN)
public class MavenProjectGenerationConfiguration {

	@Bean
	public MavenWrapperContributor mavenWrapperContributor() {
		return new MavenWrapperContributor();
	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public GitIgnoreContributor mavenGitIgnoreContributor() {
		return new GitIgnoreContributor("classpath:maven/gitignore");
	}

}
