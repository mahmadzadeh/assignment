package ea.sample.assignment.notification;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.User;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ObservableTopicCollection {
    private final ConcurrentHashMap<String, ObservableTopic> observedTopics =
            new ConcurrentHashMap<>();

    public void add( User user, String topic ) {

        ObservableTopic observableTopic = observedTopics.get( topic );
        ObserverUser observer = new ObserverUser( user );

        if ( observableTopic == null ) {
            ObservableTopic observable = new ObservableTopic( topic );
            observable.add( observer );
            observedTopics.put( topic, observable );
        } else {
            observableTopic.add( observer );
        }
    }

    public void onMessageAdded( String topic, Message message ) {

        ObservableTopic observable = observedTopics.get( topic );

        observable.notify( message );
    }

    public int size() {
        return observedTopics.size();
    }

}
