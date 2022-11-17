package shubham;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

    public static final int DELIVERY_MATCH_ID = 0;
    public static final int DELIVERY_INNING = 1;
    public static final int DELIVERY_BATTING_TEAM = 2;
    public static final int DELIVERY_BOWLING_TEAM = 3;
    public static final int DELIVERY_OVER = 4;
    public static final int DELIVERY_BALL = 5;
    public static final int DELIVERY_BATSMAN = 6;
    public static final int DELIVERY_NON_STRIKER = 7;
    public static final int DELIVERY_BOWLER = 8;
    public static final int DELIVERY_IS_SUPER_OVER = 9;
    public static final int DELIVERY_WIDE_RUNS = 10;
    public static final int DELIVERY_BYE_RUNS = 11;
    public static final int DELIVERY_LEG_BYE_RUNS = 12;
    public static final int DELIVERY_NO_BALL_RUNS = 13;
    public static final int DELIVERY_PENALTY_RUNS = 14;
    public static final int DELIVERY_BATSMAN_RUNS = 15;
    public static final int DELIVERY_EXTRA_RUNS = 16;
    public static final int DELIVERY_TOTAL_RUNS = 17;
    public static final int DELIVERY_PLAYER_DISMISSED = 18;
    public static final int DELIVERY_DISMISSED_KIND = 19;
    public static final int DELIVERY_FIELDER = 20;


    public static void main(String[] args) {
        try {
            List<Match> matches = getMatchesData();
            List<Delivery> deliveries = getDeliveriesData();

            System.out.println(findNumberOfMatchesPlayedPerYear(matches));
            System.out.println(findNumberOfMatchesWonOfAllTeam(matches));
            System.out.println(findExtraRunsConcededPerTeam(matches, deliveries));
            System.out.println(findTheMostEconomicalBowlerIn2015(matches, deliveries));
            System.out.println(findTheTeamWhoWonTheTossAndWonTheMatch(matches));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static HashMap<String, Integer> findNumberOfMatchesPlayedPerYear(List<Match> matches) {
        HashMap<String, Integer> matchesPlayedPerYear = new HashMap<>();
        int count = 0;
        for (int i = 1; i < matches.size(); i++) {
            String season = matches.get(i).getSeason();
            if (!matchesPlayedPerYear.containsKey(season)) {
                count = 0;
                matchesPlayedPerYear.put(season, count++);
            }
            else {
                matchesPlayedPerYear.put(season, count++);
            }
        }
        return matchesPlayedPerYear;
    }
    private static HashMap<String, Integer> findNumberOfMatchesWonOfAllTeam(List<Match> matches) {
        HashMap<String, Integer> matchesWonOfAllTeam = new HashMap<>();
        int count = 0;
        for (int i = 1; i < matches.size(); i++) {
            if (!matches.get(i).getResult().equals("no result")){
                String winnerTeam = matches.get(i).getWinner();
                if (!matchesWonOfAllTeam.containsKey(winnerTeam)){
                    count = 0;
                    matchesWonOfAllTeam.put(winnerTeam, count);
                }
                else{
                    matchesWonOfAllTeam.put(winnerTeam, count++);
                }
            }
        }
        return matchesWonOfAllTeam;
    }

    private static HashMap<String, Integer> findExtraRunsConcededPerTeam(List<Match> matches, List<Delivery> deliveries) {
        HashSet<String> matchId2016 = new HashSet<>();
        for (int i = 1; i < matches.size(); i++) {
            String season = matches.get(i).getSeason();
            if (season.equals("2016")){
                matchId2016.add(matches.get(i).getId());
            }
        }

        HashMap<String, Integer> extraRunsConcededPerTeam = new HashMap<>();
        for(int j=1; j < deliveries.size(); j++){
            if (matchId2016.contains(deliveries.get(j).getMatchId())){
                int extraRun = Integer.parseInt(deliveries.get(j).getExtraRuns());
                String bowlingTeam = deliveries.get(j).getBowlingTeam();
                if (!extraRunsConcededPerTeam.containsKey(bowlingTeam)){
                    extraRunsConcededPerTeam.put(bowlingTeam, extraRun);
                }
                else{
                    extraRunsConcededPerTeam.put(bowlingTeam, extraRunsConcededPerTeam.get(bowlingTeam) + extraRun);
                }
//                map.put(bowlingTeam, map.containsKey(bowlingTeam) ? map.get(bowlingTeam) + extraRun : extraRun);
            }
        }
        return extraRunsConcededPerTeam;
    }

    private static HashMap<String, Integer> findTheMostEconomicalBowlerIn2015(List<Match> matches, List<Delivery> deliveries) {
        HashSet<String> matchID2015 = new HashSet<>();
        for(int i=1; i<matches.size(); i++){
            if (matches.get(i).getSeason().equals("2015")){
                matchID2015.add(matches.get(i).getId());
            }
        }

        HashMap<String, Integer> runs = new HashMap<>();
        HashMap<String, Integer> balls = new HashMap<>();

        for(int j=1; j < deliveries.size(); j++){
            if (matchID2015.contains(deliveries.get(j).getMatchId())){
                int wideRun = Integer.parseInt(deliveries.get(j).getWideRuns());
                int noBallRun = Integer.parseInt(deliveries.get(j).getNoBallRuns());
                int totalRun = Integer.parseInt(deliveries.get(j).getBatsmanRuns());
                int totalRunForBolls = wideRun + noBallRun + totalRun;
                String bowlerName = deliveries.get(j).getBowler();
                if (!runs.containsKey(deliveries.get(j).getBowler())){
                    runs.put(bowlerName, totalRunForBolls);
                }
                else{
                    int previousRun = runs.get(bowlerName);
                    runs.put(bowlerName, previousRun + totalRunForBolls);
                }

                if (wideRun + noBallRun == 0){
                    if (balls.containsKey(bowlerName)){
                        int previousRun = balls.get(bowlerName);
                        balls.put(bowlerName, previousRun + 1);
                    }
                    else{
                        balls.put(bowlerName, 1);
                    }
                }
            }
        }
        HashMap<String, Integer> economicalPlayer = new HashMap<>();
        for (String playerName: runs.keySet()) {
            int playerRun = (int)(runs.get(playerName));
            int playerBalls = (int)(balls.get(playerName));
            economicalPlayer.put(playerName, playerRun * 6/playerBalls);
        }
        return economicalPlayer;
    }


    private static Map<String, Integer> findTheTeamWhoWonTheTossAndWonTheMatch(List<Match> matches){
        HashMap<String, Integer> teamWonTheTossAndWonTheMatch = new HashMap<>();
        for(int i = 1; i<matches.size(); i++){
            String tossWinnerTeam = matches.get(i).getTossWinner();
            String matchWinnerTeam = matches.get(i).getWinner();
            if (tossWinnerTeam.equals(matchWinnerTeam)){
//                if ()
                teamWonTheTossAndWonTheMatch.put(tossWinnerTeam, teamWonTheTossAndWonTheMatch.containsKey(tossWinnerTeam)? Integer.parseInt(String.valueOf(teamWonTheTossAndWonTheMatch.get(matches.get(i).getTossWinner()))) +1 : 1);
            }
        }
        return teamWonTheTossAndWonTheMatch;
    }



    private static List<Match> getMatchesData() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("/home/oem/Desktop/IPL-Project/ipl-project/src/shubham/matches.csv"));
        String line = null;
        List<Match> matches = new ArrayList<>();

        while (true) {
            try {
                if ((line = reader.readLine()) != null) {
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
                    match.setWinner(data[MATCH_WINNER]);
                    match.setWinByRuns(data[MATCH_WIN_BY_RUNS]);
                    match.setWinByWickets(data[MATCH_WIN_BY_WICKETS]);
                    match.setPlayerOFMatch(data[MATCH_PLAYER_OF_MATCH]);
                    match.setVenue(data[MATCH_VENUE]);
                    //match.setUmpire1(data[MATCH_UMPIRE_1]);
                    //match.setUmpire2(data[MATCH_UMPIRE_2]);
                    //match.setUmpire3(data[MATCH_UMPIRE_3]);

                    matches.add(match);
                } else {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return matches;
    }

    private static List<Delivery> getDeliveriesData() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("/home/oem/Desktop/IPL-Project/ipl-project/src/shubham/deliveries.csv"));
        String line = null;
        List<Delivery> deliveries = new ArrayList<>();
        while (true) {
            try {
                if ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    Delivery delivery = new Delivery();
                    delivery.setMatchId(data[DELIVERY_MATCH_ID]);
                    delivery.setInning(data[DELIVERY_INNING]);
                    delivery.setBattingTeam(data[DELIVERY_BATTING_TEAM]);
                    delivery.setBowlingTeam(data[DELIVERY_BOWLING_TEAM]);
                    delivery.setOver(data[DELIVERY_OVER]);
                    delivery.setBall(data[DELIVERY_BALL]);
                    delivery.setBatsman(data[DELIVERY_BATSMAN]);
                    delivery.setNonStriker(data[DELIVERY_NON_STRIKER]);
                    delivery.setBowler(data[DELIVERY_BOWLER]);
                    delivery.setIsSuperOver(data[DELIVERY_IS_SUPER_OVER]);
                    delivery.setWideRuns(data[DELIVERY_WIDE_RUNS]);
                    delivery.setByeRuns(data[DELIVERY_BYE_RUNS]);
                    delivery.setLegByeRuns(data[DELIVERY_LEG_BYE_RUNS]);
                    delivery.setNoBallRuns(data[DELIVERY_NO_BALL_RUNS]);
                    delivery.setPenaltyRuns(data[DELIVERY_PENALTY_RUNS]);
                    delivery.setBatsmanRuns(data[DELIVERY_BATSMAN_RUNS]);
                    delivery.setExtraRuns(data[DELIVERY_EXTRA_RUNS]);
                    delivery.setTotalRuns(data[DELIVERY_TOTAL_RUNS]);
//                    delivery.setPlayerDismissed(data[DELIVERY_PLAYER_DISMISSED]);
//                    delivery.setDismissalKind(data[DELIVERY_DISMISSED_KIND]);
//                    delivery.setFielder(data[DELIVERY_FIELDER]);
                    deliveries.add(delivery);
//                    matches.add(match);
                } else {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return deliveries;
    }
}

