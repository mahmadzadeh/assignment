package ea.sample.assignment;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.dto.MessageCollectionDto;
import ea.sample.assignment.dto.MessageDto;
import ea.sample.assignment.dto.TopicCollectionDto;
import ea.sample.assignment.dto.TopicDto;
import ea.sample.assignment.exeptions.DuplicateTopicException;
import ea.sample.assignment.exeptions.InvalidTopicException;
import ea.sample.assignment.exeptions.MessageNotFoundException;
import ea.sample.assignment.exeptions.TopicNotFoundException;
import ea.sample.assignment.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TopicController {

    private static final int MAX_MSG_COUNT = 10;
    private final TopicService topicService;

    public TopicController( TopicService topicService ) {
        this.topicService = topicService;
    }

    @GetMapping("/topics")
    public TopicCollectionDto getTopics() {
        return new TopicCollectionDto( topicService.getTopics() );
    }

    @PostMapping("/topics")
    public TopicDto createTopic( @RequestBody TopicDto topicDto ) {
        return new TopicDto( topicService.createTopic( topicDto ) );
    }

    @GetMapping("/topics/{topicName}")
    public TopicDto getTopic( @PathVariable String topicName ) {
        return new TopicDto( topicService.getTopic( topicName ) );
    }

    @GetMapping("/topics/{topicName}/messages")
    public MessageCollectionDto getTopicMessages( @PathVariable String topicName ) {
        return new MessageCollectionDto( topicService.getTopicMessages( topicName, MAX_MSG_COUNT ) );
    }

    @GetMapping("/topics/{topicName}/messages/{id}")
    public MessageDto getTopicMessages( @PathVariable String topicName, @PathVariable long id ) {
        return new MessageDto( topicService.getTopicMessage( topicName, id ) );
    }

    @PostMapping("/topics/{topicName}/messages")
    public MessageDto createMessageForTopic( @PathVariable String topicName, @RequestBody Message msg ) {
        return new MessageDto( topicService.createMessageForTopic( topicName, msg ) );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void topicNotFoundException( TopicNotFoundException e ) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void topicNotFoundException( MessageNotFoundException e ) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void topicNotFoundException( InvalidTopicException e ) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void duplicateTopicException( DuplicateTopicException e ) {
    }


}
