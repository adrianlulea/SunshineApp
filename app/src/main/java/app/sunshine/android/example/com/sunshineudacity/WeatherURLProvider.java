package app.sunshine.android.example.com.sunshineudacity;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by constantin-adrianlulea on 3/7/15.
 */
public class WeatherURLProvider {
    private static final String LOG_TAG = WeatherURLProvider.class.getSimpleName();

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    private static final String QUERY_PARAM = "q";
    private static final String MODE_PARAM = "mode";
    private static final String UNITS_PARAM = "units";
    private static final String DAYS_PARAM = "cnt";

    /**
     * Get format as a string for the URL from WeatherFormat enumeration
     *
     * @param format Weather format enumeration
     * @return String representation of format if valid
     * @throws java.lang.NoSuchFieldException
     */
    private static String formatToString(WeatherFormat format) throws NoSuchFieldException {
        switch (format) {
            case JSON: {
                return "json";
            }
            case XML: {
                return "xml";
            }
            default: {
                throw new NoSuchFieldException("Unrecognized Weather Format");
            }
        }
    }

    /**
     * Get unit system as a string for the URL from WeatherUnitSystem enumeration
     *
     * @param unitSystem Weather unit system enumeration
     * @return String representation for the format if valid
     * @throws java.lang.NoSuchFieldException
     */
    private static String unitsToString(WeatherUnitSystem unitSystem) throws NoSuchFieldException {
        switch (unitSystem) {
            case Imperial: {
                return "imperial";
            }
            case Metric: {
                return "metric";
            }
            default: {
                throw new NoSuchFieldException("Unrecognized Weather Unit System");
            }
        }
    }

    /**
     * Get URL for OpenWeatherMap.ORG for a number of days forecast by postalcode
     *
     * @param format   Data format (Json, Xml)
     * @param units    Temperature units (C, F)
     * @param days     Number of days
     * @param postcode Location postcode
     * @return Built URL
     * @throws NoSuchFieldException
     */
    public static URL getURL(WeatherFormat format,
                             WeatherUnitSystem units,
                             int days,
                             String postcode) throws NoSuchFieldException {
        URL url = null;

        String dataFormat = WeatherURLProvider.formatToString(format);
        String unitsSystem = WeatherURLProvider.unitsToString(units);
        String numberOfDays = Integer.toString(days);

        Uri uri = Uri.parse(WeatherURLProvider.BASE_URL).buildUpon()
                .appendQueryParameter(WeatherURLProvider.QUERY_PARAM, postcode)
                .appendQueryParameter(WeatherURLProvider.MODE_PARAM, dataFormat)
                .appendQueryParameter(WeatherURLProvider.UNITS_PARAM, unitsSystem)
                .appendQueryParameter(WeatherURLProvider.DAYS_PARAM, numberOfDays)
                .build();

        Log.d(WeatherURLProvider.LOG_TAG, "Built URI: " + uri.toString());

        try {
            url = new URL(uri.toString());
            Log.d(WeatherURLProvider.LOG_TAG, "URL successful.");
        } catch (MalformedURLException e) {
            url = null;
            Log.e(WeatherURLProvider.LOG_TAG, "Error building URL: " + e);
        }

        return url;
    }
}
