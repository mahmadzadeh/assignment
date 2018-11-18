package ea.sample.assignment.dto;

import ea.sample.assignment.domain.Topic;

import java.util.List;

public class TopicDto {

    private final long id;

    private final String name;

    private final List<Long> messages;

    public TopicDto( Topic topic ) {
        this.id = topic.getId();
        this.name = topic.getName();
        this.messages = topic.getMessages();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public List<Long> getMessages() {
        return messages;
    }
}
