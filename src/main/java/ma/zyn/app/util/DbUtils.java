package ma.zyn.app.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DbUtils {

  private static final Logger logger = LoggerFactory.getLogger("com.intuit.karate");

  private final JdbcTemplate jdbc;

  public DbUtils(Map<String, Object> datasource) {
    String url = (String) datasource.get("url");
    String username = (String) datasource.get("username");
    String password = (String) datasource.get("password");
    String driver = (String) datasource.get("driverClassName");
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driver);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    jdbc = new JdbcTemplate(dataSource);
    logger.info("init jdbc template: {}", url);
  }

  public Object update(String query) {
    logger.debug("query= {}", query);
    return jdbc.update(query);
  }

  public Object readValue(String query) {
    logger.debug("query= {}", query);
    return jdbc.queryForObject(query, Object.class);
  }

  public Map<String, Object> readRow(String query) {
    logger.debug("query= {}", query);
    return jdbc.queryForMap(query);
  }

  public List<Map<String, Object>> readRows(String query) {
    logger.debug("query= {}", query);
    return jdbc.queryForList(query);
  }

}
