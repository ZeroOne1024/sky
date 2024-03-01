package com.asuka;

import com.asuka.dto.EmployeeLoginDTO;
import com.asuka.result.Result;
import com.asuka.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PostMapping;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author Asuka
 */

@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class SkyServerApplication {



	public static void main(String[] args) {
		SpringApplication.run(SkyServerApplication.class, args);
		log.info("服务成功启动...");
	}

}
