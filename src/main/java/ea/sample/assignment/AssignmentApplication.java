package ea.sample.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "com", "ea" })
@SpringBootApplication
public class AssignmentApplication {

    public static void main( String[] args ) {
        SpringApplication.run( AssignmentApplication.class, args );
    }
}
