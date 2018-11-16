package ea.sample.assignment;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private static AtomicLong counter = new AtomicLong( 0 );

    public static long nextId() {
        return counter.addAndGet( 1 );
    }
}
