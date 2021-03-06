/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package br.com.caelum.vraptor.serialization;

import javax.enterprise.inject.Vetoed;
import javax.servlet.AsyncContext;

/**
 * Doesn't serialize anything
 * @author Lucas Cavalcanti
 * @author Jose Donizetti
 * @since 3.0.3
 */
@Vetoed
public class IgnoringSerializer implements SerializerBuilder {

	@Override
	public Serializer exclude(String... names) {
		return this;
	}

	@Override
	public <T> Serializer from(T object) {
		return this;
	}

	@Override
	public Serializer include(String... names) {
		return this;
	}

	@Override
	public Serializer recursive() {
		return this;
	}

	@Override
	public void serialize() {
	}

        @Override
        public void serialize(AsyncContext async) {
        }
        
	@Override
	public <T> Serializer from(T object, String alias) {
		return this;
	}

	@Override
	public Serializer excludeAll() {
		return this;
	}

        @Override
        public <T> Serializer from(T object, boolean useAsync) {
                return this;
        }

        @Override
        public <T> Serializer from(T object, String alias, boolean useAsync) {
                return this;
        }
}
