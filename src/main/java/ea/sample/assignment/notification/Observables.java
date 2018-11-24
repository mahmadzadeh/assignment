package ea.sample.assignment.notification;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.User;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Collection of observable topics in the system
 */
@Component
public class Observables {

    private final ConcurrentHashMap<String, ObservableTopic> observedTopics =
            new ConcurrentHashMap<>();

    public void add( User user, String topic ) {

        ObservableTopic observableTopic = observedTopics.get( topic );
        ObserverUser observer = new ObserverUser( user );

        if ( observableTopic == null ) {
            ObservableTopic observable = new ObservableTopic( topic );
            observable.add( observer );
            observedTopics.putIfAbsent( topic, observable );
        } else {
            observableTopic.add( observer );
        }
    }

    public void onMessageAdded( String topic, Message message ) {
        Optional.ofNullable( observedTopics.get( topic ) )
                .ifPresent( o -> o.notify( message ) );
    }

    public int size() {
        return observedTopics.size();
    }

}
