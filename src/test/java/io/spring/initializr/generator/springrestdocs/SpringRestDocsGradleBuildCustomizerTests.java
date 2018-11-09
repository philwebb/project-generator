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

package io.spring.initializr.generator.springrestdocs;

import java.util.List;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild.TaskCustomization;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild.TaskCustomization.Invocation;
import io.spring.initializr.generator.buildsystem.gradle.GradlePlugin;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringRestDocsGradleBuildCustomizer}.
 *
 * @author Andy Wilkinson
 */
public class SpringRestDocsGradleBuildCustomizerTests {

	private final SpringRestDocsGradleBuildCustomizer customizer = new SpringRestDocsGradleBuildCustomizer();

	@Test
	public void customizesGradleBuild() {
		GradleBuild build = new GradleBuild();
		this.customizer.customize(build);
		assertThat(build.getPlugins()).hasSize(1);
		GradlePlugin plugin = build.getPlugins().get(0);
		assertThat(plugin.getId()).isEqualTo("org.asciidoctor.convert");
		assertThat(plugin.getVersion()).isEqualTo("1.5.3");
		assertThat(build.getTaskCustomizations()).containsKey("test");
		List<TaskCustomization> testCustomizations = build.getTaskCustomizations()
				.get("test");
		assertThat(testCustomizations).hasSize(1);
		TaskCustomization testCustomization = testCustomizations.get(0);
		assertThat(testCustomization.getInvocations()).hasSize(1);
		Invocation invocation = testCustomization.getInvocations().get(0);
		assertThat(invocation.getTarget()).isEqualTo("outputs.dir");
		assertThat(invocation.getArguments()).containsExactly("snippetsDir");
		List<TaskCustomization> asciidoctorCustomizations = build.getTaskCustomizations()
				.get("asciidoctor");
		assertThat(asciidoctorCustomizations).hasSize(1);
		TaskCustomization asciidoctorCustomization = asciidoctorCustomizations.get(0);
		assertThat(asciidoctorCustomization.getInvocations()).hasSize(2);
		Invocation inputsDir = asciidoctorCustomization.getInvocations().get(0);
		assertThat(inputsDir.getTarget()).isEqualTo("inputs.dir");
		assertThat(inputsDir.getArguments()).containsExactly("snippetsDir");
		Invocation dependsOn = asciidoctorCustomization.getInvocations().get(1);
		assertThat(dependsOn.getTarget()).isEqualTo("dependsOn");
		assertThat(dependsOn.getArguments()).containsExactly("test");
	}

}
