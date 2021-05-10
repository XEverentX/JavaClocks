package org.clocks;

public class Alarm implements IAlarm {
    public Alarm(IClockTime time, String message) {
        time_    = time;
        message_ = message;
    }

    public String ring() {
        return message_;
    }

    public IClockTime get_time() {
        return time_;
    }

    private String     message_;
    private IClockTime time_;
}
