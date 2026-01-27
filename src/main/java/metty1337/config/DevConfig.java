package metty1337.config;

import java.util.Properties;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@Profile("dev")
@PropertySource("classpath:db/app-dev.properties")
@RequiredArgsConstructor
public class DevConfig {

  private final Environment environment;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(environment.getRequiredProperty("driver"));
    dataSource.setUrl(environment.getRequiredProperty("url"));
    dataSource.setUsername(environment.getRequiredProperty("username"));
    dataSource.setPassword(environment.getRequiredProperty("password"));
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan("metty1337.entity");

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);

    Properties jpaProperties = new Properties();
    jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    jpaProperties.put("hibernate.show_sql", true);
    jpaProperties.put("hibernate.hbm2ddl.auto", "update");
    em.setJpaProperties(jpaProperties);

    return em;
  }
}
