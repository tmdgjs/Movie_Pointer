package mari.hans.movie_information;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MovieInformationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieInformationApplication.class, args);
    }

}







