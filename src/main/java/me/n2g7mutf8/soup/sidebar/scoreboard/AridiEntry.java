package me.n2g7mutf8.soup.sidebar.scoreboard;

import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class AridiEntry {

    private final Aridi aridi;
    @Setter
    private String text, string;
    private Team team;

    AridiEntry(Aridi aridi, String text) {
        this.aridi = aridi;
        this.text = text;
        this.string = aridi.getUniqueString();

        setUp();
    }

    public void setUp() {
        Scoreboard scoreboard = aridi.getScoreboard();

        if (scoreboard != null) {
            String name = this.string;

            if (name.length() > 16) {
                name = name.substring(0, 16);
            }

            Team team = scoreboard.getTeam(name);
            if (team == null) {
                team = scoreboard.registerNewTeam(name);
            }

            if (!team.getEntries().contains(string)) {
                team.addEntry(string);
            }

            if (!aridi.getEntries().contains(this)) {
                aridi.getEntries().add(this);
            }

            this.team = team;
        }
    }

    void send(int position) {
        if (text.length() > 16) {
            String prefix = text.substring(0, 16);
            String suffix;

            if (prefix.charAt(15) == ChatColor.COLOR_CHAR) {
                prefix = prefix.substring(0, 15);
                suffix = this.text.substring(15);
            } else if (prefix.charAt(14) == ChatColor.COLOR_CHAR) {
                prefix = prefix.substring(0, 14);
                suffix = this.text.substring(14);
            } else {
                if (ChatColor.getLastColors(prefix).equalsIgnoreCase(ChatColor.getLastColors(string))) {
                    suffix = this.text.substring(16);
                } else {
                    suffix = ChatColor.getLastColors(prefix) + this.text.substring(16);
                }
            }

            if (suffix.length() > 16) {
                suffix = suffix.substring(0, 16);
            }

            this.team.setPrefix(prefix);
            this.team.setSuffix(suffix);
        } else {
            team.setPrefix(text);
            team.setSuffix("");
        }

        Score score = aridi.getObjective().getScore(string);
        score.setScore(position);
    }

    void quit() {
        aridi.getStrings().remove(string);
        aridi.getScoreboard().resetScores(string);
    }
}