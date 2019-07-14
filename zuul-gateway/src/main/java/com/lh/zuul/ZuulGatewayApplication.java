package com.lh.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关的默认规则：http://ZUUL_HOST:ZUUL_PORT/微服务实例名(serverId)/** ，转发至serviceId对应的微服务。
 * 比如在浏览器输入：http://localhost:9009/springcloud-zuul-filter-server1/hello地址， 它就会跳转访问到:http://localhost:9010/hello/这个地址上。
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ZuulGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulGatewayApplication.class, args);
	}

}
