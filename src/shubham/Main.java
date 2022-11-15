package shubham;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int MATCH_ID = 0;
    public static final int MATCH_SEASON = 1;
    public static final int MATCH_CITY = 2;
    public static final int MATCH_DATE = 3;
    public static final int MATCH_TEAM1 = 4;
    public static final int MATCH_TEAM2 = 5;
    public static final int MATCH_TOSS_WINNER = 6;
    public static final int MATCH_TOSS_DECISION = 7;
    public static final int MATCH_RESULT = 8;
    public static final int MATCH_DL_APPLIED = 9;
    public static final int MATCH_WINNER = 10;
    public static final int MATCH_WIN_BY_RUNS = 11;
    public static final int MATCH_WIN_BY_WICKETS = 12;
    public static final int MATCH_PLAYER_OF_MATCH = 13;
    public static final int MATCH_VENUE = 14;
    public static final int MATCH_UMPIRE_1 = 15;
    public static final int MATCH_UMPIRE_2 = 16;
    public static final int MATCH_UMPIRE_3 = 17;


    public static void main(String[] args) {
        try {
            List<Match> matches = getMatchesData();
            System.out.println(matches);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<Delivery> deliveries = getDeliveriesData();

        // findNumberOfMatchesPlayedPerYear(matches);
        // findNumberOfMatchesWonOfAllTeam(matches);
        // findExtraRunsConcededPerTeam(matches);
        // findTheMostEconomicalBowlerIn2015(matches, deliveries);

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
        BufferedReader reader = new BufferedReader(new FileReader("/home/oem/Desktop/IPL-Project/ipl-project/src/shubham/matches.csv"));
        String line = null;
        List<Match> matches = new ArrayList<>();

        while (true) {
            try {
                if ((line=reader.readLine()) != null) {
                    String[] data = line.split(",");

                    Match match = new Match();
                    match.setId(data[MATCH_ID]);
                    match.setSeason(data[MATCH_SEASON]);
                    match.setCity(data[MATCH_CITY]);
                    match.setDate(data[MATCH_DATE]);
                    match.setTeam1(data[MATCH_TEAM1]);
                    match.setTeam2(data[MATCH_TEAM2]);
                    match.setTossWinner(data[MATCH_TOSS_WINNER]);
                    match.setTossDecision(data[MATCH_TOSS_DECISION]);
                    match.setResult(data[MATCH_RESULT]);
                    match.setDlApplied(data[MATCH_DL_APPLIED]);
                    match.setWinner(data[MATCH_WIN_BY_RUNS]);
                    match.setWinByWickets(data[MATCH_WIN_BY_WICKETS]);
                    match.setPlayerOFMatch(data[MATCH_PLAYER_OF_MATCH]);
                    match.setVenue(data[MATCH_VENUE]);
                    //match.setUmpire1(data[MATCH_UMPIRE_1]);
                    //match.setUmpire2(data[MATCH_UMPIRE_2]);
                    //match.setUmpire3(data[MATCH_UMPIRE_3]);

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

