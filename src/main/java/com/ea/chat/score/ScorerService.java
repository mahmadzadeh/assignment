package com.ea.chat.score;

import com.ea.chat.score.interfaces.IChatScorer;

public class ScorerService {

    private volatile IChatScorer scorer;

    public IChatScorer getScorer() {
        if ( scorer == null ) {
            synchronized (this) {
                if ( scorer == null ) {
                    scorer = new ChatScorer();
                }
            }
        }
        return scorer;
    }
}
