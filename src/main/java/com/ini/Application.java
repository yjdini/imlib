package com.ini;


import com.ini.aop.authentication.AuthenticationInterceptor;
import com.ini.aop.validate.UserInputVerifyInterceptor;
import com.ini.service.*;
import com.ini.service.abstrac.*;
import com.ini.utils.FileUploadUtil;
import com.ini.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by Somnus`L on 2017/4/11.
 */
@Configuration
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    //aop.用户权限验证、用户输入验证
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new UserInputVerifyInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }

    @Bean
    public AdminService adminService(){
        return new AdminSerivceImpl();
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

    @Bean
    public SessionUtil sessionUtil(){
        return new SessionUtil();
    }

    @Bean
    public FileUploadUtil fileUploadUtil(){
        return new FileUploadUtil();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSource) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.ini.dao.entity");
        factory.setDataSource(dataSource);
        return factory;
    }


}
