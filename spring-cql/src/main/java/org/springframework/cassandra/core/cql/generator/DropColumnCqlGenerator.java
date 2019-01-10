/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cassandra.core.cql.generator;

import static org.springframework.cassandra.core.cql.CqlStringUtils.noNull;

import org.springframework.cassandra.core.keyspace.DropColumnSpecification;

/**
 * CQL generator for generating a {@code DROP} column clause of an {@code ALTER TABLE} statement.
 *
 * @author Matthew T. Adams
 * @see DropColumnSpecification
 * @see ColumnChangeCqlGenerator
 * @see org.springframework.cassandra.core.keyspace.AlterTableSpecification
 */
public class DropColumnCqlGenerator extends ColumnChangeCqlGenerator<DropColumnSpecification> {

	public DropColumnCqlGenerator(DropColumnSpecification specification) {
		super(specification);
	}

	public StringBuilder toCql(StringBuilder cql) {
		return noNull(cql).append("DROP ").append(spec().getName());
	}
}