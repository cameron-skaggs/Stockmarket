/**
 * Created by Cameron Skaggs on 4/9/16.
 */

/***********************************************************
*
*   Stock Day Class: there's nothing particularly complicated
 *                   about this class implementation. It stores
 *                   data and has accessor methods for said data.
 *                   I only created accessor methods when they were
 *                   needed so not all values have getters and setters.
 *                   ie superfluous accessors are not here.
*
*********************************************************** */
public class stockDay {

    String name;
    String date;
    double openPrice;
    double highPrice;
    double lowPrice;
    double closePrice;

    public stockDay(String name, String date, double openPrice, double highPrice, double lowPrice, double closePrice) {

        this.name = name;
        this.date = date;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;

    }

    public String getDate() {
        return date;
    }

    public double getHighPrice(){
        return highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public String getName() {
        return name;
    }

}
