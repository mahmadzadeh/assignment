package com.ea.chat.score;

import com.ea.chat.score.exceptions.ServiceUnavailableException;
import com.ea.chat.score.interfaces.IChatScorer;
import org.assertj.core.util.CanIgnoreReturnValue;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

// this is so that I understand the functionality of
// the scorer. I am not testing to verify correctness of
// behaviour.
public class ChatScorerTest {


    @Ignore
    public void testScoring() throws ServiceUnavailableException {
        IChatScorer scorer  = new ChatScorer();

        System.out.println(scorer.score( "asdasdas" ));
        System.out.println( scorer.score( "asd" ));
        System.out.println(scorer.score( "gggg" ));
        System.out.println(scorer.score( "2233" ));
    }
}