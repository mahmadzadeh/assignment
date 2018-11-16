package ea.sample.assignment;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class IdGeneratorTest {

    @Test
    public void nextIdBehavesAsExpected() {

        assertThat( IdGenerator.nextId() ).isEqualTo( 1 );
        assertThat( IdGenerator.nextId() ).isEqualTo( 2 );
        assertThat( IdGenerator.nextId() ).isEqualTo( 3 );
    }

}