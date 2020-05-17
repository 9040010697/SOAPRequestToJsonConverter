package com.example.SOAPRequestRest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RestController("/app")
@EnableSwagger2
public class SoapRequestRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapRequestRestApplication.class, args);
	}

	@PostMapping("/extract")
	public String request(@RequestBody String request) throws Exception {
		return extractXml(request);

	}

	public String extractXml(String request) throws Exception {
		System.out.println(request);
		XmlMapper mapper = new XmlMapper();
		Map<String, Object> obj = mapper.readValue(request, Map.class);
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj.get("Body"));
	}
	
	
	 @Bean
	  public Docket demoApi() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .protocols(new HashSet<>(Arrays.asList("http", "https")))
	        .select()
	        .apis(RequestHandlerSelectors.any())
	        .paths(Predicates.not(PathSelectors.regex("/error.*")))
	        .paths(Predicates.not(PathSelectors.regex("/actuator.*")))
	        .build().apiInfo(ApiInfo.DEFAULT);
	  }
}
