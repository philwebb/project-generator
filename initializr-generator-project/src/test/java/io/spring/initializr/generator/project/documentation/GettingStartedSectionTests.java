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

package io.spring.initializr.generator.project.documentation;

import io.spring.initializr.generator.util.template.MustacheTemplateRenderer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link GettingStartedSection}.
 *
 * @author Stephane Nicoll
 */
class GettingStartedSectionTests {

	private final MustacheTemplateRenderer renderer = new MustacheTemplateRenderer("");

	@Test
	void gettingStartedEmpty() {
		GettingStartedSection gettingStarted = newGettingStartedSection();
		assertThat(gettingStarted.isEmpty()).isTrue();
	}

	@Test
	void gettingStartedWithGuideLinkIsNotEmpty() {
		GettingStartedSection gettingStarted = newGettingStartedSection();
		gettingStarted.addGuideLink("https://example.com", "Test");
		assertThat(gettingStarted.isEmpty()).isFalse();
	}

	@Test
	void gettingStartedWithReferenceDocLinkIsNotEmpty() {
		GettingStartedSection gettingStarted = newGettingStartedSection();
		gettingStarted.addReferenceDocLink("https://example.com", "Test");
		assertThat(gettingStarted.isEmpty()).isFalse();
	}

	@Test
	void gettingStartedWithAdditionalLinkIsNotEmpty() {
		GettingStartedSection gettingStarted = newGettingStartedSection();
		gettingStarted.addAdditionalLink("https://example.com", "Test");
		assertThat(gettingStarted.isEmpty()).isFalse();
	}

	@Test
	void gettingStartedWithRequiredDependencyIsNotEmpty() {
		GettingStartedSection gettingStarted = newGettingStartedSection();
		gettingStarted.addRequiredDependency("test", "Test");
		assertThat(gettingStarted.isEmpty()).isFalse();
	}

	@Test
	void gettingStartedWithSupportingInfrastructureElementIsNotEmpty() {
		GettingStartedSection gettingStarted = newGettingStartedSection();
		gettingStarted.addSupportingInfrastructureElement("test", "Test",
				"https://example.com");
		assertThat(gettingStarted.isEmpty()).isFalse();
	}

	@Test
	void gettingStartedWithSubSectionIsNotEmpty() {
		GettingStartedSection gettingStarted = newGettingStartedSection();
		gettingStarted.addSection((writer) -> writer.println("test"));
		assertThat(gettingStarted.isEmpty()).isFalse();
	}

	private GettingStartedSection newGettingStartedSection() {
		return new GettingStartedSection(this.renderer);
	}

}
