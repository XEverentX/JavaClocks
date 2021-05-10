package org.clocks;

public interface IClock {
    public String get_time();

    public String get_alarm_messages(String separator);

    public void set_time(DefaultTime time);

    public void set_alarm();

    public void pause();
}
