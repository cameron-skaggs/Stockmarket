import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Cameron Skaggs on 4/9/16.
 */


/**********************************************************
 *
 *  Main Method: Will Read the file and print crazy days and split
 *
 *  (1) Initialize array list of different companies
 *
 *  (2) Grab the Stockmarket file
 *
 *  (3) Read the file if it exists
 *
 *  (4) Separate the file text into different lists
 *      depending on their company name
 *
 *  (5) Find the "crazy days" and the splits
 *
 **********************************************************/
public class Main {

    /* (1) Initialize array list*/
    private static ArrayList<stockDay> dayList = new ArrayList<>();
    private static ArrayList<stockDay> APPLlist = new ArrayList<>();
    private static ArrayList<stockDay> FBlist = new ArrayList<>();
    private static ArrayList<stockDay> GOOGlist = new ArrayList<>();
    private static ArrayList<stockDay> IBMlist = new ArrayList<>();
    private static ArrayList<stockDay> INTClist = new ArrayList<>();
    private static ArrayList<stockDay> MSFTlist = new ArrayList<>();
    private static ArrayList<stockDay> YHOOlist = new ArrayList<>();


    public static void main(String[] args) {

        /* (2) Grab the Stockmarket file*/
        File stockFile = new File("src/Stockmarket-1990-2015.txt");

        /* (3) Read the file if it exists */
        try {
            readFile(stockFile);
        } catch (IOException e) {
            System.out.println("File Not Found");
        }

        /* Separate the file text into different lists */
        APPLlist = splitStockDay(dayList, "AAPL");
        FBlist = splitStockDay(dayList, "FB");
        GOOGlist = splitStockDay(dayList, "GOOG");
        IBMlist = splitStockDay(dayList, "IBM");
        INTClist = splitStockDay(dayList, "INTC");
        MSFTlist = splitStockDay(dayList, "MSFT");
        YHOOlist = splitStockDay(dayList, "YHOO");

        /*(5) Find the "crazy days" and the splits*/
        getCrazyDays(APPLlist);
        getSplit(APPLlist);

        getCrazyDays(FBlist);
        getSplit(FBlist);

        getCrazyDays(GOOGlist);
        getSplit(GOOGlist);

        getCrazyDays(IBMlist);
        getSplit(IBMlist);

        getCrazyDays(INTClist);
        getSplit(INTClist);

        getCrazyDays(MSFTlist);
        getSplit(MSFTlist);

        getCrazyDays(YHOOlist);
        getSplit(YHOOlist);
    }

    /***********************************************************
     *
     *  Read File: Assuming that the file is formatted correctly,
     *             this method will parse through the file and make
     *             the name and date an array of strings, and all of
     *             the prices double. These are all then put into a day
     *             and that day is added to the day array list.
     *
     ***********************************************************/
    private static void readFile(File file) throws IOException {

        Scanner scanner = new Scanner(file);
        StringBuilder text = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            text.append(newLine);
            /* NOTE: Splits lines by tab */
            String[] values = line.split("\\t");

            String name = values[0];
            String date = values[1];
            double openPrice = Double.parseDouble(values[2]);
            double highPrice = Double.parseDouble(values[3]);
            double lowPrice = Double.parseDouble(values[4]);
            double closePrice = Double.parseDouble(values[5]);

            stockDay day = new stockDay(name, date, openPrice, highPrice, lowPrice, closePrice);
            dayList.add(day);
        }
    }

    /**********************************************************
     *
     *    Split Stock Day: This method splits an array list into
     *                    only having the days of one company name.
     *                    It's parameters are an array list which it will
     *                    split up and a string which should be
     *                    the company name.
     *
     ***********************************************************/

    private static ArrayList<stockDay> splitStockDay(ArrayList<stockDay> stockList, String comp){

        ArrayList<stockDay> compList = new ArrayList<>();

        for (int i =0; i<stockList.size(); i++){

            if(stockList.get(i).getName().equals(comp)){
                compList.add(stockList.get(i));
            }
        }
        return compList;
    }

    /**********************************************************
    *
    *   Get Crazy Days: This method iterates through an entire
    *                   array list and finds the crazy days while
    *                   also keeping track of the total value of
    *                   crazy days. Prints out the crazy days
    *                   the craziest date and the craziest
    *                   percentage difference.
    *
    * *********************************************************/

    private static void getCrazyDays(ArrayList<stockDay> dayList) {

        System.out.println("Processing " + dayList.get(0).getName());
        System.out.println("=====================");

        String craziestDate = "None";
        double craziestPrice = 0;
        int total = 0;
        for (int i = 0; i < dayList.size(); i++) {

            stockDay day = dayList.get(i);

            double highPrice = day.getHighPrice();
            double lowPrice = day.getLowPrice();
            double crazyPrice = (highPrice - lowPrice) / highPrice;
            crazyPrice *= 100;
            double standard = 15.0;

            if (crazyPrice >= standard) {
                System.out.println("Crazy day: " + day.getDate() + " " + Math.round(crazyPrice*100)/100.0 + "%");
                total++;
                if(crazyPrice > craziestPrice){
                    craziestPrice = crazyPrice;
                    craziestDate = day.getDate();

                }
            }
        }
        System.out.println("Total Crazy Days : " + total);
        System.out.println("The craziest day: " + craziestDate + " " + Math.round(craziestPrice *100)/100.0 + "% \n");
    }
/**********************************************************
 *
 *  Get Split: Iterates through the entire array three times
 *             Each time it checks for the specific split
 *             (2:1, 3:1, 3:2). When it finds a split it makes
 *             the correct output. The total is also iterated.
 *
 **********************************************************/

    private static void getSplit(ArrayList<stockDay> list){

        int splitCount = 0;
        for (int j = 0; j < 3; j++){

            double difference = 0;
            String splitStr;
            switch (j){
                case 0: difference = 2.0;
                        splitStr = "2:1";
                        break;
                case 1: difference = 3.0;
                        splitStr = "3:1";
                        break;
                case 2: difference= 1.5;
                        splitStr = "3:2";
                        break;
                default: splitStr = "Invalid Use";
                        break;
            }

            for(int i = 1; i < list.size(); i++) {

                stockDay day = list.get(i);
                stockDay nextDay = list.get(i - 1);

                double openPrice = nextDay.getOpenPrice();
                double closePrice = day.getClosePrice();
                double split = closePrice / openPrice;

                if (Math.abs(split-difference) < 0.05) {
                    System.out.println(splitStr + " stock split on " + day.getDate() + " " + closePrice + " --> " + openPrice);
                    splitCount++;
                }
            }
        }
        System.out.println("Total number of splits: " + splitCount + '\n');
    }
}

