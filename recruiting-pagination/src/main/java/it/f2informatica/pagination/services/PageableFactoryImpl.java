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
package it.f2informatica.pagination.services;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PageableFactoryImpl implements PageableFactory {
	private static final String UNIQUE_PREFIX = "unique";

	@Override
	public Pageable getPageable(QueryParameters parameters) {
		int page = parameters.getPage(), size = parameters.getSize();
		Optional<Sort> sort = getSort(parameters);
		return sort.isPresent() ? new PageRequest(page, size, sort.get()) : new PageRequest(page, size);
	}

	@Override
	public Optional<Sort> getSort(QueryParameters parameters) {
		final String sortColumn = parameters.getSortColumn();
		final String sortDirection = parameters.getSortDirection();

		return (StringUtils.hasText(sortColumn) && StringUtils.hasText(sortDirection))
			? Optional.of(new Sort(getDirection(sortDirection), suppressUniquePrefixIfAny(sortColumn)))
			: Optional.<Sort>absent();
	}

	@Override
	public Sort.Direction getDirection(String sortDirection) {
		Optional<Sort.Direction> direction = Optional.fromNullable(Sort.Direction.fromStringOrNull(sortDirection));
		return direction.isPresent() ? direction.get() : Sort.DEFAULT_DIRECTION;
	}

	private static String suppressUniquePrefixIfAny(String sortColumn) {
		final String separator = "_";
		if (sortColumn.contains(UNIQUE_PREFIX)) {
			List<String> realComposedField = Lists.newArrayList();
			for (String fakeField : sortColumn.split(separator)) {
				if (fakeField.startsWith(UNIQUE_PREFIX)) {
					realComposedField.add(fakeField.substring(UNIQUE_PREFIX.length()).toLowerCase());
					continue;
				}
				realComposedField.add(fakeField);
			}
			return org.apache.commons.lang3.StringUtils.join(realComposedField, separator).replace(separator, ".");
		}
		return sortColumn.replace(separator, ".");
	}

}
