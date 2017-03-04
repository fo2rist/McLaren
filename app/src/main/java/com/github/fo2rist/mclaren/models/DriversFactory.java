package com.github.fo2rist.mclaren.models;

import com.github.fo2rist.mclaren.models.Driver.AdditionalProperty;
import com.github.fo2rist.mclaren.models.Driver.MandatoryProperty;

import java.util.HashMap;

public class DriversFactory {
    public enum DriverId {
        Alonso,
        Vandoorne,
        Button,
        Turvey,
        Matsushita
    }

    public static Driver getDriverModel(DriverId driver) {
        switch (driver) {
            case Alonso:
                return getAlonsoModel();
            case Vandoorne:
                return getVandoorneModel();
            case Button:
                return getButtonModel();
            case Turvey:
                return getTurveyModel();
            case Matsushita:
                return getMatsushitaModel();
            default:
                throw new IllegalArgumentException("Usupported driver: " + driver);
        }
    }

    private static Driver getAlonsoModel() {
        HashMap<String, String> map = new HashMap<>();
        map.put(MandatoryProperty.NAME.getName(), "Fernando Alonso");
        map.put(MandatoryProperty.DATE_OF_BIRTH.getName(), "29.07.1981");
        map.put(MandatoryProperty.NATIONALITY.getName(), "Spanish");
        map.put(MandatoryProperty.TWITTER.getName(), "@alo_official");

        map.put(AdditionalProperty.TAG.getName(), "#FA14");
        map.put(AdditionalProperty.WORLD_CHAMPIONSHIPS.getName(), "2");
        map.put(AdditionalProperty.BEST_FINISH.getName(), "1st x 32");
        map.put(AdditionalProperty.PODIUMS.getName(), "97");
        map.put(AdditionalProperty.POLE_POSITIONS.getName(), "22");
        map.put(AdditionalProperty.FASTEST_LAPS.getName(), "21");

        map.put(AdditionalProperty.PLACE.getName(), "10");
        map.put(AdditionalProperty.POINTS.getName(), "54");
        return new Driver("alonso", map);
    }

    private static Driver getVandoorneModel() {
        HashMap<String, String> map = new HashMap<>();
        map.put(MandatoryProperty.NAME.getName(), "Stoffel Vandoorne");
        map.put(MandatoryProperty.DATE_OF_BIRTH.getName(), "26.03.1992");
        map.put(MandatoryProperty.NATIONALITY.getName(), "Belgian");
        map.put(MandatoryProperty.TWITTER.getName(), "@svandoorne");

        return new Driver("vandoorne", map);
    }

    private static Driver getButtonModel() {
        HashMap<String, String> map = new HashMap<>();
        map.put(MandatoryProperty.NAME.getName(), "Jenson Button");
        map.put(MandatoryProperty.DATE_OF_BIRTH.getName(), "19.01.1980");
        map.put(MandatoryProperty.NATIONALITY.getName(), "British");
        map.put(MandatoryProperty.TWITTER.getName(), "@JensonButton");

        return new Driver("button", map);
    }

    private static Driver getTurveyModel() {
        return null;
    }

    private static Driver getMatsushitaModel() {
        return null;
    }
}
