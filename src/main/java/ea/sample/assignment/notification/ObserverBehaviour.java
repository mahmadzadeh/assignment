package ea.sample.assignment.notification;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.notification.exceptions.UnableToFullfilException;

public interface ObserverBehaviour {

    void doOnUpdate( ObserverUser observer, Message message ) throws UnableToFullfilException;

}
