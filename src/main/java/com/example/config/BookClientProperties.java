package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.validation.constraints.NotNull;

@Data
@ConfigurationProperties(prefix = "catalog")
public class BookClientProperties {

    /**
     * URL for catalog service URL
     */
    @NotNull
    private String url;
}
