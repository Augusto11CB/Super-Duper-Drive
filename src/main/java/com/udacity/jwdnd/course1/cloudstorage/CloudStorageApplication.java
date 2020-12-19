package com.udacity.jwdnd.course1.cloudstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class, args);
    }

	/*
        // TODO Aks Udacity Knowledge Center about this case

        @Bean
        public ErrorPageRegistrar errorPageRegistrar(){
            return new CustomErrorPageRegistrar();
        }

        private static class CustomErrorPageRegistrar implements ErrorPageRegistrar {

            // Register your error pages and url paths.
            @Override
            public void registerErrorPages(ErrorPageRegistry registry) {
                registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/result?errorMessage=" + FILE_NOT_FOUND_MSG));
            }
        }
	*/

}
