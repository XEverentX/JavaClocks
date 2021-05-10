package org.clocks;


import java.sql.*;

public class Clock {
    public Clock(DefaultTime time, int index) {
        time_  = time;
        index_ = index;
        try {
            alarms_ = new AlarmDBClient(index_);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String get_time() {
        return time_.get_time();
    }

    public String get_alarm_messages(String separator) {
        String result = "";
        try {
            result = alarms_.ring(time_, separator);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public void set_alarm(DefaultTime time, String message) {
        try {
            alarms_.set_alarm(time, message);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void pause() {
        if (status == time_status.RUN) {
            time_.freeze();
            status = time_status.PAUSED;
        } else {
            time_.unfreeze();
            status = time_status.RUN;
        }
    }

    public void set_time(DefaultTime time) {
        time_ = time;
    }

    final static public char set_alarm   = 'a';
    final static public char set_time    = 's';
    final static public char pause       = 'p';
    final static public char get_time    = 't';
    final static public char get_message = 'm';

    private AlarmDBClient alarms_;
    private DefaultTime   time_;
    private int           index_;
    private time_status   status = time_status.RUN;
}
