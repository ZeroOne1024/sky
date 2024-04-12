package com.asuka;

import com.asuka.dto.EmployeeLoginDTO;
import com.asuka.result.Result;
import com.asuka.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PostMapping;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author Asuka
 */

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableScheduling // 开启任务调度
@Slf4j
public class SkyServerApplication {

	public static void main(String[] args) {
		System.out.println("""
                ___________________初音未来保佑你的项目顺利运行!_
                _______________#########_______________________
                ______________############_____________________
                ______________#############____________________
                _____________##__###########___________________
                ____________###__######_#####__________________
                ____________###_#######___####_________________
                ___________###__##########_####________________
                __________####__###########_####_______________
                ________#####___###########__#####_____________
                _______######___###_########___#####___________
                _______#####___###___########___######_________
                ______######___###__###########___######_______
                _____######___####_##############__######______
                ____#######__#####################_#######_____
                ____#######__##############################____
                ___#######__######_#################_#######___
                ___#######__######_######_#########___######___
                ___#######____##__######___######_____######___
                ___#######________######____#####_____#####____
                ____######________#####_____#####_____####_____
                _____#####________####______#####_____###______
                ______#####______;###________###______#________
                ________##_______####________####______________""");





		SpringApplication.run(SkyServerApplication.class, args);
		log.info("服务成功启动...");
	}

















}
