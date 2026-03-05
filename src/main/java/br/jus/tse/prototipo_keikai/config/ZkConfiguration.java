package br.jus.tse.prototipo_keikai.config;

import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
// IMPORTANTE: O Spring Boot 2.7 usa org.springframework.boot.web.servlet
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ZkConfiguration {

    @Bean
    public ServletRegistrationBean<DHtmlLayoutServlet> zkLoaderServlet() {
        Map<String, String> params = new HashMap<>();
        params.put("update-uri", "/zkau");

        ServletRegistrationBean<DHtmlLayoutServlet> reg = new ServletRegistrationBean<>(
                new DHtmlLayoutServlet(), "*.zul");
        reg.setInitParameters(params);
        reg.setLoadOnStartup(1);

        // Importante: definir ordem para que o ZK servlet tenha prioridade
        reg.setOrder(1);

        return reg;
    }

    @Bean
    public ServletRegistrationBean<DHtmlUpdateServlet> zkUpdateServlet() {
        ServletRegistrationBean<DHtmlUpdateServlet> reg = 
            new ServletRegistrationBean<>(new DHtmlUpdateServlet(), "/zkau/*");
        reg.setLoadOnStartup(2);
        return reg;
    }
}