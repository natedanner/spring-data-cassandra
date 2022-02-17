/*
 * Copyright 2013-2022 the original author or authors.
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
package org.springframework.data.cassandra.core.convert;

import org.springframework.data.mapping.model.DefaultSpELExpressionEvaluator;
import org.springframework.data.mapping.model.SpELExpressionEvaluator;

import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;

/**
 * {@link CassandraValueProvider} to read property values from a {@link Row}.
 *
 * @author Alex Shvid
 * @author Matthew T. Adams
 * @author David Webb
 * @author Mark Paluch
 * @deprecated since 3.0, use directly {@link RowValueProvider}.
 */
@Deprecated
public class BasicCassandraRowValueProvider extends RowValueProvider {

	/**
	 * Create a new {@link BasicCassandraRowValueProvider} with the given {@link Row}, {@link CodecRegistry} and
	 * {@link SpELExpressionEvaluator}.
	 *
	 * @param source must not be {@literal null}.
	 * @param codecRegistry must not be {@literal null}.
	 * @param evaluator must not be {@literal null}.
	 * @since 2.1
	 */
	public BasicCassandraRowValueProvider(Row source, CodecRegistry codecRegistry, SpELExpressionEvaluator evaluator) {
		super(source, evaluator);
	}

	/**
	 * Create a new {@link BasicCassandraRowValueProvider} with the given {@link Row} and
	 * {@link DefaultSpELExpressionEvaluator}.
	 *
	 * @param source must not be {@literal null}.
	 * @param evaluator must not be {@literal null}.
	 * @deprecated since 2.1, use {@link #BasicCassandraRowValueProvider(Row, CodecRegistry, SpELExpressionEvaluator)}
	 */
	@Deprecated
	public BasicCassandraRowValueProvider(Row source, DefaultSpELExpressionEvaluator evaluator) {
		super(source, evaluator);
	}

}
