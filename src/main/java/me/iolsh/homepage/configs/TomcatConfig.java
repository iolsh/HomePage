package me.iolsh.homepage.configs;

import io.micrometer.core.instrument.util.StringUtils;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;

@Configuration
public class TomcatConfig {

    @Value("${homepage.keystoreFile}")
    private String keystoreFile;
    @Value("${homepage.keystorePass}")
    private String keystorePass;
    @Value("${homepage.keyAlias}")
    private String keyAlias;

    @Bean
    @Profile("ssl")
    public TomcatServletWebServerFactory servletContainer() { //TODO check me ;)
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }

    private Connector createSslConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        File keystore = new File(keystoreFile);
        File truststore = keystore;
        connector.setScheme("https");
        connector.setSecure(true);
        connector.setPort(8443);
        protocol.setSSLEnabled(true);
        protocol.setKeystoreFile(keystore.getAbsolutePath());
        protocol.setKeystorePass(keystorePass);
        protocol.setTruststoreFile(truststore.getAbsolutePath());
        protocol.setTruststorePass(keystorePass);
        protocol.setKeyAlias(keyAlias);
        return connector;
    }

}
