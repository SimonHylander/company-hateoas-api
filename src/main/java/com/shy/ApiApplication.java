package com.shy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.shy", "com.shy.company"})
public class ApiApplication {
	// https://github.com/spring-projects/spring-hateoas-examples/tree/master/hypermedia/src/main/java/org/springframework/hateoas/examples

	/**
     * "id": "hal-cookbook",
     * "name": "HAL Cookbook"
	 * "_links": {
	 *     "self": {
	 *       "href": "http://example.com/api/book/hal-cookbook"
	 *     }
	 *   },
	 *   "_embedded": {
	 *     "author": {
	 *       "_links": {
	 *         "self": {
	 *           "href": "http://author-example.com"
	 *         }
	 *       },
	 *       "id": "shahadat",
	 *       "name": "Shahadat Hossain Khan"
	 *     }
	 *   },
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}