package Ultimate.huh.core.utils;

import org.bukkit.scoreboard.Team;

public class ScoreboardUtil {

    public static void changeTeamOptions(Team team) {

        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        team.setAllowFriendlyFire(false);
        team.setCanSeeFriendlyInvisibles(true);
    }
}
