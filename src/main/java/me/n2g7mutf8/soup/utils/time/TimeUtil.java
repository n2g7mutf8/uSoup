package me.n2g7mutf8.soup.utils.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static String formatDateDiff(Calendar fromDate, Calendar toDate) {
        boolean future = false;

        if (toDate.equals(fromDate)) {
            return "now";
        } else {
            if (toDate.after(fromDate)) {
                future = true;
            }

            StringBuilder sb = new StringBuilder();
            int[] types = new int[]{1, 2, 5, 11, 12, 13};
            String[] names = new String[]{"year", "years", "month", "months", "day", "days", "hour", "hours",
                    "minute", "minutes", "second", "seconds"};
            int accuracy = 0;

            for (int i = 0; i < types.length && accuracy <= 2; ++i) {
                int diff = dateDiff(types[i], fromDate, toDate, future);

                if (diff > 0) {
                    ++accuracy;
                    sb.append(" ").append(diff).append(" ").append(names[i * 2 + (diff > 1 ? 1 : 0)]);
                }
            }

            return sb.length() == 0 ? "now" : sb.toString().trim();
        }
    }

    private static int dateDiff(int type, Calendar fromDate, Calendar toDate, boolean future) {
        int diff = 0;

        long savedDate;

        for (savedDate = fromDate.getTimeInMillis(); future && !fromDate.after(toDate) || !future && !fromDate.before
                (toDate); ++diff) {
            savedDate = fromDate.getTimeInMillis();
            fromDate.add(type, future ? 1 : -1);
        }

        --diff;
        fromDate.setTimeInMillis(savedDate);
        return diff;
    }

    public static String dateToString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return new SimpleDateFormat("MMM dd yyyy &7(hh:mm aa zz)").format(date);
    }

}
