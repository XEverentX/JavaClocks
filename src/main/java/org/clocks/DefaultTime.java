package org.clocks;

import java.security.InvalidParameterException;

public class DefaultTime implements IClockTime {
    public DefaultTime() {
        initial_time_ = 0;
        start_time_   = System.currentTimeMillis();
    }

    public DefaultTime(long hours, long minutes, long seconds) {
        if (invalid_time(hours, minutes, seconds)) {
            throw new InvalidParameterException("Invalid time format");
        }

        initial_time_ = (seconds + (60 * (minutes + 60 * (hours)))) * 1000;
        start_time_   = System.currentTimeMillis();
    }

    public void set_time(long initial_time) {
        initial_time_ = initial_time;
        start_time_   = System.currentTimeMillis();
    }

    private String convert_time(long current_time) {
        long seconds = current_time % 60;
        current_time /= 60;

        long minutes = current_time % 60;
        current_time /= 60;

        long hours = current_time % 24;
        return hours + ":" +
                minutes + ":" +
                seconds;
    }

    public String get_time() {
        return convert_time(get_current_time());
    }

    private long get_current_time() {
        if (!is_freeze_) {
            long duration = System.currentTimeMillis() - start_time_;
            return (initial_time_ + duration) / 1000;

        } else {
            return initial_time_ / 1000;
        }
    }

    public String get_initial_time() {
        return convert_time(initial_time_ / 1000);
    }

    public void freeze() {
        if (!is_freeze_) {
            long duration = System.currentTimeMillis() - start_time_;
            initial_time_ = initial_time_ + duration;
            is_freeze_    = true;
        }
    }

    public void unfreeze() {
        if (is_freeze_) {
            start_time_ = System.currentTimeMillis();
            is_freeze_  = false;
        }
    }

    public long get_hours() {
        long current_time = get_current_time();

        return (current_time / 3600) % 24;
    }

    public long get_minutes() {
        long current_time = get_current_time();

        return (current_time / 60) % 60;
    }

    public long get_seconds() {
        long current_time = get_current_time();

        return current_time % 60;
    }

    static public boolean invalid_time(long h, long m, long s) {
        return (h > 24 || h < 0 || m > 60 || m < 0 || s > 60 || s < 0);
    }

    private long    start_time_;
    private long    initial_time_;
    private boolean is_freeze_;
}
