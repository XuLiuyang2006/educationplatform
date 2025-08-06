package com.example.educationplatform;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "教育平台 API 文档",
                version = "1.0",
                description = "基于讯飞人工智能平台的数智化教育应用系统接口文档",
                contact = @Contact(name = "Jackie", email = "liuyang.xu.jackie@gmail.com"),
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
                //license声明了许可证信息，name是许可证名称，url是许可证的链接，MIT是常用的开源许可证之一，这个url是详细规则的链接
        )
)
public class EducationPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationPlatformApplication.class, args);
    }

}
