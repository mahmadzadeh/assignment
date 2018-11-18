package ea.sample.assignment.dto;

import ea.sample.assignment.domain.Topic;

import java.util.List;

public class TopicDto {

    private long id;
    private String name;
    private List<Long> messages;

    public TopicDto( Topic topic ) {
        this.id = topic.getId();
        this.name = topic.getName();
        this.messages = topic.getMessages();
    }

    public TopicDto( long id, String name, List<Long> msgs ) {
        this.id = id;
        this.name = name;
        this.messages = msgs;
    }

    protected TopicDto() {

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
