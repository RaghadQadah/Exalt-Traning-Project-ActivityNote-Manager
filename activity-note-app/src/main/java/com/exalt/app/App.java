package com.exalt.app;

import com.exalt.app.utils.adapter.IDataAdapter;
import com.exalt.app.utils.adapter.JsoupDataAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    /**
     * Cross-Origin Resource Sharing Bean. Spring boot app will the beans from FilterRegistrationBean as
     * requests filter (will be called before te matched requests).
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        /**
         * Set CORS headers
         */
        config.setAllowCredentials(true);

        // Allow origin from UI dev server.
        /*
         * TODO: this might be added from application.properties
         */
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        /**
         * This will match any urls from all controllers and will use CORS headers
         * to be part from the response headers.
         */
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Bean
    public IDataAdapter createDataAdapter() {
        /**
         * TODO: pass any config here
         */
        JsoupDataAdapter dataAdapter = new JsoupDataAdapter();

        return dataAdapter;
    }
}

