package restapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RestApplication  {

    private static final Logger logger = LogManager.getLogger(RestApplication.class);



    //TODO 1)fix issue with time zone = post new expence created with prev date - fixed with WA +4 hours added
    // the issue with TZ sync - needs to be fixed permanantly
//TODO 2) add user's authorisation - create default user admin/3
    //now particular user is able to login - this is fine for initial version

    //rest api supports this via GET /expence call.
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    }

