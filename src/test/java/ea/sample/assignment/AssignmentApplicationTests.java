package ea.sample.assignment;

import ea.sample.assignment.domain.Topic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AssignmentApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void testGettingTopics() {
        ResponseEntity<Topic> responseEntity = testRestTemplate.getForEntity( "/topics", Topic.class );

        assertThat( responseEntity.getStatusCode() ).isEqualTo( HttpStatus.OK );
    }

}
