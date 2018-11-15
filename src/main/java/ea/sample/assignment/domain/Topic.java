package ea.sample.assignment.domain;

import java.util.Collection;
import java.util.Stack;

public class Topic
{
	private final String name;
	private final Stack<Message> messages;

	public Topic( String name )
	{
		this.name = name;
		messages = new Stack<>();
	}

	public String getName()
	{
		return name;
	}

	public void addMessage( Message msg )
	{
		messages.push( msg );
	}

	public Collection<Message> getMessages()
	{
		Stack<Message> shallowCopy = new Stack<>();
		shallowCopy.addAll( messages );

		return shallowCopy;
	}
}
