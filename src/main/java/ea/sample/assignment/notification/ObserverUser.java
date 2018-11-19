package ea.sample.assignment.notification;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.User;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.apache.commons.io.FileUtils.write;

public class ObserverUser {

    private final User user;

    public ObserverUser( User user ) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    /**
     * To simplify and due to shortage of time I just create a unique file with the content of the newly
     * created message. Any time a new message is added to subscribed topic a unique file
     * is created for the user with the content of the new message.
     *
     * This is my version of the requirement:
     * "receive in real-time new messages added to this topic"
     */
    public void update( Message message, String topic ) {

        String fileName = getFileName();

        try {

            write( new File( fileName ), buildMessageContent( message, topic ), "UTF-8" );

        } catch ( IOException e ) {

        }
    }

    private String getFileName() {
        return "new_msg_for_" + user.getName() + "_" + UUID.randomUUID().toString() + ".txt";
    }

    private String buildMessageContent( Message message, String topic ) {
        return "Here is the latest message for the topic '" + topic +
                "' that you have subscribed to: " + message.getMessage() + "";
    }
}
