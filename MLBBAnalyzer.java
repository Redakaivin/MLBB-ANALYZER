import java.util.*;

// Match class that holds match details
class Match {
    String hero;
    int kills, deaths, assists;
    boolean win;
    int damageDealt;
    int damageTaken;
    String heroBuild;

    // Match constructor
    Match(String hero, int kills, int deaths, int assists, boolean win, int damageDealt, int damageTaken, String heroBuild) {
        this.hero = hero;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.win = win;
        this.damageDealt = damageDealt;
        this.damageTaken = damageTaken;
        this.heroBuild = heroBuild;
    }
}

// Main class for analysis
class MLBBAnalyzer {
    static ArrayList<Match> matchHistory = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- MLBB Match Tracker ---");
            System.out.println("1. Add Match");
            System.out.println("2. Show Stats");
            System.out.println("3. Hero Counter-Picks");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addMatch();
                   case 3 -> heroCounterPick();
                case 4 -> {
                    System.out.println("Exiting... GG!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // Add match details to matchHistory
    static void addMatch() {
        System.out.print("Hero name: ");
        String hero = scanner.nextLine();

        System.out.print("Kills: ");
        int kills = scanner.nextInt();

        System.out.print("Deaths: ");
        int deaths = scanner.nextInt();

        System.out.print("Assists: ");
        int assists = scanner.nextInt();

        System.out.print("Did you win? (true/false): ");
        boolean win = scanner.nextBoolean();
        scanner.nextLine(); // consume newline

        System.out.print("Damage dealt: ");
        int damageDealt = scanner.nextInt();

        System.out.print("Damage taken: ");
        int damageTaken = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Hero build (comma-separated items): ");
        String heroBuild = scanner.nextLine();

        matchHistory.add(new Match(hero, kills, deaths, assists, win, damageDealt, damageTaken, heroBuild));
        System.out.println("Match added!");
    }


    // Show match stats
    static void showStats() {
        if (matchHistory.isEmpty()) {
            System.out.println("No match data yet.");
            return;
        }

        int totalKills = 0, totalDeaths = 0, totalAssists = 0, wins = 0;
        int totalDamageDealt = 0, totalDamageTaken = 0;
        HashMap<String, Integer> heroCount = new HashMap<>();

        for (Match m : matchHistory) {
            totalKills += m.kills;
            totalDeaths += m.deaths;
            totalAssists += m.assists;
            totalDamageDealt += m.damageDealt;
            totalDamageTaken += m.damageTaken;
            if (m.win) wins++;

            heroCount.put(m.hero, heroCount.getOrDefault(m.hero, 0) + 1);
        }

        String mostPicked = Collections.max(heroCount.entrySet(), Map.Entry.comparingByValue()).getKey();

        // Print out stats
        System.out.println("\n--- Stats ---");
        System.out.println("Matches played: " + matchHistory.size());
        System.out.println("Win rate: " + (100.0 * wins / matchHistory.size()) + "%");
        System.out.println("Average KDA: " +
                (totalKills / matchHistory.size()) + "/" +
                (totalDeaths / matchHistory.size()) + "/" +
                (totalAssists / matchHistory.size()));
        System.out.println("Total damage dealt: " + totalDamageDealt);
        System.out.println("Total damage taken: " + totalDamageTaken);
        System.out.println("Most picked hero: " + mostPicked);

        System.out.println("Most picked hero: " + mostPicked);

        System.out.println("\n--- Hero Builds ---");
        for (Match m : matchHistory) {
            System.out.println("Hero: " + m.hero + " | Build: " + m.heroBuild);
        }
    }
    static void heroCounterPick() {
        System.out.print("Enter enemy hero name: ");
        String enemyHero = scanner.nextLine();

        // Get the counter picks for the entered hero
        String[] counters = HeroCounter.getCounters(enemyHero);

        System.out.println("Counters for " + enemyHero + ":");
        for (String counter : counters) {
            System.out.println("- " + counter);
        }
    }
}

class HeroCounter {
    static HashMap<String, String[]> counterMap = new HashMap<>();

    static {
        // Predefined counters for the heroes
        counterMap.put("Selena", new String[]{"Sun", "Kalea", "Zhask", "Wanwan"});
        counterMap.put("Julian", new String[]{"Baxia", "Granger","Minisittar","Hylos","Gord"});
        counterMap.put("Lancelot", new String[]{"Bane", "Khufra","Chou","Alucard","Argus"});
        counterMap.put("Alucard", new String[]{"Eudora","Zhask","Saber","Freya"});
        counterMap.put("Granger", new String[]{"Irithel","Uranus","Xavier","Helcurt"});
        counterMap.put("Fanny", new String[]{"Zilong","Franco","Chou","Aulus"});



        // Add more heroes and counters here...
    }

    public static String[] getCounters(String hero) {
        return counterMap.getOrDefault(hero, new String[]{"No counters available"});
    }
}

