/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.cassandra.core.mapping;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.data.cassandra.util.SpelUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.internal.core.util.Strings;

/**
 * Strategy class to generate {@link CqlIdentifier identifier names} using {@link NamingStrategy} and contextual details
 * from entities and properties.
 *
 * @author Mark Paluch
 * @since 3.3.6
 */
class CqlIdentifierGenerator {

	private @Nullable NamingStrategy namingStrategy;

	static CqlIdentifier createIdentifier(String simpleName, boolean forceQuote) {

		if (Strings.isDoubleQuoted(simpleName)) {
			return CqlIdentifier.fromCql(simpleName);
		}

		if (forceQuote || Strings.needsDoubleQuotes(simpleName)) {
			return CqlIdentifier.fromInternal(simpleName);
		}

		return CqlIdentifier.fromCql(simpleName);
	}

	/**
	 * Generate a {@link CqlIdentifier name} using the provided name or fall back to the default {@link Function name
	 * generator} using a {@link NamingStrategy}.
	 *
	 * @param providedName the name to use if provided.
	 * @param forceQuote whether to enforce quoting.
	 * @param defaultNameGenerator the default name generator.
	 * @param source source to be used for name generation.
	 * @param spelContext the SpEL evaluation context for evaluating SpEL expressions provided through
	 *          {@code providedName}.
	 * @return the generated name.
	 */
	public <T> CqlIdentifier generate(@Nullable String providedName, boolean forceQuote,
			BiFunction<NamingStrategy, T, String> defaultNameGenerator, T source, @Nullable EvaluationContext spelContext) {

		String name;
		boolean useForceQuote = forceQuote;

		if (StringUtils.hasText(providedName)) {
			name = spelContext != null ? SpelUtils.evaluate(providedName, spelContext) : providedName;
			useForceQuote = true;
		} else {
			name = defaultNameGenerator.apply(getNamingStrategy(forceQuote), source);
		}

		Assert.state(name != null, () -> String.format("Cannot determine default name for %s", source));

		return createIdentifier(name, useForceQuote);
	}

	public void setNamingStrategy(@Nullable NamingStrategy namingStrategy) {

		Assert.notNull(namingStrategy, "NamingStrategy must not be null");

		this.namingStrategy = namingStrategy;
	}

	private NamingStrategy getNamingStrategy(boolean forceQuote) {

		if (namingStrategy == null) {
			if (forceQuote) {
				return new NamingStrategy() {};
			} else {
				return NamingStrategy.INSTANCE;
			}
		}

		return namingStrategy;
	}
}
