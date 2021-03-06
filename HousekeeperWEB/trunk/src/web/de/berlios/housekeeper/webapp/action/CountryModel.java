package de.berlios.housekeeper.webapp.action;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.berlios.housekeeper.model.LabelValue;

public class CountryModel {
    private Map availableCountries;

    /**
     * Build a List of LabelValues for all the available countries. Uses
     * the two letter uppercase ISO name of the country as the value and the
     * localized country name as the label.
     *
     * @param locale The Locale used to localize the country names.
     *
     * @return List of LabelValues for all available countries.
     */
    public Map getCountries(Locale locale) {
        if (availableCountries == null) {
            final String EMPTY = "";
            final Locale[] available = Locale.getAvailableLocales();
    
            List countries = new ArrayList();
            countries.add(new LabelValue("",""));
    
            for (int i = 0; i < available.length; i++) {
                final String iso = available[i].getCountry();
                final String name = available[i].getDisplayCountry(locale);
    
                if (!EMPTY.equals(iso) && !EMPTY.equals(name)) {
                    LabelValue country = new LabelValue(name, iso);
    
                    if (!countries.contains(country)) {
                        countries.add(new LabelValue(name, iso));
                    }
                }
            }
    
            Collections.sort(countries, new LabelValueComparator(locale));
            
            Map options = new LinkedHashMap();
            // loop through and convert list to a JSF-Friendly Map for a <select>
            for (Iterator it = countries.iterator(); it.hasNext();) {
                LabelValue option = (LabelValue) it.next();
                if (!options.containsValue(option.getValue())) {
                    options.put(option.getLabel(), option.getValue());
                }
            }
            this.availableCountries = options;
        }

        return availableCountries;
    }

    /**
     * Class to compare LabelValues using their labels with
     * locale-sensitive behaviour.
     */
    public class LabelValueComparator implements Comparator {
        private Comparator c;

        /**
         * Creates a new LabelValueComparator object.
         *
         * @param locale The Locale used for localized String comparison.
         */
        public LabelValueComparator(Locale locale) {
            c = Collator.getInstance(locale);
        }

        /**
         * Compares the localized labels of two LabelValues.
         *
         * @param o1 The first LabelValue to compare.
         * @param o2 The second LabelValue to compare.
         *
         * @return The value returned by comparing the localized labels.
         */
        public final int compare(Object o1, Object o2) {
            LabelValue lhs = (LabelValue) o1;
            LabelValue rhs = (LabelValue) o2;

            return c.compare(lhs.getLabel(), rhs.getLabel());
        }
    }
}
