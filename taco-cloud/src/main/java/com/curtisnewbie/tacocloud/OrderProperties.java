package com.curtisnewbie.tacocloud;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "taco.orders")
public class OrderProperties {
    // this is a property, the default value is 20. In configuration files (e.g.,
    // application.properties or application.yaml, it will be named "taco.orders.pageSize")
    @Min(value = 5, message = "Must be between 5 and 25")
    @Max(value = 25, message = "Must be between 5 and 25")
    private int pageSize = 20;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int p) {
        this.pageSize = p;
    }
}
