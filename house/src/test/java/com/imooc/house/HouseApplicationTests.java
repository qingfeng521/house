package com.imooc.house;

import com.imooc.house.configEntity.HttpClientEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseApplicationTests {
	//@Autowired
	HttpClient httpClient;
	@Test
	public void contextLoads() {
		try {
			System.out.println(httpClient.execute(new HttpGet("http://www.baidu.com")).getEntity().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
