package metty1337.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    context.register(AppConfig.class, WebConfig.class, DevConfig.class);

    DispatcherServlet servlet = new DispatcherServlet(context);
    ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcher", servlet);
    dynamic.setLoadOnStartup(1);
    dynamic.addMapping("/");
  }
}
