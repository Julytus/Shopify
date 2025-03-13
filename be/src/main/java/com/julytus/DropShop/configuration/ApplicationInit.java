package com.julytus.DropShop.configuration;

import com.julytus.DropShop.common.PredefinedRole;
import com.julytus.DropShop.model.Role;
import com.julytus.DropShop.repository.RoleRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInit {
    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(RoleRepository roleRepository) {
        log.info("Initializing application..............");
        return args -> {
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role(1L, PredefinedRole.ADMIN));
                roleRepository.save(new Role(2L, PredefinedRole.USER));;
            }
            log.info("JulyTus: Application initialization completed");
        };
    }
}
