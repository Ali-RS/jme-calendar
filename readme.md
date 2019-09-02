Jme-Calendar
===

A Calendar for jMonkeyEngine.
The calendar has a timeframe from 1700's to 2300's. You can set the time, date and speed multiplier.

```java

// create and attach the calendar
GameCalendarState calendarState = new GameCalendarState(2019, 9, 3, 0, 0, 1200);
stateManager.attach(calendarState);

// optionally add the GUI state to play around with the settings.
stateManager.attach(new CalendarGuiState());

```

The calendar also provides positions for the Sun and Moon in the form of a direction. This allows you to set the direction of
a DirectionalLight, and optionally set the location by multiplying the direction.

Please see the main class for an example including a sun, moon and GUI.
