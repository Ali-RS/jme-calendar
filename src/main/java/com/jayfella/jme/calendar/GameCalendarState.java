package com.jayfella.jme.calendar;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

public class GameCalendarState extends BaseAppState {

    private final GameCalendar gameCalendar;
    private final PositionProvider positionProvider;

    /**
     * Creates a new CalendarState starting at the given date with the specified time speed.
     * @param year   The year to start
     * @param month  The month to start (1 - 12)
     * @param day    The day to start (1 - 7)
     * @param hour   The hour to start (0 - 23)
     * @param minute The minute to start (0 - 60)
     * @param tMult  The time multiplier. 1 = normal speed. 2 = twice as fast, etc..
     */
    public GameCalendarState(int year, int month, int day, int hour, int minute, float tMult) {
        this.gameCalendar = new GameCalendar(year,month,day,hour,minute,tMult);
        this.positionProvider = new SimplePositionProvider(this.gameCalendar);
    }

    public GameCalendar getGameCalendar() {
        return gameCalendar;
    }

    public PositionProvider getPositionProvider() {
        return positionProvider;
    }

    @Override protected void initialize(Application app) { }
    @Override protected void cleanup(Application app) { }
    @Override protected void onEnable() { }
    @Override protected void onDisable() { }

    @Override
    public void update(float tpf) {
        this.gameCalendar.update(tpf);
        this.positionProvider.update(tpf);
    }

}
