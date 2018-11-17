package ea.sample.assignment.domain;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicTest {


    @Test
    public void getLast_N_messageWhen_N_BiggerThanMessageList() {
        Topic topic = new Topic( 1, "" );

        topic.addMessage( 1 );
        topic.addMessage( 2 );

        assertThat( topic.getLastN( 10 ) ).isEqualTo( topic.getMessages() );
    }

    @Test
    public void getLast_N_messageWhen_N_EqualToMessageList() {
        Topic topic = new Topic( 1, "" );

        topic.addMessage( 1 );
        topic.addMessage( 2 );


        assertThat( topic.getLastN( 2 ) ).isEqualTo( topic.getMessages() );
    }


    @Test
    public void getLast_N_messageWhen_N_SmallerThanMessageList() {
        Topic topic = new Topic( 1, "" );

        topic.addMessage( 1 );
        topic.addMessage( 2 );
        topic.addMessage( 3 );

        int expectedSize = 2;

        List<Long> lastN = topic.getLastN( expectedSize );

        assertThat( lastN.size() ).isEqualTo( expectedSize );
        assertThat( lastN.get( 0 ) ).isEqualTo( 2 );
        assertThat( lastN.get( 1 ) ).isEqualTo( 3 );
    }

}