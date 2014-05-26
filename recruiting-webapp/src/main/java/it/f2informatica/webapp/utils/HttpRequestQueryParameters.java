package it.f2informatica.webapp.utils;

import it.f2informatica.pagination.services.QueryParameters;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestQueryParameters extends QueryParameters {

  private HttpServletRequest request;

  public HttpRequestQueryParameters(HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public String getSearchCriteria() {
    return request.getParameter("sSearch");
  }

  @Override
  public int getDisplayStart() {
    return getParameterAsPrimitiveInt("iDisplayStart");
  }

  @Override
  public int getDisplayLength() {
    return getParameterAsPrimitiveInt("iDisplayLength");
  }

  @Override
  public String getColumnName(int columnIndex) {
    return request.getParameter("mDataProp_" + columnIndex);
  }

  @Override
  public int getSortColumnIndex() {
    return getParameterAsPrimitiveInt("iSortCol_0");
  }

  @Override
  public int getColumnsNumber() {
    return getParameterAsPrimitiveInt("iColumns");
  }

  @Override
  public String getSortDirection() {
    return request.getParameter("sSortDir_0");
  }

  @Override
  public String getEcho() {
    return request.getParameter("sEcho");
  }

  private int getParameterAsPrimitiveInt(String parameter) {
    String intParameter = request.getParameter(parameter);
    if (StringUtils.hasText(intParameter)) {
      return Integer.parseInt(intParameter);
    }
    return 0;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("HttpRequestQueryParameters [getPage()=");
    builder.append(getPage());
    builder.append(", getSize()=");
    builder.append(getSize());
    builder.append(", getSortColumn()=");
    builder.append(getSortColumn());
    builder.append("]");
    return builder.toString();
  }

}
