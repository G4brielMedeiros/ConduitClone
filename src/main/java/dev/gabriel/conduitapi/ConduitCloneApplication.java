package dev.gabriel.conduitapi;

import dev.gabriel.conduitapi.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class ConduitCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConduitCloneApplication.class, args);
    }

}
