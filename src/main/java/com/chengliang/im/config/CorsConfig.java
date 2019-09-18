package com.chengliang.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Cors配置
 * @author chengliang
 */
@Configuration
public class CorsConfig {

        @Bean
        public CorsFilter corsFilter() {
            //创建cors配置对象
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.addAllowedOrigin("*");
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addExposedHeader("head1");
            //corsConfiguration.addExposedHeader("Location");
            //url配置资源对象
            UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
            //注册url配置资源对象
            urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
            //返回Cors过滤器
            return new CorsFilter(urlBasedCorsConfigurationSource);
        }

}
