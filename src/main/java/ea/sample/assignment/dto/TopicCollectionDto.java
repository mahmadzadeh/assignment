package ea.sample.assignment.dto;

import ea.sample.assignment.domain.Topic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TopicCollectionDto {

    private final List<Topic> topics;

    public TopicCollectionDto( Collection<Topic> topics ) {
        this.topics = new ArrayList<>( topics );
    }

    public List<Topic> getTopics() {
        return Collections.unmodifiableList( this.topics );
    }

    public int size() {
        return topics.size();
    }
}
