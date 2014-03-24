package it.f2informatica.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class MySQLApplicationContext {
  private static final Logger logger = LoggerFactory.getLogger(MySQLApplicationContext.class);

  public DataSource dataSource() {
    return null;
  }

}
