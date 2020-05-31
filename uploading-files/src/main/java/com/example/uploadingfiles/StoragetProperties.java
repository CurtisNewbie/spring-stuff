package com.example.uploadingfiles;

import org.springframework.boot.context.properties.ConfigurationProperties;

// externalized configuration, this is kinda equivalent to @ConfigProperty
@ConfigurationProperties("storage")
public class StoragetProperties {

    // this is the default
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String l) {
        this.location = l;
    }

}