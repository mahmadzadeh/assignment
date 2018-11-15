package ea.sample.assignment.domain;

import org.junit.Test;

import java.util.Collection;
import java.util.Stack;

public class TopicTest
{

	@Test
	public void getMessages() throws Exception
	{
		Topic topic = new Topic( "" );

		topic.addMessage( new Message( 1, "m1" ) );
		topic.addMessage( new Message( 1, "m2" ) );
		topic.addMessage( new Message( 1, "m3" ) );

		Collection<Message> copy = topic.getMessages();

		copy.stream().forEach( it -> System.out.println(it) );
	}

}