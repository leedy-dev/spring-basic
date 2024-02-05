package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.*;

@Configuration
@ComponentScan(
        basePackages = "hello.core",
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
/*
  AppConfig 등록을 방지하기 위해 Configuration 어노테이션 제외

  exclude 넣어도 스프링부트 테스트해보면 AppConfig 제외가 안된다.
  예상으로는 실행 순서 문제인 것 같다.
 */
public class AutoAppConfig {

}
