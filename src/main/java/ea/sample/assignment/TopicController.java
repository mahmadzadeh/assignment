package ea.sample.assignment;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.dto.MessageCollectionDto;
import ea.sample.assignment.dto.MessageDto;
import ea.sample.assignment.dto.TopicCollectionDto;
import ea.sample.assignment.dto.TopicDto;
import ea.sample.assignment.exeptions.*;
import ea.sample.assignment.service.ITopicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TopicController {

    private static final int MAX_MSG_COUNT = 10;
    private final ITopicService topicService;

    public TopicController( ITopicService topicService ) {
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

    @PostMapping("/topics/{topicName}/messages")
    public MessageDto createMessageForTopic( @PathVariable String topicName, @RequestBody Message msg ) {
        return new MessageDto( topicService.createMessageForTopic( topicName, msg ) );
    }

    @GetMapping("/topics/{topicName}/messages/{id}")
    public MessageDto getTopicMessages( @PathVariable String topicName, @PathVariable long id ) {
        return new MessageDto( topicService.getTopicMessage( topicName, id ) );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void topicNotFoundException( TopicNotFoundException e ) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void topicNotFoundException( UserNotFoundException e ) {
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void invalidMessageException( InvalidMessageException e ) {
    }


}
