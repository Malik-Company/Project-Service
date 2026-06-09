package com.pmotracker.msproject.infrastructure.util;

import com.pmotracker.msproject.infrastructure.common.Currency;

import java.util.Calendar;

public class Utility {

//    public static void main (String[] args) throws java.lang.Exception
//    {
//        System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "M") + " Miles\n");
//        System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "K") + " Kilometers\n");
//        System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "N") + " Nautical Miles\n");
//    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            double roundOff = (double) Math.round(dist * 100) / 100;
            return (roundOff);
        }
    }
    /**
     * @param startTime Start Time
     * @param endTime Stop Time
     * @param now Current Time
     * @return true if Current Time is between fromHour and toHour
     */
    public static boolean isTimeBetweenTwoTimes(String startTime, String endTime, Calendar now) {

        int fromHour =Integer.parseInt(startTime.split(":")[0]);
        int fromMinute = Integer.parseInt(startTime.split(":")[1]);

        int toHour = Integer.parseInt(endTime.split(":")[0]);
        int toMinute = Integer.parseInt(endTime.split(":")[1]);

        //Start Time
        Calendar from = Calendar.getInstance();
        from.set(Calendar.HOUR_OF_DAY, fromHour);
        from.set(Calendar.MINUTE, fromMinute);

        //End Time
        Calendar to = Calendar.getInstance();
        to.set(Calendar.HOUR_OF_DAY, toHour);
        to.set(Calendar.MINUTE, toMinute);

        if(to.before(from)) {
            if (now.after(to)) to.add(Calendar.DATE, 1);
            else from.add(Calendar.DATE, -1);
        }
        return now.after(from) && now.before(to);
    }
    public static String getAmountValue(double value){
        // TODO get currency from user context
        Currency currency = Currency.PKR;
        // Default value
        String returnValue = "Rs. "+String.format("%,.0f", value);

        if(currency.equals(Currency.USD)){
            returnValue = "$ "+String.format("%,.0f", value);
        }

        return returnValue;
    }
    public static String getHumanReadablePriceFromNumber(double number){

        if(number >= 1000000000){
            return String.format("%.0f billion", number/ 1000000000.0);
        }

        if(number >= 1000000){
            return String.format("%.0f million", number/ 1000000.0);
        }

        if(number >=1000){
            return String.format("%.0f thousand", number/ 1000.0);
        }

        return String.format("%.0f",number);

    }

}
