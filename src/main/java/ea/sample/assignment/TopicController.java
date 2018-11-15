package ea.sample.assignment;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;

@RestController
public class TopicController
{

	private static final int MAX_MSG_COUNT = 10;
	private final TopicService topicService;

	public TopicController( TopicService topicService )
	{
		this.topicService = topicService;
	}

	@GetMapping( "/topics" )
	public Set<Topic> getTopics()
	{
		return topicService.getTopics();
	}

	@GetMapping( "/topics/{name}" )
	public Topic getTopic( @PathVariable String topicName )
	{
		return topicService.getTopic( topicName );
	}

	@GetMapping( "/topics/{name}/messages" )
	public List<Message> getTopicMessages( @PathVariable String topicName )
	{
		return topicService.getTopicMessages( topicName, MAX_MSG_COUNT );
	}

	@ExceptionHandler
	@ResponseStatus( HttpStatus.NOT_FOUND )
	private void topicNotFoundException( TopicNotFoundException e )
	{
	}
}
