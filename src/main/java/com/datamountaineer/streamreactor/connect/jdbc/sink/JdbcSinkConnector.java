package com.datamountaineer.streamreactor.connect.jdbc.sink;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * <h1>JdbcSinkConnector</h1>
 * Kafka connect JDBC Sink connector
 * <p>
 * Sets up JdbcSinkTask and configurations for the tasks.
 **/
public final class JdbcSinkConnector extends SinkConnector {
  private static final Logger logger = LoggerFactory.getLogger(JdbcSinkConnector.class);

  //???
  private Map<String, String> configProps = null;

  /**
   * States which SinkTask class to use
   **/
  public Class<? extends Task> taskClass() {
    return JdbcSinkTask.class;
  }

  /**
   * Set the configuration for each work and determine the split
   *
   * @param maxTasks The max number of task workers be can spawn
   * @return a List of configuration properties per worker
   **/
  @Override
  public List<Map<String, String>> taskConfigs(int maxTasks) {
    logger.info("Setting task configurations for " + maxTasks + " workers.");
    final List<Map<String, String>> configs = Lists.newArrayList();
    for (int i = 0; i < maxTasks; ++i)
      configs.add(configProps);
    return configs;
  }

  /**
   * Start the sink and set to configuration
   *
   * @param props A map of properties for the connector and worker
   **/
  @Override
  public void start(Map<String, String> props) {
    final Joiner.MapJoiner mapJoiner = Joiner.on(',').withKeyValueSeparator("=");
    final String propsStr = mapJoiner.join(props);
    logger.info("Starting JDBC Connector with " + propsStr);
    configProps = props;
  }

  @Override
  public void stop() {
  }

  @Override
  public String version() {
    return getClass().getPackage().getImplementationVersion();
  }
}
