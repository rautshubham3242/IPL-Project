package shubham;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//    public static final int MATCH_UMPIRE_1 = 15;
//    public static final int MATCH_UMPIRE_2 = 16;
//    public static final int MATCH_UMPIRE_3 = 17;

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
        HashMap<String, Integer> map = new HashMap<>();
        int count = 0;
        for (int i = 1; i < matches.size(); i++) {
            if (map.containsKey(matches.get(i).getSeason())) {
                map.put(matches.get(i).getSeason(), count++);
            } else {
                count = 0;
                map.put(matches.get(i).getSeason(), count++);
            }
        }
        return map;
    }

    private static HashMap<String, Integer> findNumberOfMatchesWonOfAllTeam(List<Match> matches) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 1; i < matches.size(); i++) {
            if (!matches.get(i).getResult().equals("no result"))
            map.put(matches.get(i).getWinner(), map.containsKey(matches.get(i).getWinner()) ? map.get(matches.get(i).getWinner()) + 1 : 1);
        }
        return map;
    }

    private static HashMap<String, Integer> findExtraRunsConcededPerTeam(List<Match> matches, List<Delivery> deliveries) {
        ArrayList<String> matchId2016 = new ArrayList<>();
        for (int i = 1; i < matches.size(); i++) {
            if (matches.get(i).getSeason().equals("2016")){
                matchId2016.add(matches.get(i).getId());
            }
        }

        HashMap<String, Integer> map = new HashMap<>();
        for(int j=1; j < deliveries.size(); j++){
            if (matchId2016.contains(deliveries.get(j).getMatchId())){
                int extraRun = Integer.parseInt(deliveries.get(j).getExtraRuns());
                map.put(deliveries.get(j).getBowlingTeam(), map.containsKey(deliveries.get(j).getBowlingTeam()) ? map.get(deliveries.get(j).getBowlingTeam()) + extraRun : extraRun);
            }
        }
        return map;
    }

    private static HashMap<String, Integer> findTheMostEconomicalBowlerIn2015(List<Match> matches, List<Delivery> deliveries) {
        ArrayList<String> matchID2015 = new ArrayList<>();
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

                runs.put(deliveries.get(j).getBowler(), runs.containsKey(deliveries.get(j).getBowler()) ? runs.get(deliveries.get(j).getBowler()) + totalRunForBolls : totalRunForBolls);
                if (wideRun + noBallRun == 0){
                    balls.put(deliveries.get(j).getBowler(), balls.containsKey(deliveries.get(j).getBowler()) ? balls.get(deliveries.get(j).getBowler()) + 1 : 1);
                }
            }
        }
        HashMap<String, Integer> economicalPlayer = new HashMap<>();
        for (String playerName: runs.keySet()) {
                economicalPlayer.put(playerName, (int)(runs.get(playerName) * 6f/balls.get(playerName)));
        }
        return economicalPlayer;
    }


    private static Map<String, Integer> findTheTeamWhoWonTheTossAndWonTheMatch(List<Match> matches){
        HashMap<String, Integer> map = new HashMap<>();
        for(int i = 1; i<matches.size(); i++){
            if (matches.get(i).getTossWinner().equals(matches.get(i).getWinner())){
                map.put(matches.get(i).getTossWinner(), map.containsKey(matches.get(i).getTossWinner())? Integer.parseInt(String.valueOf(map.get(matches.get(i).getTossWinner()))) +1 : 1);
            }
        }
        return map;
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
        //match_id	inning	batting_team	bowling_team	over	ball	batsman	non_striker	bowler	is_super_over	wide_runs	bye_runs
//    legbye_runs	noball_runs	penalty_runs	batsman_runs	extra_runs	total_runs	player_dismissed	dismissal_kind	fielder

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

