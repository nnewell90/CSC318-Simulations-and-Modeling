/*
* This program simulates the growth of high country pine trees for the Northwest Lumber
* Company for 100 years. The pines are fast-growing, maturing in 5 years. The trees are
* affected by the following problems: Drought, Beetles, and Fire.
* */

public class Main {
    // Constants for the number of years and age categories
    private static final int NUM_YEARS = 100;
    private static final int NUM_AGE_CATEGORIES = 4;

    // Arrays to hold statistics for number of trees in each age category
    private static double[][] treeAgesStats = new double[NUM_AGE_CATEGORIES][2]; // [age category][0: sum, 1: sum of squares]

    // Arrays representing death and growth of trees for drought, beetles, and fire
    private static double[][][] growRain = {
            {{0.10, 70}, {0.05, 95}, {0.0, 99}},
            {{0.10, 75}, {0.05, 95}, {0.0, 95}},
            {{0.30, 60}, {0.05, 95}, {0.0, 95}},
            {{0.35, 65}, {0.05, 95}, {0.0, 96}}
    };

    private static double[][][] growBeetles = {
            {{0.10, 5}, {0.05, 95}, {0.0, 100}},
            {{0.15, 5}, {0.05, 95}, {0.0, 100}},
            {{0.30, 10}, {0.10, 90}, {0.02, 98}},
            {{0.30, 10}, {0.10, 90}, {0.02, 98}}
    };

    private static double[][][] growFire = {
            {{0.15, 10}, {0.10, 90}, {0.05, 95}},
            {{0.18, 12}, {0.12, 88}, {0.07, 93}},
            {{0.22, 15}, {0.15, 85}, {0.10, 90}},
            {{0.30, 20}, {0.20, 80}, {0.15, 85}}
    };

    public static void main(String[] args) {
        // Run the simulation for each policy
        runSimulation("a. Seed the clouds to change the rain distribution: ");
        runSimulation("b. Spray all 3 year old trees: ");
        runSimulation("c. Spray 50% of the 3 year old trees and 50% of the 4 year old trees");
    }

    public static void runSimulation(String policy) {
        System.out.println("Policy: " + policy);

        // Reset statistics for each policy run
        resetStatistics();

        // Simulate forest growth for 100 years
        for (int year = 1; year <= NUM_YEARS; year++) {
            // Calculate weather for this year
            int rainfallNoSeeding = RainfallRandom.RainNoSeed();
            int rainfallSeeding = RainfallRandom.RainSeed();

            System.out.println("Rainfall no seeding: " + rainfallNoSeeding);
            System.out.println("Rainfall seeding: " + rainfallSeeding);

            // Calculate trees dying and not growing from rainfall without seeding
            double[][] dtreesRain = calculateTreesDying(rainfallNoSeeding);
            double[][] nogrowRain = calculateTreesNotGrowing(rainfallNoSeeding);

            // Subtract dying trees
            subtractDyingTrees(nogrowRain, dtreesRain);

            // Calculate beetle kills
            calculateBeetleKills(nogrowRain, growBeetles[rainfallNoSeeding]);

            // Subtract beetle kills
            subtractBeetleKills(nogrowRain, growBeetles[rainfallNoSeeding]);

            // Calculate fire deaths
            calculateFireDeaths(nogrowRain, growFire[rainfallNoSeeding]);

            // Subtract fire deaths
            subtractFireDeaths(nogrowRain, growFire[rainfallNoSeeding]);

            // Age all trees
            ageTrees(nogrowRain);

            // Collect statistics for number of trees in each age category
            collectStatistics(nogrowRain);

            // Harvest all 4-year-old stock
            harvest4YearOldStock(nogrowRain);

            // Plant all vacant acres with one-year stock
            plantOneYearStock(nogrowRain);
        }

        // Calculate averages and variances for 1 to 4 age categories
        calculateMean();
        calculateVariance();
    }

    // Method to reset statistics for each policy run
    private static void resetStatistics() {
        for (int i = 0; i < NUM_AGE_CATEGORIES; i++) {
            treeAgesStats[i][0] = 0; // sum
            treeAgesStats[i][1] = 0; // sum of squares
        }
    }

    // Method to calculate trees dying from rainfall
    private static double[][] calculateTreesDying(int rainfallNoSeeding) {
        double[][] dtreesRain = new double[NUM_AGE_CATEGORIES][2];
        for (int age = 0; age < NUM_AGE_CATEGORIES; age++) {
            dtreesRain[age][0] = growRain[rainfallNoSeeding][age][0];
            dtreesRain[age][1] = 100 - dtreesRain[age][0];
        }
        return dtreesRain;
    }

    // Method to calculate trees not growing from rainfall
    private static double[][] calculateTreesNotGrowing(int rainfallNoSeeding) {
        double[][] nogrowRain = new double[NUM_AGE_CATEGORIES][2];
        for (int age = 0; age < NUM_AGE_CATEGORIES; age++) {
            nogrowRain[age][0] = growRain[rainfallNoSeeding][age][1];
            nogrowRain[age][1] = 100 - nogrowRain[age][0];
        }
        return nogrowRain;
    }

