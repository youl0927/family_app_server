package com.family.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing      //BaseEntity 자동으로 업데이트 되게 해줌
public class JpaConfig {
}
