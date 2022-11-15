package shubham;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    //id	season	city	date	team1	team2	toss_winner	toss_decision	result	dl_applied	winner	win_by_runs	win_by_wickets	player_of_match	venue	umpire1	umpire2	umpire3

    public static final int MATCH_ID = 0;
    public static final int MATCH_TEAM1 = 1;
    public static final int MATCH_TEAM2 = 2;
    public static final int CITY = 3;
    public static void main(String[] args) {
        List<Match> matches = getMatchesData();  // capitalization, verb
        List<Delivery> deliveries = getDeliveriesData();

        // use data and solve problems
        /*
        findNumberOfMatchesPlayedPerYear(matches);
        findNumberOfMatchesWonOfAllTeam(matches);
        findExtraRunsConcededPerTeam(matches);
        findTheMostEconomicalBowlerIn2015(matches, deliveries);
         */
    }
    private static void findNumberOfMatchesPlayedPerYear(List<Match> matches) {
    }

    private static void findNumberOfMatchesWonOfAllTeam(List<Match> matches){
    }
    private static void findExtraRunsConcededPerTeam(List<Match> matches){
    }
    private static void findTheMostEconomicalBowlerIn2015(List<Match> matches, List<Delivery> deliveries) {
    }

    private static List<Delivery> getDeliveriesData() {
        return null;
    }

    private static List<Match> getMatchesData() throws FileNotFoundException {
        // use bufferedReader and some file reader
        BufferedReader reader = new BufferedReader(new FileReader("matches.csv"));
        String line = null;
        List<Match> matches = new ArrayList<>();
        while (true) {
            try {
                if (!(reader.readLine() != null)) {
                    line = reader.readLine(); //"12,CSK, MI, hyderabad"
                    String[] data = line.split(","); //["12", "CSK", "MI", "hyderabad"]
                    Match match = new Match();
                    match.setId(data[MATCH_ID]);
                    match.setTeam1(data[MATCH_TEAM1]);
                    match.setTeam2(data[MATCH_TEAM2]);
                    match.setCity(data[CITY]);
                    matches.add(match);
                }
                else{
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return matches;
    }

}

