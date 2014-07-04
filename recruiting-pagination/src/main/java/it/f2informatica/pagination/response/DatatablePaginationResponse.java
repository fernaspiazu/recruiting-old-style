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
package it.f2informatica.pagination.response;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysema.query.Tuple;
import it.f2informatica.pagination.services.QueryParameters;
import it.f2informatica.pagination.utils.SafeGetterMethodExecutor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DatatablePaginationResponse implements PaginationResponse {

	@Autowired
	private SafeGetterMethodExecutor safeGetterMethodExecutor;

	@Override
	public <T> Map<String, Object> generateResponseAttributes(QueryParameters queryParameters, Page<T> paginatedQueryResult) {
		final boolean hasContent = paginatedQueryResult.hasContent();
		Map<String, Object> datatableAttributes = Maps.newHashMap();
		datatableAttributes.put("sEcho", queryParameters.getEcho());
		datatableAttributes.put("iTotalRecords", (hasContent) ? paginatedQueryResult.getTotalElements() : 0L);
		datatableAttributes.put("iTotalDisplayRecords", (hasContent) ? paginatedQueryResult.getTotalElements() : 0L);
		datatableAttributes.put("aaData", convertResultAsArray(paginatedQueryResult, queryParameters));
		return datatableAttributes;
	}

	private <T> Object[] convertResultAsArray(Page<T> paginatedQueryResult, QueryParameters parameters) {
		return paginatedQueryResult.hasContent()
			? Iterables.toArray(transformDataRowOneByOne(parameters, paginatedQueryResult), Object.class)
			: ArrayUtils.EMPTY_OBJECT_ARRAY;
	}

	private <T> List<Map<String, Object>> transformDataRowOneByOne(QueryParameters parameters, Page<T> paginatedQueryResult) {
		List<Map<String, Object>> data = Lists.newArrayList();
		for (T entity : paginatedQueryResult.getContent()) {
			T tuple = resolveTuple(entity);
			Map<String, Object> row = Maps.newHashMap();
			for (int columnIndex = 0; columnIndex < parameters.getColumnsNumber(); columnIndex++) {
				String field = parameters.getColumnName(columnIndex);
				row.put(field, safeGetterMethodExecutor.invokeGetterOnField(field, tuple));
			}
			data.add(row);
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	private <T> T resolveTuple(T entity) {
		if (entity instanceof Tuple) {
			return (T) ((Tuple) entity).toArray()[0];
		}
		return entity;
	}

}
