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
package it.f2informatica.core.validator.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
	private static final long serialVersionUID = -600637407302494115L;

	private String fieldName;
	private String errorCode;
	private String errorMessage;

	public ErrorMessage(String fieldName, String errorCode, String errorMessage) {
		this.fieldName = fieldName;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(errorCode).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ErrorMessage that = (ErrorMessage) o;
		return new EqualsBuilder().append(this.errorCode, that.getErrorCode()).isEquals();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("ErrorMessage");
		sb.append("{errorCode='").append(errorCode).append('\'');
		sb.append(", errorMessage='").append(errorMessage).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
