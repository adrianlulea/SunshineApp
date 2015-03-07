package app.sunshine.android.example.com.sunshineudacity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by constantin-adrianlulea on 3/7/15.
 */
public class WeatherDayInformation {
    private static final String LOG_TAG = WeatherDayInformation.class.getSimpleName();

    private final String DT_TAG = "dt";
    private final String TEMP_TAG = "temp";
    private final String TEMP_DAY_TAG = "day";
    private final String TEMP_MIN_TAG = "min";
    private final String TEMP_MAX_TAG = "max";
    private final String TEMP_NIGHT_TAG = "night";
    private final String TEMP_EVE_TAG = "eve";
    private final String TEMP_MORN_TAG = "morn";
    private final String PRESSURE_TAG = "pressure";
    private final String HUMIDITY_TAG = "humidity";
    private final String WEATHER_TAG = "weather";
    private final String WEATHER_ID_TAG = "id";
    private final String WEATHER_MAIN_TAG = "main";
    private final String WEATHER_DESCRIPTION_TAG = "description";
    private final String WEATHER_ICON_TAG = "icon";
    private final String SPEED_TAG = "speed";
    private final String DEG_TAG = "deg";
    private final String CLOUDS_TAG = "clouds";
    private final String RAIN_TAG = "rain";

    private JSONObject mDayInformation;

    private String mDt;
    private String mTempDay, mTempMin, mTempMax, mTempNight, mTempEve, mTempMorn;
    private String mPressure;
    private String mHumidity;
    private String mWeatherId, mWeatherMain, mWeatherDescription, mWeatherIcon;
    private String mSpeed;
    private String mDeg;
    private String mClouds;
    private String mRain;

    public WeatherDayInformation(JSONObject dayInformation) throws JSONException {
        this.mDayInformation = dayInformation;
        this.parseWeatherDayInformation();
    }

    private void parseDt() throws JSONException {
        this.mDt = parseSimpleString(DT_TAG);
    }

    private void parseTemp() throws JSONException {
        JSONObject tempInformation = this.mDayInformation.getJSONObject(TEMP_TAG);

        this.mTempDay = parseSimpleString(tempInformation, TEMP_DAY_TAG);
        this.mTempMin = parseSimpleString(tempInformation, TEMP_MIN_TAG);
        this.mTempMax = parseSimpleString(tempInformation, TEMP_MAX_TAG);
        this.mTempNight = parseSimpleString(tempInformation, TEMP_NIGHT_TAG);
        this.mTempEve = parseSimpleString(tempInformation, TEMP_EVE_TAG);
        this.mTempMorn = parseSimpleString(tempInformation, TEMP_MORN_TAG);
    }

    private String parseSimpleString(String tag) throws JSONException {
        return parseSimpleString(this.mDayInformation, tag);
    }

    private String parseSimpleString(JSONObject jsonObject, String tag) throws JSONException {
        if (jsonObject.has(tag)) {
            return jsonObject.getString(tag);
        }

        return "";
    }

    private void parsePressure() throws JSONException {
        this.mPressure = parseSimpleString(PRESSURE_TAG);
    }

    private void parseHumidity() throws JSONException {
        this.mHumidity = parseSimpleString(HUMIDITY_TAG);
    }

    private void parseWeather() throws JSONException {
        JSONArray weatherInformation = this.mDayInformation.getJSONArray(WEATHER_TAG);
        JSONObject firstWeatherInformationObject = weatherInformation.optJSONObject(0);

        this.mWeatherId = parseSimpleString(firstWeatherInformationObject, WEATHER_ID_TAG);
        this.mWeatherMain = parseSimpleString(firstWeatherInformationObject, WEATHER_MAIN_TAG);
        this.mWeatherDescription = parseSimpleString(firstWeatherInformationObject, WEATHER_DESCRIPTION_TAG);
        this.mWeatherIcon = parseSimpleString(firstWeatherInformationObject, WEATHER_ICON_TAG);
    }

    private void parseSpeed() throws JSONException {
        this.mSpeed = parseSimpleString(SPEED_TAG);
    }

    private void parseDeg() throws JSONException {
        this.mDeg = parseSimpleString(DEG_TAG);
    }

    private void parseClouds() throws JSONException {
        this.mClouds = parseSimpleString(CLOUDS_TAG);
    }

    private void parseRain() throws JSONException {
        this.mRain = parseSimpleString(RAIN_TAG);
    }

    private void parseWeatherDayInformation() throws JSONException {
        // Parse dt
        this.parseDt();

        // Parse temp
        this.parseTemp();

        this.parsePressure();
        this.parseHumidity();

        // Parse weather
        this.parseWeather();

        this.parseSpeed();
        this.parseDeg();
        this.parseClouds();
        this.parseRain();
    }

    public String getInformation() {
        String description = this.mWeatherDescription;
        String minMax = this.mTempMin + "/" + this.mTempMax;
        String information = description + " - " + minMax;

        return information;
    }

}
