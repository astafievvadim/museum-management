package com.astafievvadim.mm_backend.configuration;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(30 * 1024 * 1024); // 30MB
            connector.setMaxPostSize(30 * 1024 * 1024);
        });
    }
}
