package com.loveacamp.promotions;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@TestConfiguration
public class TestConfig {
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR").build();
        localeResolver.setDefaultLocale(locale);
        return localeResolver;
    }
}
