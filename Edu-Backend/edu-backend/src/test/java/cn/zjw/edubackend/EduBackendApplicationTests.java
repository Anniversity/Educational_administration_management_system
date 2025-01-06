package cn.zjw.edubackend;

import cn.zjw.edubackend.common.enums.ServiceEnum;
import cn.zjw.edubackend.common.enums.UserTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.HashMap;

@SpringBootTest
class EduBackendApplicationTests {

    @Resource
    Environment environment;

    @Test
    void contextLoads() {
        HashMap<String, String> map = new HashMap<>();
        for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
            for (ServiceEnum serviceEnum : ServiceEnum.values()) {
                String userType = userTypeEnum.name().toLowerCase();
                String service = serviceEnum.name().toLowerCase();

                String key = "permission." + userType + "." + service;
                String value = environment.getProperty(key);

                map.put(key, value);
            }
        }
    }

}
