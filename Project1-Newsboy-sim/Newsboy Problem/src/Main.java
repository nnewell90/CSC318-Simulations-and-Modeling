/*
* This program simulates a newsboys' paper routine by using his experience for customer
* demand to determine the best reordering policy. He buys papers for 35 cents and sells
* them for $1.00 a piece. All extra papers he has left at the end of the day can be
* returned for 5 cents each.
*
* This program will determine the best reordering policy of the following:
*       a. order 16 papers each day
*       b. order the same number each day as called for the last
*       c. order one less each day than called for the previous day
*
* It will run each reordering policy for 1,000 days and give the results.
*
* New statistics will be included in this program to show the average papers sold per
* day, the average papers demanded per day, and the variance for the papers sold and
* demanded. You will also see the average profit per day and the variance of the daily
* profit.
*
* @author: Nicole Newell
* @since: January 22, 2024
* */

public class Main {
    public static void main(String[] args) {

        // runs the simulation three times
        runSimulation();
        runSimulation();
        runSimulation();
    }

    private static void runSimulation() {

        int day;
        stats statistics = new stats();
        newsboy jim = new newsboy();
        dmdproc wantpaper = new dmdproc();
        int dmd;

        // tests jim's behavior for 1,000 days
        for (day = 1; day <= 1000; day++) {
            // gets demand for the day
            dmd = wantpaper.dmdtoday();
            // gives jim the demand for the day
            jim.setDemand(dmd);
            // records statistics for the day
            statistics.setProfit(jim.getProfit());
            statistics.setSold(jim.getSold());
            statistics.setDemand(dmd);
            // order papers for next day
            jim.order();

            // prints the results for a 5-day test run
            if (day >= 500 && day <= 505) {
                System.out.println("For day " + day + ", demand: " + dmd + " sold: " + jim.getSold());
                System.out.println("Profit: $" + jim.getProfit() + " ordered: " + jim.getOrdered());
            } // end 5-day test run
        } // end 1,000 days of sales

        // prints the resulting statistics for the 1,000 days of sales
        System.out.println("****************Statistics for 1,000 Days of Sales*******************");
        System.out.println("Average profit: $" + statistics.getAverageProfit());
        System.out.println("Average papers sold per day: " + statistics.getAverageSold());
        System.out.println("Average papers demanded per day: " + statistics.getAverageDemand());
        System.out.println("Variance in profit: " + statistics.getProfitVariance());
        System.out.println("Variance in papers sold: " + statistics.getSoldVariance());
        System.out.println("Variance in papers demanded: " + statistics.getDemandVariance());
        System.out.println("Standard Deviation in profit: " + statistics.getProfitStdev());
        System.out.println("Standard Deviation in papers sold: " + statistics.getSoldStdev());
        System.out.println("Standard Deviation in papers demanded: " + statistics.getDemandStdev());
        System.out.println("Count: " + statistics.getCount());
        System.out.println();     // prints an empty line in between simulation runs
    } // end runSimulation
} // end Main

/* Creates the class for the newsboy */
class newsboy {
    private int demand;
    private int ordered;
    private int bought;
    private int sold;
    private double profit;

    // creates the constructor for newsboy
    public newsboy() {
        demand = 0;
        ordered = 15;
        bought = 0;
        sold = 0;
        profit = 0.0;
    } // end of newsboy constructor

    // creates the order policy
    public int order() {
        int x;
        x = 16;
        ordered = x;
        return x;
    } // end of order

    // creates the behavior of the newsboy
    private void behavior() {
        bought = ordered;

        // calculates the papers sold
        if (demand >= bought)
            sold = bought;
        else
            sold = demand;
        // calculates the profit
        profit = 1.00 * sold - 0.35 * bought;
        // order for tomorrow
        ordered = order();
    } // end of behavior

    // creates the demand given to the newsboy
    public void setDemand(int x) {
        demand = x;
        behavior();
    } // end setDemand

    // gets the profit, papers sold, and papers ordered
    public double getProfit() {
        return profit;
    }
    public int getSold() {
        return sold;
    }
    public int getOrdered() {
        return ordered;
    }
} // end class newsboy
/* Creates the class that calculates the statistics */
class stats {
    private double profit;
    private double sold;
    private double demand;
    private double psum;
    private double ssum;
    private double dsum;
    private double psum2;
    private double ssum2;
    private double dsum2;
    private double averageProfit;
    private double averageSold;
    private double averageDemand;
    private double stdevProfit;
    private double stdevSold;
    private double stdevDemand;
    private double varianceProfit;
    private double varianceSold;
    private double varianceDemand;
    private int count;

    // creates the constructor for stats
    public stats() {
        profit = sold = demand = psum = ssum = dsum = psum2 = ssum2 = dsum2 = averageProfit =
                averageSold = averageDemand = stdevProfit = stdevSold = stdevDemand = varianceProfit =
                        varianceSold = varianceDemand = 0;
        count = 0;
    }
    // sets profit and calculates the statistics for the day
    public void setProfit(double x) {
        profit = x;
        psum += profit;
        psum2 += profit * profit;
    } // end setProfit
    // sets papers sold
    public void setSold(double x) {
        sold = x;
        ssum += sold;
        ssum2 += sold * sold;
    }
    // sets the demand
    public void setDemand(double x) {
        demand = x;
        dsum += demand;
        dsum2 += demand * demand;
        count++;
        averageProfit = psum / count;
        averageSold = ssum / count;
        averageDemand = dsum / count;
        varianceProfit = psum2 / count - averageProfit * averageProfit;
        varianceSold = ssum2 / count - averageSold * averageSold;
        varianceDemand = dsum2 / count - averageDemand * averageDemand;
        stdevProfit = Math.sqrt(varianceProfit);
        stdevSold = Math.sqrt(varianceSold);
        stdevDemand = Math.sqrt(varianceDemand);
    }

    // gets the values for profit, average, variance, standard dev, and count
    public double getAverageProfit() {
        return averageProfit;
    }
    public double getAverageSold() {
        return averageSold;
    }
    public double getAverageDemand() {
        return averageDemand;
    }
    public double getProfitVariance() {
        return varianceProfit;
    }
    public double getSoldVariance() {
        return varianceSold;
    }
    public double getDemandVariance() {
        return varianceDemand;
    }
    public double getProfitStdev() {
        return stdevProfit;
    }
    public double getSoldStdev() {
        return stdevSold;
    }
    public double getDemandStdev() {
        return stdevDemand;
    }
    public int getCount() {
        return count;
    }
} // end class stats
/* Creates the process generator for the demand of papers */
class dmdproc {
    private int demand;

    // creates the constructor for dmdproc
    public dmdproc() {
        demand = 0;
    }

    // creates the process generator for today's demand
    public int dmdtoday() {
        // demand for papers: 15-8.33%, 16-8.33%, 17-4.17%, 18-16.67%, 19-16.67%, 20-8.33%
        int x;

        x = (int)(Math.random() * 100);
        if (x <= 20)
            demand = 15;
        else if (x <= 30)
            demand = 16;
        else if (x <= 60)
            demand = 17;
        else if (x <= 70)
            demand = 18;
        else if (x <= 80)
            demand = 19;
        else
            demand = 20;

        return demand;
    } // end dmdtoday
} // end dmdproc