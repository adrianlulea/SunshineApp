package app.sunshine.android.example.com.sunshineudacity;

import android.text.format.Time;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by constantin-adrianlulea on 3/7/15.
 */
public class WeatherDataParser {
    private static final String LOG_TAG = WeatherDataParser.class.getSimpleName();

    private static final String DAY_LIST_TAG = "list";
    private String weatherData;

    public WeatherDataParser(String data) {
        this.weatherData = data;
    }

    private static JSONArray getDaysArray(JSONObject jsonData) throws JSONException {
        JSONArray days = jsonData.getJSONArray(DAY_LIST_TAG);

        return days;
    }

    private static JSONObject getDayInformation(JSONArray days, int dayIndex) throws JSONException {
        JSONObject dayInformation = days.getJSONObject(dayIndex);

        return dayInformation;
    }

    private static String getDayInformation(JSONObject day) throws JSONException {
        WeatherDayInformation dayInformation = new WeatherDayInformation(day);

        return dayInformation.getInformation();
    }

    public static String[] getDaysInformation(JSONObject jsonData) throws JSONException {
        Log.d(LOG_TAG, "entering getDaysInformation");

        JSONArray days = getDaysArray(jsonData);

        String[] daysInformation = new String[days.length()];

        Time time = new Time();
        time.setToNow();

        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), time.gmtoff);

        time = new Time();

        for (int i = 0; i < days.length(); i++) {
            String day;
            String information;

            long dateTime = time.setJulianDay(julianStartDay + i);
            day = getReadableDateString(dateTime);
            information = getDayInformation(days.getJSONObject(i));

            daysInformation[i] = day + " - " + information;
        }

        return daysInformation;
    }

    private static String getReadableDateString(long time) {
        SimpleDateFormat shortDateString = new SimpleDateFormat("EEE MMM dd");

        return shortDateString.format(time);
    }

    private static double getMaxTemperatureForDay(String data, int day) throws JSONException {
        JSONObject weather = new JSONObject(data);
        JSONArray days = getDaysArray(weather);
        JSONObject dayInformation = getDayInformation(days, day);
        JSONObject temperatureInformation = dayInformation.getJSONObject("temp");

        return temperatureInformation.getDouble("max");
    }
}
