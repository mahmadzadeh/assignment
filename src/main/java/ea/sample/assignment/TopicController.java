package ea.sample.assignment;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ea.sample.assignment.domain.Topic;

@RestController
public class TopicController
{

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
	public Topic getTopic( @PathVariable String name )
	{
		return topicService.getTopic( name );
	}





	@ExceptionHandler
	@ResponseStatus( HttpStatus.NOT_FOUND )
	private void topicNotFoundException( TopicNotFoundException e )
	{
	}
}
