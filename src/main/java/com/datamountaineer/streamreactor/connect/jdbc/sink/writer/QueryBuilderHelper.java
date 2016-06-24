package com.datamountaineer.streamreactor.connect.jdbc.sink.writer;

import com.datamountaineer.streamreactor.connect.jdbc.dialect.DbDialect;
import com.datamountaineer.streamreactor.connect.jdbc.sink.config.InsertModeEnum;
import io.confluent.common.config.ConfigException;

/**
 * Helper class for creating an instance of QueryBuilder
 */
public class QueryBuilderHelper {
  /**
   * Creates an instance of DbDialect from the jdbc sink settings.
   *
   * @param connection- The jdbc connection string
   * @param insertMode- The way data should be pushed into the rdbms: insert/upsert
   * @return - An instance of DbDialect
   */
  public static QueryBuilder from(final String connection, final InsertModeEnum insertMode) {
    try {
      final DbDialect dialect = DbDialect.fromConnectionString(connection);
      if (insertMode == InsertModeEnum.UPSERT) {
        return new UpsertQueryBuilder(dialect);
      }
      return new InsertQueryBuilder(dialect);
    } catch (IllegalArgumentException ex) {
      throw new ConfigException(ex.getMessage(), ex);
    }
  }
}
