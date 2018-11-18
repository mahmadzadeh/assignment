package ea.sample.assignment.dto;

import ea.sample.assignment.domain.Message;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MessageCollectionDto {

    private final Set<Message> messages;

    public MessageCollectionDto( Collection<Message> messages ) {
        this.messages = new HashSet<>( messages );
    }

    public Set<Message> getMessages() {
        return Collections.unmodifiableSet( messages );
    }

    public int size() {
        return messages.size();
    }

}
