package ea.sample.assignment.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class IdGeneratorTest {

    @Test
    public void nextIdBehavesAsExpected() {
        assertThat( IdGenerator.nextId() ).isGreaterThan( 0 );
    }

}