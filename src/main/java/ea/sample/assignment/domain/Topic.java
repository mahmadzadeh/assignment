package ea.sample.assignment.domain;

import java.util.List;
import java.util.Stack;

import static java.util.Collections.unmodifiableList;

public class Topic {
    private final String name;
    private final List<Message> messages;

    public Topic( String name ) {
        this.name = name;
        messages = new Stack<>();
    }

    public String getName() {
        return name;
    }

    public void addMessage( Message msg ) {
        messages.add( msg );
    }

    public List<Message> getMessages() {
        return unmodifiableList( messages );
    }

    public List<Message> getLastN( int n ) {

        return n > messages.size()
                ? unmodifiableList( messages )
                : unmodifiableList( messages.subList( messages.size() - n, messages.size() ) );
    }


}
