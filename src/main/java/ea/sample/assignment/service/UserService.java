package ea.sample.assignment.service;

import ea.sample.assignment.dao.IUserRepository;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.domain.User;
import ea.sample.assignment.exeptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final MessageService messageService;
    private final IUserRepository userRepository;
    private final TopicService topicService;

    public UserService( IUserRepository userRepository, MessageService messageService, TopicService topicService ) {
        this.messageService = messageService;
        this.userRepository = userRepository;
        this.topicService = topicService;
    }

    public Set<User> getUsers() {
        return userRepository.readAll();
    }

    public User getUser( long id ) {
        return userRepository.read( id ).orElseThrow( () -> new UserNotFoundException( "Invalid User with id " + id ) );
    }

    public Set<Message> getUserMessages( long userId ) {
        return messageService.getUserMessages( userId );
    }

    public Set<Topic> getUserSubscribedTopics( long userId ) {
        return userRepository.readTopics( userId );
    }

    public Topic createSubscriptionFor( long userId, Topic topic ) {

        Topic t = topicService.getTopic( topic.getName() );

        User user = getUser( userId );

        user.subscribe( topic.getName() );

        return t;
    }
}
