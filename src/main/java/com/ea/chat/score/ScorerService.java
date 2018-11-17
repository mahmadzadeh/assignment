package com.ea.chat.score;

import com.ea.chat.score.interfaces.IChatScorer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScorerService {

    private volatile IChatScorer scorer;

    @Bean("scorer")
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
