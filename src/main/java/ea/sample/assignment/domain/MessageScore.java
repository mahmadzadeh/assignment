package ea.sample.assignment.domain;

public class MessageScore {

    private final int score;

    public MessageScore( int score ) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "MessageScore{" +
                "score=" + score +
                '}';
    }
}
