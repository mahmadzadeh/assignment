package ea.sample.assignment.notification;

import ea.sample.assignment.domain.Message;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObservableTopic {

    private final String topic;

    private final List<ObserverUser> observers;

    public ObservableTopic( String topicName ) {
        this.topic = topicName;
        observers = new CopyOnWriteArrayList<>();
    }

    public String getName() {
        return topic;
    }

    public void add( ObserverUser observer ) {
        observers.add( observer );
    }

    public void notify( Message message ) {

        for ( ObserverUser observer : observers ) {
            observer.update( message, this.topic );
        }
    }
}
