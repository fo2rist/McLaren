package com.github.fo2rist.mclaren.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/** Info about the driver. */
public class Driver implements Serializable {

    public interface Property {
        String getDisplayName();
    };

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

    public enum AdditionalProperty implements Property {
        TAG("Tag"),
        WORLD_CHAMPIONSHIPS("World Championships"),
        BEST_FINISH("Best Race Finish"),
        PODIUMS("Podiums"),
        POLE_POSITIONS("Pole Positions"),
        FASTEST_LAPS("Fastest Laps"),
        PLACE("Place"),
        POINTS("Points")
        ;

        String propertyName_;

        AdditionalProperty(String name) {
            propertyName_ = name;
        }

        public String getDisplayName() {
            return propertyName_;
        }
    }

    private String id_;
    private HashMap<String, String> properties_;

    public Driver(@NonNull String id, @NonNull Map<String, String> properties) {
        //check that all mandatory properties present first
        for (Property key: MandatoryProperty.values()) {
            if (!properties.containsKey(key.getDisplayName())) {
                throw new IllegalArgumentException(String.format("Required property %s doesn't exist.", key.getDisplayName()));
            }
        }

        id_ = id;
        properties_ = new HashMap<>(properties);
    }

    /** Id to identify driver model inside the app. */
    public String getId() {
        return id_;
    }

    /**
     * Get any property of the driver.
     * One or {@link MandatoryProperty} and {@link AdditionalProperty}.
     * @return property value or 'null' if not exists
     */
    public String getProperty(Property property) {
        return properties_.get(property.getDisplayName());
    }

}
