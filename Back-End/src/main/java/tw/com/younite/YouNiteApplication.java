package tw.com.younite;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("tw.com.younite.mapper")
public class YouNiteApplication {


	public static void main(String[] args) {
		SpringApplication.run(YouNiteApplication.class, args);
	}

}
