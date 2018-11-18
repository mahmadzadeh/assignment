package ea.sample.assignment.service;

import ea.sample.assignment.dao.IUserRepository;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.domain.User;
import ea.sample.assignment.dto.UserDto;
import ea.sample.assignment.exeptions.InvalidUserException;
import ea.sample.assignment.exeptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class UserService {

    private final MessageService messageService;
    private final IUserRepository userRepository;

    public UserService( IUserRepository userRepository, MessageService messageService ) {
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    public Set<User> getUsers() {
        return userRepository.readAll();
    }

    public User getUser( long id ) {
        return userRepository.read( id ).orElseThrow( () -> new UserNotFoundException( "Invalid User with id " + id ) );
    }

    public User createUser( UserDto userDto ) {
        if ( isBlank( userDto.getName() ) || isBlank( userDto.getEmail() ) ) {
            throw new InvalidUserException( "Invalid user given. User name and email can not be empty " );
        }

        return userRepository.create( userDto.getName(), userDto.getEmail() );
    }

    public Set<Message> getUserMessages( long userId ) {
        return messageService.getUserMessages( userId );
    }

    public Set<String> getUserSubscribedTopics( long userId ) {
        return userRepository.readTopics( userId );
    }

    public Topic createSubscriptionFor( long userId, Topic topic ) {

        User user = getUser( userId );

        user.subscribe( topic.getName() );

        return topic;
    }
}
