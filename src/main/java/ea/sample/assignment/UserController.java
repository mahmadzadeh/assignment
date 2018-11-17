package ea.sample.assignment;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.domain.User;
import ea.sample.assignment.exeptions.TopicNotFoundException;
import ea.sample.assignment.exeptions.UserNotFoundException;
import ea.sample.assignment.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class UserController {

    private static final int MAX_MSG_COUNT = 10;
    private final UserService userService;

    public UserController( UserService userService ) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Set<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser( @PathVariable long id ) {
        return userService.getUser( id );
    }

    @GetMapping("/users/{id}/messages")
    public Set<Message> getTopicMessages( @PathVariable long id ) {
        return userService.getUserMessages( id );
    }

    @GetMapping("/users/{id}/subscriptions")
    public Set<Topic> getSubscriptions( @PathVariable long id ) {
        return userService.getUserSubscribedTopics( id );
    }

    @PostMapping("/users/{id}/subscriptions")
    public Topic createSubscriptionForTopic( @PathVariable long id, @RequestBody Topic topic ) {
        return userService.createSubscriptionFor( id, topic );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void userNotFoundException( UserNotFoundException e ) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void topicNotFoundException( TopicNotFoundException e ) {
    }
}
