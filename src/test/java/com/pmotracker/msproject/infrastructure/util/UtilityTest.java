
package com.pmotracker.msproject.infrastructure.util;

import org.junit.jupiter.api.Test;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilityTest {

    @Test
    void testDistance() {
        // Same coordinates
        assertEquals(0.0, Utility.distance(32.9697, -96.80322, 32.9697, -96.80322, "M"));

        // Test with "M" (Miles)
        assertEquals(262.68, Utility.distance(32.9697, -96.80322, 29.46786, -98.53506, "M"));

        // Test with "K" (Kilometers)
        assertEquals(422.74, Utility.distance(32.9697, -96.80322, 29.46786, -98.53506, "K"));

        // Test with "N" (Nautical Miles)
        assertEquals(228.11, Utility.distance(32.9697, -96.80322, 29.46786, -98.53506, "N"));
    }

    @Test
    void testIsTimeBetweenTwoTimes() {
        Calendar now = Calendar.getInstance();

        // Test case 1: Current time is within the range
        now.set(Calendar.HOUR_OF_DAY, 14);
        now.set(Calendar.MINUTE, 30);
        assertTrue(Utility.isTimeBetweenTwoTimes("10:00", "18:00", now));

        // Test case 2: Current time is before the range
        now.set(Calendar.HOUR_OF_DAY, 9);
        now.set(Calendar.MINUTE, 0);
        assertFalse(Utility.isTimeBetweenTwoTimes("10:00", "18:00", now));

        // Test case 3: Current time is after the range
        now.set(Calendar.HOUR_OF_DAY, 19);
        now.set(Calendar.MINUTE, 0);
        assertFalse(Utility.isTimeBetweenTwoTimes("10:00", "18:00", now));
        
        // Test case 4: Time range spans across midnight
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 0);
        assertTrue(Utility.isTimeBetweenTwoTimes("22:00", "02:00", now));
        
        now.set(Calendar.HOUR_OF_DAY, 1);
        now.set(Calendar.MINUTE, 0);
        assertTrue(Utility.isTimeBetweenTwoTimes("22:00", "02:00", now));
        
        now.set(Calendar.HOUR_OF_DAY, 3);
        now.set(Calendar.MINUTE, 0);
        assertFalse(Utility.isTimeBetweenTwoTimes("22:00", "02:00", now));
    }

    @Test
    void testGetAmountValue() {
        // The method has a hardcoded currency, so we only test that one.
        assertEquals("Rs. 1,000", Utility.getAmountValue(1000.0));
        assertEquals("Rs. 1,234,568", Utility.getAmountValue(1234567.89));
    }
    
    @Test
    void testGetHumanReadablePriceFromNumber() {
        assertEquals("1.00 k", Utility.getHumanReadablePriceFromNumber(1000));
        assertEquals("10.00 k", Utility.getHumanReadablePriceFromNumber(10000));
        assertEquals("1.00 m", Utility.getHumanReadablePriceFromNumber(1000000));
        assertEquals("1.00 b", Utility.getHumanReadablePriceFromNumber(1000000000));
        assertEquals("500.00", Utility.getHumanReadablePriceFromNumber(500));
    }
}
