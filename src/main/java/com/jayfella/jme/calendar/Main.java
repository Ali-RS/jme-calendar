package com.jayfella.jme.calendar;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.BillboardControl;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;

public class Main extends SimpleApplication {

    public static void main(String... args) {

        Main app = new Main();

        AppSettings settings = new AppSettings(true);
        settings.setTitle("Calendar Example");
        settings.setFrameRate(120);
        settings.setResolution(1280, 720);

        app.setSettings(settings);
        app.setShowSettings(false);
        app.setPauseOnLostFocus(false);

        app.start();
    }

    private GameCalendarState calendarState;
    private final ColorRGBA dayColor = new ColorRGBA(0.5f, 0.6f, 0.7f, 1.0f);
    private final ColorRGBA nightColor = new ColorRGBA(0.13f, 0.2f, 0.32f, 1.0f);
    private final ColorRGBA skyColor = new ColorRGBA();

    private int sunSize = 32;
    private Geometry sun;
    private Geometry moon;

    @Override
    public void simpleInitApp() {

        inputManager.setCursorVisible(true);
        flyCam.setDragToRotate(true);

        // create the calendar
        calendarState = new GameCalendarState(2019, 9, 3, 0, 0, 1200);
        stateManager.attach(calendarState);

        // attach the calendar gui state so we can toy around with it.
        stateManager.attach(new CalendarGuiState());

        // create an arbitrary scene. Here we create a basic sun and moon, but you can create your own type.
        sun = new Geometry("Sun", new Sphere(sunSize, sunSize, sunSize));
        sun.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"));
        sun.getMaterial().setColor("Color", ColorRGBA.Yellow);
        rootNode.attachChild(sun);

        moon = new Geometry("Moon", new Quad(sunSize, sunSize));
        moon.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"));
        moon.getMaterial().setTexture("ColorMap", assetManager.loadTexture("Textures/full-moon.png"));
        moon.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        moon.addControl(new BillboardControl());
        rootNode.attachChild(moon);

        Quad quad = new Quad(500, 500);
        Geometry land = new Geometry("Floor", quad);
        land.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"));
        land.getMaterial().setColor("Color", ColorRGBA.Green.mult(0.4f));
        land.rotate(-FastMath.HALF_PI, 0, 0);
        land.setLocalTranslation(-250, 0, 250);
        rootNode.attachChild(land);

        cam.setLocation(new Vector3f(0, 10, 0));
        cam.lookAt(new Vector3f(-250, 0, 0), Vector3f.UNIT_Y);

        // init lemur
        GuiGlobals.initialize(this);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
    }


    @Override
    public void simpleUpdate(float tpf) {

        // our position provider gives us the directions of the sun and moon.
        // we're just going to multiply the directions so they're far away.
        sun.setLocalTranslation(calendarState.getPositionProvider().getSunDirection().mult(700));
        moon.setLocalTranslation(calendarState.getPositionProvider().getMoonDirection().mult(700));

        // interpolate the background color to imitate day/night.
        float dayRads = calendarState.getGameCalendar().getFractionOfDay() * FastMath.TWO_PI;
        float lerp = (FastMath.cos(dayRads) + 1) * 0.5f;
        skyColor.interpolateLocal(dayColor, nightColor, lerp);
        viewPort.setBackgroundColor(skyColor);



    }

}
