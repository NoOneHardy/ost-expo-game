package ch.no1hardy.ost.infrastructure.persistence.kurrent.config;

import io.kurrent.dbclient.KurrentDBClient;
import io.kurrent.dbclient.KurrentDBClientSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KurrentConfiguration {
    @Value("${kurrentdb.host}")
    private String host;

    @Value("${kurrentdb.port}")
    private Integer port;

    @Value("${kurrentdb.username}")
    private String username;

    @Value("${kurrentdb.password}")
    private String password;
    
    @Bean
    KurrentDBClient kurrentClient() {
        KurrentDBClientSettings settings = KurrentDBClientSettings.builder()
                .addHost(host, port)
                .defaultCredentials(username, password)
                .tls(false)
                .buildConnectionSettings();
        return KurrentDBClient.create(settings);
    }
}
