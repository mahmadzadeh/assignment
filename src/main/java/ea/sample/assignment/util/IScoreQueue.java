package ea.sample.assignment.util;

import ea.sample.assignment.domain.Message;

import java.util.List;

public interface IScoreQueue {

    List<Message> dequeueItems( int size );

    void enqueue( Message message );

    int size();
}
