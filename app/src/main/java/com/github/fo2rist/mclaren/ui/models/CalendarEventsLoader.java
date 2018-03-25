package com.github.fo2rist.mclaren.ui.models;

import android.content.Context;
import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.models.Circuit;
import com.github.fo2rist.mclaren.models.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CalendarEventsLoader {

    TypeToken<ArrayList<Event>> grandPrixListType = new TypeToken<ArrayList<Event>>(){};
    TypeToken<ArrayList<Circuit>> circuitListType = new TypeToken<ArrayList<Circuit>>(){};

    Context context;
    int year;

    public CalendarEventsLoader(Context context, int year) {
        this.context = context;
        this.year = year;
    }

    public List<CalendarEvent> getCalendar() {
        List<Event> events = readJsonFromAssets(context, "calendar_" + year + ".json", grandPrixListType);
        List<Circuit> circuits = readJsonFromAssets(context, "circuits.json", circuitListType);

        List<CalendarEvent> calendarEvents = new ArrayList<>();
        if (events == null || circuits == null) {
            return calendarEvents;
        }

        for(Event gp: events) {
            Circuit circuit = findCircuit(circuits, gp);
            calendarEvents.add(new CalendarEvent(circuit, gp));
        }
        return calendarEvents;
    }

    private Circuit findCircuit(List<Circuit> circuits, Event gp) {
        for(Circuit circuit: circuits) {
            if (circuit.track.equals(gp.track)) {
                return circuit;
            }
        }
        return null;
    }

    @Nullable
    private static <T> T readJsonFromAssets(Context context, String fileName, TypeToken<T> objectTypeToken) {
        try {
            InputStream stream = context.getAssets().open(fileName);
            InputStreamReader reader = new InputStreamReader(stream);
            T result = new Gson().fromJson(reader, objectTypeToken.getType());
            stream.close();
            return result;
        } catch (IOException e) {
            //that should never happen in prod, or at least it mean apk is broken
            return null;
        }
    }
}
