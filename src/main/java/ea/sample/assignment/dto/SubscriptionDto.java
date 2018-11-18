package ea.sample.assignment.dto;

public class SubscriptionDto {

    private String topic;
    private long userId;

    public SubscriptionDto( String topic, long userId ) {
        this.topic = topic;
        this.userId = userId;
    }


}
