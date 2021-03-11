package com.midnight.weatherforecast;

/**
 * Created by midnight on 2/13/18.
 */

public class Halt {
    long timeout, interval;
    final long TIME_OUT, INTERVAL;
    private ConditionCheck conditionCheck;
    public Halt(long timeout, long interval) {
        this.timeout = timeout;
        this.interval = interval;
        TIME_OUT = timeout;
        INTERVAL = interval;
    }


    public void execHalt(ConditionCheck condition_Check) {
        try {
            conditionCheck=condition_Check;
            while (timeout > 0 && conditionCheck.condition()) {
                Thread.sleep(interval);
                timeout = timeout - interval;
            }
        } catch (InterruptedException e) {

        } finally {
            conditionCheck.finalyAssert();
        }

    }

    public void reset() {
        this.timeout = TIME_OUT;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public ConditionCheck getConditionCheck() {
        return conditionCheck;
    }

    public void setConditionCheck(ConditionCheck conditionCheck) {
        this.conditionCheck = conditionCheck;
    }
}
