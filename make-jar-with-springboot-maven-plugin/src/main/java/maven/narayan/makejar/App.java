package maven.narayan.makejar;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.util.Objects;

import static org.apache.catalina.startup.Tomcat.addServlet;

/**
 * @author narayan-sambireddy
 */
@EnableWebMvc
@Configuration
@ComponentScan
public interface App {

    String  URL_MAPPING = "/";
    String  CONTEXT_PATH = "/app";
    Integer LOAD_ON_STARTUP = 1;
    String  DISPATCHER = "dispatcherServlet";
    String  BASE_DIR = new File(".").getAbsolutePath();

    static void main( String[] args ) {
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(getPort());

            Context servletContext = tomcat.addContext(CONTEXT_PATH, BASE_DIR);

            Wrapper dispatcher = addServlet(servletContext, DISPATCHER, new DispatcherServlet(springWebContext()));
            dispatcher.addMapping(URL_MAPPING);
            dispatcher.setLoadOnStartup(LOAD_ON_STARTUP);

            tomcat.start();
            tomcat.getServer().await();

        }catch (Throwable t) {
            t.printStackTrace();
            System.exit(-1);
        }
    }

    private static WebApplicationContext springWebContext() {
        return new AnnotationConfigWebApplicationContext() {{
            register(App.class);
        }};
    }

    private static Integer getPort() {
        return Integer.valueOf(Objects.toString(System.getProperty("PORT"), "8080"));
    }
 }
