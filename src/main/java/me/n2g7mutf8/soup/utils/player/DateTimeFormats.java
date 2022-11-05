package me.n2g7mutf8.soup.utils.player;

import java.text.DecimalFormat;

class DateTimeFormats {

    static ThreadLocal<DecimalFormat> REMAINING_SECONDS = ThreadLocal.withInitial(() -> new DecimalFormat("0.#"));

    static ThreadLocal<DecimalFormat> REMAINING_SECONDS_TRAILING = ThreadLocal.withInitial(() -> new DecimalFormat("0.0"));
}
