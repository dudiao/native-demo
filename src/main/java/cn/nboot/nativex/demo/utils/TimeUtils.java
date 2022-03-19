package cn.nboot.nativex.demo.utils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author songyinyin
 * @since 2022/3/19 21:22
 */
public class TimeUtils {

  /**
   * 获取任务花费时间（单位会根据时间的大小变化）
   *
   * @return 获取任务花费时间（单位会根据时间的大小变化）
   */
  public static String getTimeAbbreviate(long timeNanos) {
    TimeUnit unit = chooseUnit(timeNanos);
    double value = (double) timeNanos / (double) TimeUnit.NANOSECONDS.convert(1L, unit);
    return String.format(Locale.ROOT, "%.4g", value) + " " + abbreviate(unit);
  }

  private static TimeUnit chooseUnit(long nanos) {
    if (TimeUnit.DAYS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
      return TimeUnit.DAYS;
    } else if (TimeUnit.HOURS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
      return TimeUnit.HOURS;
    } else if (TimeUnit.MINUTES.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
      return TimeUnit.MINUTES;
    } else if (TimeUnit.SECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
      return TimeUnit.SECONDS;
    } else if (TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
      return TimeUnit.MILLISECONDS;
    } else {
      return TimeUnit.MICROSECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L ? TimeUnit.MICROSECONDS : TimeUnit.NANOSECONDS;
    }
  }

  private static String abbreviate(TimeUnit unit) {
    switch (unit) {
      case NANOSECONDS:
        return "ns";
      case MICROSECONDS:
        return "μs";
      case MILLISECONDS:
        return "ms";
      case SECONDS:
        return "s";
      case MINUTES:
        return "min";
      case HOURS:
        return "h";
      case DAYS:
        return "d";
      default:
        throw new AssertionError();
    }
  }
}
