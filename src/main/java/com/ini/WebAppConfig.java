package com.ini;


import com.aop.authentication.AuthenticationInterceptor;
import com.ini.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Somnus`L on 2017/4/11.
 */
@Configuration
@SpringBootApplication
public class WebAppConfig extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(WebAppConfig.class, args);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl();
    }

    @Bean
    public ApplyService applyService(){
        return new ApplyServiceImpl();
    }
    @Bean
    public CommentService commentService(){
        return new CommentServiceImpl();
    }
    @Bean
    public SkillService skillService(){
        return new SkillServiceImpl();
    }
    @Bean
    public StatisticsService statisticsService(){
        return new StatisticsServiceImpl();
    }

//    @Bean
//    public MongoTemplate mongoTemplate()
//    {
//        return new MongoTemplate(MongoClientFactory.getMongoClient(), "test");
//    }
}
