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
        map.put(MandatoryProperty.NAME.getDisplayName(), "Fernando Alonso");
        map.put(MandatoryProperty.DATE_OF_BIRTH.getDisplayName(), "29.07.1981");
        map.put(MandatoryProperty.NATIONALITY.getDisplayName(), "Spanish");
        map.put(MandatoryProperty.TWITTER.getDisplayName(), "@alo_official");

        map.put(AdditionalProperty.TAG.getDisplayName(), "#FA14");
        map.put(AdditionalProperty.WORLD_CHAMPIONSHIPS.getDisplayName(), "2");
        map.put(AdditionalProperty.BEST_FINISH.getDisplayName(), "1st x 32");
        map.put(AdditionalProperty.PODIUMS.getDisplayName(), "97");
        map.put(AdditionalProperty.POLE_POSITIONS.getDisplayName(), "22");
        map.put(AdditionalProperty.FASTEST_LAPS.getDisplayName(), "21");

        map.put(AdditionalProperty.PLACE.getDisplayName(), "10");
        map.put(AdditionalProperty.POINTS.getDisplayName(), "54");
        return new Driver("alonso", map);
    }

    private static Driver getVandoorneModel() {
        HashMap<String, String> map = new HashMap<>();
        map.put(MandatoryProperty.NAME.getDisplayName(), "Stoffel Vandoorne");
        map.put(MandatoryProperty.DATE_OF_BIRTH.getDisplayName(), "26.03.1992");
        map.put(MandatoryProperty.NATIONALITY.getDisplayName(), "Belgian");
        map.put(MandatoryProperty.TWITTER.getDisplayName(), "@svandoorne");

        map.put(AdditionalProperty.TAG.getDisplayName(), "#SV2");
        map.put(AdditionalProperty.WORLD_CHAMPIONSHIPS.getDisplayName(), "0");
        map.put(AdditionalProperty.BEST_FINISH.getDisplayName(), "10th x 1");
        map.put(AdditionalProperty.PODIUMS.getDisplayName(), "0");
        map.put(AdditionalProperty.POLE_POSITIONS.getDisplayName(), "0");
        map.put(AdditionalProperty.FASTEST_LAPS.getDisplayName(), "0");

        return new Driver("vandoorne", map);
    }

    private static Driver getButtonModel() {
        HashMap<String, String> map = new HashMap<>();
        map.put(MandatoryProperty.NAME.getDisplayName(), "Jenson Button");
        map.put(MandatoryProperty.DATE_OF_BIRTH.getDisplayName(), "19.01.1980");
        map.put(MandatoryProperty.NATIONALITY.getDisplayName(), "British");
        map.put(MandatoryProperty.TWITTER.getDisplayName(), "@JensonButton");

        return new Driver("button", map);
    }

    private static Driver getTurveyModel() {
        HashMap<String, String> map = new HashMap<>();
        map.put(MandatoryProperty.NAME.getDisplayName(), "Oliver Turvey");
        map.put(MandatoryProperty.DATE_OF_BIRTH.getDisplayName(), "01.03.1987");
        map.put(MandatoryProperty.NATIONALITY.getDisplayName(), "British");
        map.put(MandatoryProperty.TWITTER.getDisplayName(), "@OliverTurvey");

        return new Driver("turvey", map);
    }

    private static Driver getMatsushitaModel() {
        HashMap<String, String> map = new HashMap<>();
        map.put(MandatoryProperty.NAME.getDisplayName(), "Nobuharu Matsushita");
        map.put(MandatoryProperty.DATE_OF_BIRTH.getDisplayName(), "13.10.1993");
        map.put(MandatoryProperty.NATIONALITY.getDisplayName(), "Japanese");
        map.put(MandatoryProperty.TWITTER.getDisplayName(), "@Nobu_Mat13");

        return new Driver("matsushita", map);
    }
}
