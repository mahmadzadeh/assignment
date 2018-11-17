package ea.sample.assignment.domain;

public class MessageScore {

    private final int score;
    private final long id;
    private final String msgString;

    private final long messageId;

    public MessageScore( long id, long messageId, int score, String msgString ) {
        this.score = score;
        this.messageId = messageId;
        this.id = id;
        this.msgString = msgString;
    }

    public int getScore() {
        return score;
    }

    public long getId() {
        return id;
    }

    public long getMessageId() {
        return messageId;
    }

    @Override
    public String toString() {
        return "MessageScore{" +
                "score=" + score +
                ", id=" + id +
                ", messageId=" + messageId +
                '}';
    }

    public String getMsgString() {
        return msgString;
    }
}
