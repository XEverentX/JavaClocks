package org.clocks;

public class Clock {
    public static int max_alarm_count = 100;

    public Clock(IClockTime time) {
        time_ = time;
    }

    public String get_time() {
        return time_.get_time();
    }

    public String get_alarm_messages(String separator) {
        StringBuilder message = new StringBuilder("\n");
        for (int i = 0; i < alarms_count_; i++) {
            if (time_.get_time().equals(alarms_[i].get_time().get_initial_time())) {
                message.append(alarms_[i].ring()).append(separator);
            }
        }

        return message.toString();
    }

    public void set_alarm(DefaultTime time, String message) {
        alarms_[alarms_count_] = new Alarm(time, message);
        alarms_count_ += 1;
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

    public void set_time(IClockTime time) {
        time_ = time;
    }

    final static public char set_alarm   = 'a';
    final static public char set_time    = 's';
    final static public char pause       = 'p';
    final static public char get_time    = 't';
    final static public char get_message = 'm';

    private final IAlarm[]    alarms_       = new IAlarm[max_alarm_count];
    private       IClockTime  time_         = new DefaultTime();
    private       int         alarms_count_ = 0;
    private       time_status status        = time_status.RUN;
}
