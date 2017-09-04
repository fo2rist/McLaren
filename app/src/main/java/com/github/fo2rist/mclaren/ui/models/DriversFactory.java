package com.github.fo2rist.mclaren.ui.models;

import java.util.HashMap;

public class DriversFactory {
    public enum DriverId {
        ALONSO,
        VANDOORNE,
        BUTTON,
        TURVEY,
        MATSUSHITA,
        DEVRIES,
        NORRIS,
    }

    public static Driver getDriverModel(DriverId driver) {
        switch (driver) {
            case ALONSO:
                return getAlonsoModel();
            case VANDOORNE:
                return getVandoorneModel();
            case BUTTON:
                return getButtonModel();
            case TURVEY:
                return getTurveyModel();
            case MATSUSHITA:
                return getMatsushitaModel();
            case DEVRIES:
                return getDeVriesModel();
            case NORRIS:
                return getNorrisModel();
            default:
                throw new IllegalArgumentException("Usupported driver: " + driver);
        }
    }

    private static Driver getAlonsoModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Fernando Alonso");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "29.07.1981");
        map.put(Driver.MandatoryProperty.NATIONALITY, "Spanish");
        map.put(Driver.MandatoryProperty.TWITTER, "@alo_official");

        map.put(Driver.AdditionalProperty.TAG, "#FA14");
        map.put(Driver.AdditionalProperty.WORLD_CHAMPIONSHIPS, "2");
        map.put(Driver.AdditionalProperty.BEST_FINISH, "1st x 32");
        map.put(Driver.AdditionalProperty.PODIUMS, "97");
        map.put(Driver.AdditionalProperty.POLE_POSITIONS, "22");
        map.put(Driver.AdditionalProperty.FASTEST_LAPS, "23");

        map.put(Driver.AdditionalProperty.PLACE, null);
        map.put(Driver.AdditionalProperty.POINTS, null);
        map.put(Driver.AdditionalProperty.TEAM_LINK, "http://www.mclaren.com/formula1/team/fernando-alonso/");
        map.put(Driver.AdditionalProperty.HERITAGE_LINK, "http://www.mclaren.com/formula1/heritage/driver/fernando-alonso/");
        return new Driver("alonso", map);
    }

    private static Driver getVandoorneModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Stoffel Vandoorne");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "26.03.1992");
        map.put(Driver.MandatoryProperty.NATIONALITY, "Belgian");
        map.put(Driver.MandatoryProperty.TWITTER, "@svandoorne");

        map.put(Driver.AdditionalProperty.TAG, "#SV2");
        map.put(Driver.AdditionalProperty.WORLD_CHAMPIONSHIPS, "0");
        map.put(Driver.AdditionalProperty.BEST_FINISH, "10th x 2");
        map.put(Driver.AdditionalProperty.PODIUMS, "0");
        map.put(Driver.AdditionalProperty.POLE_POSITIONS, "0");
        map.put(Driver.AdditionalProperty.FASTEST_LAPS, "0");
        map.put(Driver.AdditionalProperty.TEAM_LINK, "http://www.mclaren.com/formula1/team/stoffel-vandoorne/");

        return new Driver("vandoorne", map);
    }

    private static Driver getButtonModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Jenson Button");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "19.01.1980");
        map.put(Driver.MandatoryProperty.NATIONALITY, "British");
        map.put(Driver.MandatoryProperty.TWITTER, "@JensonButton");

        map.put(Driver.AdditionalProperty.TAG, "#JB");
        map.put(Driver.AdditionalProperty.WORLD_CHAMPIONSHIPS, "1");
        map.put(Driver.AdditionalProperty.BEST_FINISH, "1st x 15");
        map.put(Driver.AdditionalProperty.POLE_POSITIONS, "8");
        map.put(Driver.AdditionalProperty.FASTEST_LAPS, "8");
        map.put(Driver.AdditionalProperty.TEAM_LINK, "http://www.mclaren.com/formula1/team/jenson-button/");

        return new Driver("button", map);
    }

    private static Driver getTurveyModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Oliver Turvey");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "01.03.1987");
        map.put(Driver.MandatoryProperty.NATIONALITY, "British");
        map.put(Driver.MandatoryProperty.TWITTER, "@OliverTurvey");

        return new Driver("turvey", map);
    }

    private static Driver getMatsushitaModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Nobuharu Matsushita");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "13.10.1993");
        map.put(Driver.MandatoryProperty.NATIONALITY, "Japanese");
        map.put(Driver.MandatoryProperty.TWITTER, "@Nobu_Mat13");

        return new Driver("matsushita", map);
    }

    private static Driver getDeVriesModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Nyck De Vries");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "06.02.1995");
        map.put(Driver.MandatoryProperty.NATIONALITY, "Dutch");
        map.put(Driver.MandatoryProperty.TWITTER, "@nyckdevries");

        return new Driver("devries", map);
    }

    private static Driver getNorrisModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Lando Norris");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "13.11.1999");
        map.put(Driver.MandatoryProperty.NATIONALITY, "British");
        map.put(Driver.MandatoryProperty.TWITTER, "@LandoNorris");

        return new Driver("norris", map);
    }
}
