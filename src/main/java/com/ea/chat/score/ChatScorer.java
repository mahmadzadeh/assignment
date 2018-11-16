package com.ea.chat.scorer.com.ea.chat.score;

import com.ea.chat.score.exceptions.ServiceUnavailableException;
import com.ea.chat.score.interfaces.IChatScorer;

class ChatScorer implements IChatScorer {

    @Override
    public int score( String message ) throws ServiceUnavailableException {
        if ( Math.random() >= 0.95 ) {
            try {
                Thread.sleep( 10000 );
            } catch ( InterruptedException e ) {
            }
            throw new ServiceUnavailableException();
        } else {
            final long timeout = Math.round( Math.random() * 500 );
            sleepUntil( System.currentTimeMillis() + timeout );
            return (int) Math.round( message.length() * Math.PI * 42 );
        }
    }

    private static void sleepUntil( final long end ) {
        long now;
        while ( ( now = System.currentTimeMillis() ) < end ) {
            try {
                Thread.sleep( end - now );
            } catch ( InterruptedException e ) {
            }
        }
    }

}
