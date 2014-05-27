/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.core.gateway;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public abstract class EntityToModelConverter<S, T> implements Converter<S, T> {

  public List<T> convertList(List<S> sourceList) {
    return FluentIterable.from(convertIterable(sourceList)).toList();
  }

  public Iterable<T> convertIterable(Iterable<S> sourceIterable) {
    return Iterables.transform(sourceIterable,
      new Function<S, T>() {
        @Override
        public T apply(S source) {
          return convert(source);
        }
      }
    );
  }

}
