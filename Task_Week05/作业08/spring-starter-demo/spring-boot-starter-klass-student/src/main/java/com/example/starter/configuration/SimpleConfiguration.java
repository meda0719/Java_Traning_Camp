package com.example.starter.configuration;

import com.example.starter.mybean.Klass;
import com.example.starter.mybean.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;


@Slf4j
@Configuration
public class SimpleConfiguration {

    public SimpleConfiguration() {
        log.info("SimpleConfiguration create!!!");
    }

    @Bean
    public Klass klass() {
        Student stu[] = {Student.builder().id(33).name("小主").build()};
        List<Student> lstu =  Arrays.asList(stu);
        return new Klass(lstu);
    }
}