    // Method to collect statistics for number of trees in each age category
    private static void collectStatistics(double[][] nogrowRain) {
        for (int age = 0; age < NUM_AGE_CATEGORIES; age++) {
            treeAgesStats[age][0] += nogrowRain[age][0]; // sum
            treeAgesStats[age][1] += Math.pow(nogrowRain[age][0], 2); // sum of squares
        }
    }

    private static void calculateMean() {
        for (int age = 0; age < NUM_AGE_CATEGORIES; age++) {
            double mean = treeAgesStats[age][0] / NUM_YEARS;
            System.out.println("Mean for age category " + (age + 1) + ": " + mean);
        }
    }

    private static void calculateVariance() {
        for (int age = 0; age < NUM_AGE_CATEGORIES; age++) {
            double mean = treeAgesStats[age][0] / NUM_YEARS;
            double sumSquaredDifferences = treeAgesStats[age][1] / NUM_YEARS - Math.pow(mean, 2);
            System.out.println("Variance for age category " + (age + 1) + ": " + sumSquaredDifferences);
        }
    }

    // Method to subtract dying trees
    private static void subtractDyingTrees(double[][] treeAge, double[][] dtreesRain) {
        for (int age = 0; age < NUM_AGE_CATEGORIES; age++) {
            for (int growing = 0; growing < 2; growing++) {
                // calculate number of trees dying
                double mortality = dtreesRain[age][0] / 100.0;
                double dying = treeAge[age][growing] * mortality;
                // subtract dying trees from age categories
                treeAge[age][growing] -= dying;
            }
        }
    }

    // Method to calculate beetle kills
    private static void calculateBeetleKills(double[][] treeAge, double[][] killBeetle) {
        for (int age = 0; age < 4; age++) {
            for (int growing = 0; growing < 2; growing++) {
                // calculate number of trees killed by beetles
                double mortality = killBeetle[age][0] / 100.0;
                double killedTrees = treeAge[age][growing] * mortality;
                // subtract killed trees from age categories
                treeAge[age][growing] -= killedTrees;
            }
        }
    }

    // Method to subtract beetle kills
    private static void subtractBeetleKills(double[][] treeAge, double[][] killBeetle) {
        for (int age = 0; age < 4; age++) {
            for (int growing = 0; growing < 2; growing++) {
                // subtract trees killed by beetles
                treeAge[age][growing] -= (treeAge[age][growing] * (killBeetle[age][0] / 100.0));
            }
        }
    }

    // Method to calculate fire deaths
    private static void calculateFireDeaths(double[][] treeAge, double[][] killFire) {
        for (int age = 0; age < 4; age++) {
            for (int growing = 0; growing < 2; growing++) {
                // calculate number of trees killed by fire
                double mortality = killFire[age][0] / 100.0;
                double fireDeath = treeAge[age][growing] * mortality;
                // subtract killed trees from age categories
                treeAge[age][growing] -= fireDeath;
            }
        }
    }

    // Method to subtract fire deaths
    private static void subtractFireDeaths(double[][] treeAge, double[][] killFire) {
        for (int age = 0; age < 4; age++) {
            for (int growing = 0; growing < 2; growing++) {
                // subtract trees killed by fire
                double mortality = killFire[age][0] / 100.0;
                treeAge[age][growing] *= (1 - mortality);
            }
        }
    }

    // Method to age all trees
    private static void ageTrees(double[][] treeAge) {
        for (int age = 0; age < 4; age++) {
            for (int growing = 0; growing < 2; growing++) {
                if (treeAge[age][growing] > 0) {
                    if (age < NUM_AGE_CATEGORIES - 1 && treeAge[age][growing] > 0) {
                        treeAge[age][growing]++;
                    }
                }
            }
        }
    }

    // Method to harvest all 4-year-old stock
    private static void harvest4YearOldStock(double[][] treeAge) {
        int harvest = (int) treeAge[3][0];
        treeAge[3][0] = 0;
        int replant = harvest * 5;
        treeAge[0][0] += replant;
    }

    // Method to plant 1-year-old stock
    private static void plantOneYearStock(double[][] treeAge) {
        double vacantAcre = 0;
        for (double[] age : treeAge) {
            vacantAcre += age[1];
        }

        double perAcre = 0;
        for (int age = 0; age < NUM_AGE_CATEGORIES; age++) {
            perAcre += (age + 1) * treeAge[age][0];
        }
        perAcre /= vacantAcre;

        for (int age = 0; age < NUM_AGE_CATEGORIES; age++) {
            treeAge[age][1] += perAcre;
        }

        // reset one-year-old stock
        treeAge[0][0] = 0;
    }
}

class RainfallRandom {
    public static int RainNoSeed() {
        return (int) (Math.random() * 3); // Returns 0, 1, or 2 for Drought, Moderate, or Heavy rainfall
    }

    public static int RainSeed() {
        return (int) (Math.random() * 3); // Returns 0, 1, or 2 for Drought, Moderate, or Heavy rainfall
    }
}

