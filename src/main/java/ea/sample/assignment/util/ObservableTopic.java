package ea.sample.assignment.util;

import ea.sample.assignment.domain.Topic;

public class ObservableTopic {
    private final Topic topic;

    public ObservableTopic( Topic topic ) {

        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }
}
