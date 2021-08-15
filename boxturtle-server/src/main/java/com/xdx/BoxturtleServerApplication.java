package com.xdx;

import com.xdx.dao.AdminMapper;
import com.xdx.model.Admin;
import com.xdx.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Value;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@SpringBootApplication
@Slf4j
@MapperScan("com.xdx.dao")
//测试git
//
public class BoxturtleServerApplication implements ApplicationRunner {
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private RedisService<String,String> redisService;

	public static void main(String[] args) {
		SpringApplication.run(BoxturtleServerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		generateArtifacts();
		Admin param1=new Admin();
		param1.setAdminId(1);
		Admin admin1=adminMapper.selectOne(param1);
		log.info(admin1.toString());
		Admin param2=new Admin();
		param2.setAdminName("xdx");
		List<Admin>adminListByName=adminMapper.select(param2);
		log.info("adminListByName"+adminListByName.toString());
		log.info("selectAll:"+adminMapper.selectAll().toString());
		redisService.set("xdx","xdx  works  hard");
		log.info(redisService.get("xdx").toString());
	}
	@Value("${xdx}")
	private String xdx;
	@RequestMapping("hello")
	public String hello(){
		log.info("这是一条测试日志");
		return "hello"+xdx;
	}
	private void generateArtifacts() throws Exception {
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(
				this.getClass().getResourceAsStream("/generatorConfig.xml"));
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}
}
