package ea.sample.assignment.service;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final MessageService messageService;

    public UserService( MessageService messageService ) {
        this.messageService = messageService;
    }

    public Set<User> getUsers() {
        return null;
    }

    public User getUser( long id ) {
        return null;
    }


    public List<Message> getUserMessages( long userId ) {
        return null;
    }

    public List<Topic> getUserSubscribedTopics( long userId ) {
        return null;
    }

    public Topic createSubscriptionFor( long userId, Topic topic ) {
        return null;
    }
}
