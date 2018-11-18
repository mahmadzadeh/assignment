package ea.sample.assignment.dto;

import ea.sample.assignment.domain.Message;

public class MessageDto {

    private long id;
    private String message;
    private int score;
    private final long userId;

    public MessageDto( Message message ) {
        this.id = message.getId();
        this.message = message.getMessage();
        this.score = message.getScore();
        this.userId = message.getUserId();
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public int getScore() {
        return score;
    }

    public long getUserId() {
        return userId;
    }
}
