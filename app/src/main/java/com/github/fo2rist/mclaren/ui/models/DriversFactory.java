package com.github.fo2rist.mclaren.ui.models;

import android.support.annotation.NonNull;

import java.util.HashMap;

public class DriversFactory {
    public enum DriverId {
        ALONSO,
        BUTTON,
        CAMARA,
        DEVRIES,
        MATSUSHITA,
        NORRIS,
        SAINZ,
        TURVEY,
        VANBUREN,
        VANDOORNE,
    }

    public static Driver getDriverModel(@NonNull DriverId driver) {
        switch (driver) {
            case ALONSO:
                return getAlonsoModel();
            case BUTTON:
                return getButtonModel();
            case CAMARA:
                return getCamaraModel();
            case DEVRIES:
                return getDeVriesModel();
            case MATSUSHITA:
                return getMatsushitaModel();
            case NORRIS:
                return getNorrisModel();
            case SAINZ:
                return getSainzModel();
            case TURVEY:
                return getTurveyModel();
            case VANBUREN:
                return getVanburenModel();
            case VANDOORNE:
                return getVandoorneModel();
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


    private static Driver getButtonModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Jenson Button");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "19.01.1980");
        map.put(Driver.MandatoryProperty.NATIONALITY, "British");
        map.put(Driver.MandatoryProperty.TWITTER, "@JensonButton");

        map.put(Driver.AdditionalProperty.TEAM_LINK, "http://www.mclaren.com/formula1/team/jenson-button/");
        map.put(Driver.AdditionalProperty.TAG, "#JB");
        map.put(Driver.AdditionalProperty.WORLD_CHAMPIONSHIPS, "1");
        map.put(Driver.AdditionalProperty.BEST_FINISH, "1st x 15");
        map.put(Driver.AdditionalProperty.POLE_POSITIONS, "8");
        map.put(Driver.AdditionalProperty.FASTEST_LAPS, "8");

        return new Driver("button", map);
    }

    private static Driver getCamaraModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Sergio Sette Camara");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "23.05.1998");
        map.put(Driver.MandatoryProperty.NATIONALITY, "Brazilian");
        map.put(Driver.MandatoryProperty.TWITTER, "@sergiosettecama");

        map.put(Driver.AdditionalProperty.TEAM_LINK, "https://www.mclaren.com/formula1/team/sergio-sette-camara/");

        return new Driver("camara", map);
    }

    private static Driver getDeVriesModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Nyck De Vries");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "06.02.1995");
        map.put(Driver.MandatoryProperty.NATIONALITY, "Dutch");
        map.put(Driver.MandatoryProperty.TWITTER, "@nyckdevries");

        map.put(Driver.AdditionalProperty.TEAM_LINK, "https://www.mclaren.com/formula1/team/nyck-de-vries/");

        return new Driver("devries", map);
    }

    private static Driver getMatsushitaModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Nobuharu Matsushita");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "13.10.1993");
        map.put(Driver.MandatoryProperty.NATIONALITY, "Japanese");
        map.put(Driver.MandatoryProperty.TWITTER, "@Nobu_Mat13");

        return new Driver("matsushita", map);
    }

    private static Driver getNorrisModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Lando Norris");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "13.11.1999");
        map.put(Driver.MandatoryProperty.NATIONALITY, "British");
        map.put(Driver.MandatoryProperty.TWITTER, "@LandoNorris");

        map.put(Driver.AdditionalProperty.TEAM_LINK, "https://www.mclaren.com/formula1/team/lando-norris/");
        map.put(Driver.AdditionalProperty.TAG, "#LN4");
        map.put(Driver.AdditionalProperty.POLE_POSITIONS, "0");
        map.put(Driver.AdditionalProperty.FASTEST_LAPS, "0");

        return new Driver("norris", map);
    }

    private static Driver getSainzModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Carlos Sainz");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "01.09.1994");
        map.put(Driver.MandatoryProperty.NATIONALITY, "Spanish");
        map.put(Driver.MandatoryProperty.TWITTER, "@Carlossainz55");

        map.put(Driver.AdditionalProperty.TEAM_LINK, "https://www.mclaren.com/formula1/team/carlos-sainz/");
        map.put(Driver.AdditionalProperty.TAG, "#CS55");

        map.put(Driver.AdditionalProperty.BEST_FINISH, "4th");
        map.put(Driver.AdditionalProperty.POLE_POSITIONS, "0");
        map.put(Driver.AdditionalProperty.FASTEST_LAPS, "0");

        return new Driver("sainz", map);
    }

    private static Driver getTurveyModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Oliver Turvey");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "01.03.1987");
        map.put(Driver.MandatoryProperty.NATIONALITY, "British");
        map.put(Driver.MandatoryProperty.TWITTER, "@OliverTurvey");

        map.put(Driver.AdditionalProperty.TEAM_LINK, "https://www.mclaren.com/formula1/team/oliver-turvey/");

        return new Driver("turvey", map);
    }

    private static Driver getVanburenModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Rudy van Buren");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "04.04.1992");
        map.put(Driver.MandatoryProperty.NATIONALITY, "Dutch");
        map.put(Driver.MandatoryProperty.TWITTER, "@RvBuren");

        map.put(Driver.AdditionalProperty.TEAM_LINK, "https://www.mclaren.com/formula1/team/rudy-van-buren/");

        return new Driver("vanburen", map);
    }

    private static Driver getVandoorneModel() {
        HashMap<Driver.Property, String> map = new HashMap<>();
        map.put(Driver.MandatoryProperty.NAME, "Stoffel Vandoorne");
        map.put(Driver.MandatoryProperty.DATE_OF_BIRTH, "26.03.1992");
        map.put(Driver.MandatoryProperty.NATIONALITY, "Belgian");
        map.put(Driver.MandatoryProperty.TWITTER, "@svandoorne");

        map.put(Driver.AdditionalProperty.TEAM_LINK, "http://www.mclaren.com/formula1/team/stoffel-vandoorne/");
        map.put(Driver.AdditionalProperty.TAG, "#SV2");
        map.put(Driver.AdditionalProperty.WORLD_CHAMPIONSHIPS, "0");
        map.put(Driver.AdditionalProperty.BEST_FINISH, "7th x 2");
        map.put(Driver.AdditionalProperty.PODIUMS, "0");
        map.put(Driver.AdditionalProperty.POLE_POSITIONS, "0");
        map.put(Driver.AdditionalProperty.FASTEST_LAPS, "0");

        return new Driver("vandoorne", map);
    }
}
