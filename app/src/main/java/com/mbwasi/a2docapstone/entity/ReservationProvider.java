package com.mbwasi.a2docapstone.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ReservationProvider {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ReservationItem> ITEMS = new ArrayList<ReservationItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */

    private static final int COUNT = 10;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(ReservationItem item) {
        ITEMS.add(item);
    }

    private static ReservationItem createDummyItem(int position) {
       return new ReservationItem("2","23/02/2020","17:00",position+"");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ReservationItem {
        public final String tableSize;
        public final String date;

        @Override
        public String toString() {
            return "ReservationItem{" +
                    "tableSize='" + tableSize + '\'' +
                    ", date='" + date + '\'' +
                    ", time='" + time + '\'' +
                    ", confirmationNo='" + confirmationNo + '\'' +
                    '}';
        }

        public final String time;
        public final String confirmationNo;

        public ReservationItem(String tableSize, String date, String time, String confirmationNo) {
            this.tableSize = tableSize;
            this.date = date;
            this.time = time;
            this.confirmationNo = confirmationNo;
        }

    }
}
