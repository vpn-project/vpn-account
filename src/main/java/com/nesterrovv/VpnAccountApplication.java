package com.nesterrovv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@SuppressWarnings("HideUtilityClassConstructor")
public class VpnAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(VpnAccountApplication.class, args);
    }

}
