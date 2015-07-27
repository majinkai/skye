package com.handu.skye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author Jinkai.Ma
 */
@SpringBootApplication
@Import({SkyRocketMQConfig.class})
public class SkyeWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyeWebApplication.class, args);
    }

}
