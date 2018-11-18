package ea.sample.assignment.domain;

public class Subscription {

    private final long id;
    private final long userId;
    private final String topic;

    public Subscription( long id, long userId, String topic ) {
        this.id = id;
        this.userId = userId;
        this.topic = topic;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getTopic() {
        return topic;
    }
}
