package it.f2informatica.pagination.services;

public abstract class QueryParameters {

  public int getPage() {
    return getDisplayStart() / getDisplayLength();
  }

  public int getSize() {
    return getDisplayLength();
  }

  public String getSortColumn() {
    return getColumnName(getSortColumnIndex());
  }

  public abstract String getSearchCriteria();

  public abstract int getDisplayStart();

  public abstract int getDisplayLength();

  public abstract String getColumnName(int columnIndex);

  public abstract int getSortColumnIndex();

  public abstract int getColumnsNumber();

  public abstract String getSortDirection();

  public abstract String getEcho();

}
