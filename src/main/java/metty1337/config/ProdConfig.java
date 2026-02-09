package metty1337.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@Profile("prod")
@PropertySource("classpath:db/app-prod.properties")
@RequiredArgsConstructor
public class ProdConfig {

  private final Environment environment;

  @Bean
  public DataSource dataSource() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setDriverClassName(environment.getRequiredProperty("driver"));
    hikariConfig.setJdbcUrl(environment.getRequiredProperty("url"));
    hikariConfig.setUsername(environment.getRequiredProperty("username"));
    hikariConfig.setPassword(environment.getRequiredProperty("password"));

    hikariConfig.setMaximumPoolSize(10);
    hikariConfig.setMinimumIdle(2);
    hikariConfig.setConnectionTimeout(30_000);
    hikariConfig.setIdleTimeout(600_000);
    hikariConfig.setMaxLifetime(1_800_000);

    return new HikariDataSource(hikariConfig);
  }

  @Bean
  @DependsOn("liquibase")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    var em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("metty1337.entity");

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);

    Properties jpaProperties = new Properties();
    jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    jpaProperties.put("hibernate.show_sql", false);
    jpaProperties.put("hibernate.hbm2ddl.auto", "validate");
    em.setJpaProperties(jpaProperties);

    return em;
  }
}
