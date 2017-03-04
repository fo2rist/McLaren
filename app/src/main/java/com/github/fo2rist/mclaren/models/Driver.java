package com.github.fo2rist.mclaren.models;

import java.util.LinkedHashMap;
import java.util.Map;

import static android.R.attr.propertyName;


/** Info about the driver. */
public class Driver {

    public interface Property {
        String getName();
    };

    enum MandatoryProperty implements Property {
        MANDATORY_TAG("Tag"),
        MANDATORY_NAME("Name"),
        MANDATORY_DATE_OF_BIRTH("Date of Birth"),
        MANDATORY_NATIONALITY("Nationality"),
        MANDATORY_TWITTER("Twitter");

        private String propertyName_;

        MandatoryProperty(String name) {
            propertyName_ = name;
        }

        public String getName() {
            return propertyName_;
        }
    }

    enum OptionalProperty implements Property {
        OPTIONAL_WORLD_CHAMPIONSHIPS("World Championships"),
        OPTIONAL_BEST_FINISH("Best Race Finish"),
        OPTIONAL_PODIUMS("Podiums"),
        OPTIONAL_POLE_POSITIONS("Pole Positions"),
        OPTIONAL_FASTEST_LAPS("Fastest Laps");

        String propertyName_;

        OptionalProperty(String name) {
            propertyName_ = name;
        }

        public String getName() {
            return propertyName_;
        }
    }
    
    private LinkedHashMap<String, String> properties;

    public Driver(Map<String, String> properties) {
        for (Property key: MandatoryProperty.values()) {
            String value = properties.get(key.getName());

            if (value != null) {
                properties.put(key.getName(), value);
            } else {
                throw new IllegalArgumentException(String.format("Required property %s doesn't exist.", key.getName()));
            }
        }

        for (Property key: OptionalProperty.values()) {
            String value = properties.get(key.getName());

            if (value != null) {
                properties.put(key.getName(), value);
            }
        }

    }

    /**
     * Get any property of the driver.
     * One or {@link MandatoryProperty} and {@link OptionalProperty}.
     * @return property value or 'null' if not exists
     */
    public String getProperty(Property property) {
        return properties.get(propertyName);
    }

}
