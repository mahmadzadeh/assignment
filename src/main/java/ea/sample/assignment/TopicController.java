package ea.sample.assignment;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.exeptions.MessageNotFoundException;
import ea.sample.assignment.exeptions.TopicNotFoundException;
import ea.sample.assignment.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private static final int MAX_MSG_COUNT = 10;
    private final TopicService topicService;

    public TopicController( TopicService topicService ) {
        this.topicService = topicService;
    }

    @GetMapping("/")
    public Set<Topic> getTopics() {
        return topicService.getTopics();
    }

    @GetMapping("/{topicName}")
    public Topic getTopic( @PathVariable String topicName ) {
        return topicService.getTopic( topicName );
    }

    @GetMapping("/{topicName}/messages")
    public List<Message> getTopicMessages( @PathVariable String topicName ) {
        return topicService.getTopicMessages( topicName, MAX_MSG_COUNT );
    }

    @GetMapping("/{topicName}/messages/{id}")
    public Message getTopicMessages( @PathVariable String topicName, @PathVariable long id ) {
        return topicService.getTopicMessage( topicName, id );
    }

    @PostMapping("/{topicName}/messages")
    public Message createMessageForTopic( @PathVariable String topicName, @RequestBody Message msg ) {
        return topicService.createMessageForTopic( topicName, msg );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void topicNotFoundException( TopicNotFoundException e ) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void topicNotFoundException( MessageNotFoundException e ) {
    }
}
