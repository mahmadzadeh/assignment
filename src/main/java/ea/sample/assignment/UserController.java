package ea.sample.assignment;

import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.dto.*;
import ea.sample.assignment.exeptions.*;
import ea.sample.assignment.service.ITopicService;
import ea.sample.assignment.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final int TOP_TEN = 10;

    private final UserService userService;
    private final ITopicService topicService;

    public UserController( UserService userService, ITopicService topicService ) {
        this.userService = userService;
        this.topicService = topicService;
    }

    @GetMapping("/users")
    public UserCollectionDto getUsers() {
        return new UserCollectionDto( userService.getUsers() );
    }

    @PostMapping("/users")
    public UserDto createTopic( @RequestBody UserDto userDto ) {
        return new UserDto( this.userService.createUser( userDto ) );
    }

    @GetMapping("/users/{id}")
    public UserDto getUser( @PathVariable long id ) {
        return new UserDto( userService.getUser( id ) );
    }

    @GetMapping("/users/{id}/messages")
    public MessageCollectionDto getTopicMessages( @PathVariable long id ) {
        return new MessageCollectionDto( userService.getUserMessages( id ) );
    }

    @GetMapping("/users/rankings")
    public UserRankingsDto getRankings() {
        return new UserRankingsDto( userService.getTopRanking( TOP_TEN ) );
    }

    @GetMapping("/users/{id}/subscriptions")
    public TopicCollectionDto getSubscriptions( @PathVariable long id ) {

        Set<Topic> topics = userService
                .getUserSubscribedTopics( id )
                .stream()
                .map( topicService::getTopic )
                .collect( Collectors.toSet() );

        return new TopicCollectionDto( topics );
    }

    @PostMapping("/users/{id}/subscriptions")
    public TopicDto createSubscriptionForTopic( @PathVariable long id, @RequestBody TopicDto inputTopic ) {
        Topic topic = topicService.getTopic( inputTopic.getName() );
        return new TopicDto( userService.createSubscriptionFor( id, topic ) );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void userNotFoundException( UserNotFoundException e ) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void topicNotFoundException( TopicNotFoundException e ) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void dudplicatedUserException( DuplicateUserException e ) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void dudplicatedSubscriptionException( DuplicateSubscriptionException e ) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void invalidTopicException( InvalidTopicException e ) {
    }
}
