package com.jayfella.jme.calendar;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.props.PropertyPanel;

public class CalendarGuiState extends BaseAppState {

    GameCalendarState calendarState;

    private Container container;
    private PropertyPanel propertyPanel;

    private Label timeLabel;
    private Label dateLabel;

    @Override
    protected void initialize(Application app) {

        calendarState = app.getStateManager().getState(GameCalendarState.class);

        container = new Container();

        timeLabel = new Label("Time");
        container.addChild(timeLabel);

        dateLabel = new Label("Date");
        container.addChild(dateLabel);

        propertyPanel = new PropertyPanel("glass");

        propertyPanel.addFloatProperty("Time Mult", calendarState.getGameCalendar(), "timeMult", 0, 86400, 1);
        propertyPanel.addIntProperty("Day", calendarState.getGameCalendar(), "day", 1, 7, 1);
        propertyPanel.addIntProperty("Month", calendarState.getGameCalendar(), "month", 1, 12, 1);
        propertyPanel.addIntProperty("Year", calendarState.getGameCalendar(), "year", 1700, 2300, 1);
        propertyPanel.addIntProperty("Hour", calendarState.getGameCalendar(), "hour", 0, 23, 1);
        propertyPanel.addFloatProperty("Minute", this, "minute", 0, 59, 1);

        container.addChild(propertyPanel);

        container.setLocalTranslation(0, app.getCamera().getHeight(), 2);

        Vector3f prefSize = container.getPreferredSize();
        container.setPreferredSize(new Vector3f(300, prefSize.y, 1));

    }

    // binding methods because lemur property panel doesn't support doubles.
    public float getMinute() { return (float) calendarState.getGameCalendar().getMinute(); }
    public void setMinute(float minute) { calendarState.getGameCalendar().setMinute(minute); }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        ((SimpleApplication)getApplication()).getGuiNode().attachChild(container);
    }

    @Override
    protected void onDisable() {
        container.removeFromParent();
    }

    @Override
    public void update(float tpf) {

        timeLabel.setText(calendarState.getGameCalendar().getTimeString());

        String dateString = calendarState.getGameCalendar().getDayName()
                + " "
                + calendarState.getGameCalendar().getDay()
                + " "
                + calendarState.getGameCalendar().getMonthName()
                + " "
                + calendarState.getGameCalendar().getYear()
                + " [ "
                + calendarState.getPositionProvider().getMoonPhaseVerbose()
                + " ]";

        dateLabel.setText(dateString);
    }

}
