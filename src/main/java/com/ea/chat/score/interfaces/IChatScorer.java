package com.ea.chat.score.interfaces;

import com.ea.chat.score.exceptions.ServiceUnavailableException;

public interface IChatScorer {
	int score(String message) throws ServiceUnavailableException;
}
