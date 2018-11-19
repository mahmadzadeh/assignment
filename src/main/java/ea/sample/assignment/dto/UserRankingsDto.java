package ea.sample.assignment.dto;

import ea.sample.assignment.domain.User;

import java.util.Collections;
import java.util.List;

public class UserRankingsDto {
    private final List<User> rankings;

    public UserRankingsDto( List<User> topRanking ) {
        rankings = Collections.unmodifiableList( topRanking );
    }

    public List<User> getRankings() {
        return Collections.unmodifiableList( rankings );
    }


}
