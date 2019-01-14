package com.github.fo2rist.mclaren.ui.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/** Info about the driver. */
public class Driver implements Serializable {

    /** Single property with name. */
    public interface Property {
        String getDisplayName();
    };

    /**
     * Properties that must be declared for all drivers.
     */
    public enum MandatoryProperty implements Property {
        NAME("Name"),
        DATE_OF_BIRTH("Date of Birth"),
        NATIONALITY("Nationality"),
        TWITTER("Twitter");

        private String propertyName_;

        MandatoryProperty(String name) {
            propertyName_ = name;
        }

        public String getDisplayName() {
            return propertyName_;
        }
    }

    /**
     * Optional properties that could be skipped for some drivers.
     */
    public enum AdditionalProperty implements Property {
        TAG("Tag"),
        WORLD_CHAMPIONSHIPS("World Championships"),
        BEST_FINISH("Best Race Finish"),
        PODIUMS("Podiums"),
        POLE_POSITIONS("Pole Positions"),
        FASTEST_LAPS("Fastest Laps"),
        PLACE("Place"),
        POINTS("Points"),
        TEAM_LINK("Team Page"),
        HERITAGE_LINK("Heritage Page"),
        ;

        String propertyName_;

        AdditionalProperty(String name) {
            propertyName_ = name;
        }

        public String getDisplayName() {
            return propertyName_;
        }
    }

    private String id;
    private HashMap<Property, String> properties;

    public Driver(@NonNull String id, @NonNull Map<Property, String> properties) {
        //check that all mandatory properties present first
        for (Property key: MandatoryProperty.values()) {
            if (!properties.containsKey(key)) {
                throw new IllegalArgumentException(String.format("Required property %s doesn't exist.", key.getDisplayName()));
            }
        }

        this.id = id;
        this.properties = new HashMap<>(properties);
    }

    /** Id to identify driver model inside the app. */
    public String getId() {
        return id;
    }

    /**
     * Get any property of the driver.
     * One or {@link MandatoryProperty} and {@link AdditionalProperty}.
     * @return property value or 'null' if not exists
     */
    public String getProperty(Property property) {
        return properties.get(property);
    }

}
