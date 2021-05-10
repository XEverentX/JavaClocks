package org.clocks;

public interface IClockTime {
    public void set_time(long initial_time);

    public String get_time();

    public String get_initial_time();

    public void freeze();

    public void unfreeze();

    public long get_hours();

    public long get_minutes();

    public long get_seconds();

}
