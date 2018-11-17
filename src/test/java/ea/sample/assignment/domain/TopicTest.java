package ea.sample.assignment.domain;

import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicTest {

    @Test
    public void getMessages() throws Exception {
        Topic topic = new Topic( 1, "" );

        topic.addMessage( new Message( 1, "m1", null ) );
        topic.addMessage( new Message( 1, "m2", null ) );
        topic.addMessage( new Message( 1, "m3", null ) );

        Collection<Message> copy = topic.getMessages();

        copy.stream().forEach( it -> System.out.println( it ) );
    }


    @Test
    public void getLast_N_messageWhen_N_BiggerThanMessageList() {
        Topic topic = new Topic( 1, "" );

        topic.addMessage( new Message( 1, "m1", null ) );
        topic.addMessage( new Message( 1, "m2", null ) );


        assertThat( topic.getLastN( 10 ) ).isEqualTo( topic.getMessages() );
    }

    @Test
    public void getLast_N_messageWhen_N_EqualToMessageList() {
        Topic topic = new Topic( 1, "" );

        topic.addMessage( new Message( 1, "m1", null ) );
        topic.addMessage( new Message( 1, "m2", null ) );


        assertThat( topic.getLastN( 2 ) ).isEqualTo( topic.getMessages() );
    }


    @Test
    public void getLast_N_messageWhen_N_SmallerThanMessageList() {
        Topic topic = new Topic( 1, "" );

        topic.addMessage( new Message( 1, "m1", null ) );
        topic.addMessage( new Message( 2, "m2", null ) );
        topic.addMessage( new Message( 3, "m3", null ) );

        int expectedSize = 2;

        List<Message> lastN = topic.getLastN( expectedSize );

        assertThat( lastN.size() ).isEqualTo( expectedSize );
        assertThat( lastN.get( 0 ) ).isEqualTo( new Message( 2, "m2", null ) );
        assertThat( lastN.get( 1 ) ).isEqualTo( new Message( 3, "m3", null ) );
    }

}