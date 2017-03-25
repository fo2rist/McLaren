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
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(MandatoryProperty.NAME, "Fernando Alonso");
        map.put(MandatoryProperty.DATE_OF_BIRTH, "29.07.1981");
        map.put(MandatoryProperty.NATIONALITY, "Spanish");
        map.put(MandatoryProperty.TWITTER, "@alo_official");

        map.put(AdditionalProperty.TAG, "#FA14");
        map.put(AdditionalProperty.WORLD_CHAMPIONSHIPS, "2");
        map.put(AdditionalProperty.BEST_FINISH, "1st x 32");
        map.put(AdditionalProperty.PODIUMS, "97");
        map.put(AdditionalProperty.POLE_POSITIONS, "22");
        map.put(AdditionalProperty.FASTEST_LAPS, "21");

        map.put(AdditionalProperty.PLACE, null);
        map.put(AdditionalProperty.POINTS, null);
        map.put(AdditionalProperty.TEAM_LINK, "http://www.mclaren.com/formula1/team/fernando-alonso/");
        map.put(AdditionalProperty.HERITAGE_LINK, "http://www.mclaren.com/formula1/heritage/driver/fernando-alonso/");
        return new Driver("alonso", map);
    }

    private static Driver getVandoorneModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(MandatoryProperty.NAME, "Stoffel Vandoorne");
        map.put(MandatoryProperty.DATE_OF_BIRTH, "26.03.1992");
        map.put(MandatoryProperty.NATIONALITY, "Belgian");
        map.put(MandatoryProperty.TWITTER, "@svandoorne");

        map.put(AdditionalProperty.TAG, "#SV2");
        map.put(AdditionalProperty.WORLD_CHAMPIONSHIPS, "0");
        map.put(AdditionalProperty.BEST_FINISH, "10th x 1");
        map.put(AdditionalProperty.PODIUMS, "0");
        map.put(AdditionalProperty.POLE_POSITIONS, "0");
        map.put(AdditionalProperty.FASTEST_LAPS, "0");
        map.put(AdditionalProperty.TEAM_LINK, "http://www.mclaren.com/formula1/team/stoffel-vandoorne/");

        return new Driver("vandoorne", map);
    }

    private static Driver getButtonModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(MandatoryProperty.NAME, "Jenson Button");
        map.put(MandatoryProperty.DATE_OF_BIRTH, "19.01.1980");
        map.put(MandatoryProperty.NATIONALITY, "British");
        map.put(MandatoryProperty.TWITTER, "@JensonButton");

        map.put(AdditionalProperty.TAG, "#JB");
        map.put(AdditionalProperty.WORLD_CHAMPIONSHIPS, "1");
        map.put(AdditionalProperty.BEST_FINISH, "1st x 15");
        map.put(AdditionalProperty.POLE_POSITIONS, "8");
        map.put(AdditionalProperty.FASTEST_LAPS, "8");
        map.put(AdditionalProperty.TEAM_LINK, "http://www.mclaren.com/formula1/team/jenson-button/");

        return new Driver("button", map);
    }

    private static Driver getTurveyModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(MandatoryProperty.NAME, "Oliver Turvey");
        map.put(MandatoryProperty.DATE_OF_BIRTH, "01.03.1987");
        map.put(MandatoryProperty.NATIONALITY, "British");
        map.put(MandatoryProperty.TWITTER, "@OliverTurvey");

        return new Driver("turvey", map);
    }

    private static Driver getMatsushitaModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(MandatoryProperty.NAME, "Nobuharu Matsushita");
        map.put(MandatoryProperty.DATE_OF_BIRTH, "13.10.1993");
        map.put(MandatoryProperty.NATIONALITY, "Japanese");
        map.put(MandatoryProperty.TWITTER, "@Nobu_Mat13");

        return new Driver("matsushita", map);
    }
}
