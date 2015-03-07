package app.sunshine.android.example.com.sunshineudacity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by constantin-adrianlulea on 3/7/15.
 */
public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {
    private static final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
    private static final String requestMethod = "GET";

    private ArrayAdapter<String> mWeatherAdapter;

    public FetchWeatherTask(ArrayAdapter<String> weatherAdapter) {
        this.mWeatherAdapter = weatherAdapter;
    }


    /**
     * Parse given JSON string and return an array of forecast information by day
     *
     * @param forecastJSONString JSON string to parse
     * @return
     */
    private String[] parseJSONString(String forecastJSONString) {
        String[] weatherInformationByDay = null;

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(forecastJSONString);
        } catch (JSONException e) {
            jsonObject = null;
            Log.d(LOG_TAG, "Could not get JSON object from " + forecastJSONString);
        }

        if (jsonObject != null) {
            try {
                weatherInformationByDay = WeatherDataParser.getDaysInformation(jsonObject);
            } catch (JSONException jsone) {
                weatherInformationByDay = new String[1];
                weatherInformationByDay[0] = "failed";
                Log.e(LOG_TAG, "Error occured while parsing " + jsone);
            }

            for (int i = 0; i < weatherInformationByDay.length; i++) {
                Log.d(LOG_TAG, "Days[" + i + "] = " + weatherInformationByDay[i].toString());
            }
        } else {
            weatherInformationByDay = new String[1];
            weatherInformationByDay[0] = "failed";
        }

        return weatherInformationByDay;
    }

    private String[] getWeatherData(String postcode) {
        String[] weatherInformationByDay = null;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJsonString = null;
        int days = 7;


        try {
            // Construct URL for request
            URL url = WeatherURLProvider.getURL(WeatherFormat.JSON,
                    WeatherUnitSystem.Metric,
                    days,
                    postcode);

            // Create request and open connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(requestMethod);
            urlConnection.connect();

            // Read input stream
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();

            if (inputStream == null) {
                forecastJsonString = null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }

            if (stringBuffer.length() == 0) {
                forecastJsonString = null;
            }

            forecastJsonString = stringBuffer.toString();

            Log.d(LOG_TAG, "Forecast JSON string: " + forecastJsonString);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error" + e);
            forecastJsonString = null;
        } catch (NoSuchFieldException fe) {
            Log.e(LOG_TAG, "Error" + fe);
            forecastJsonString = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "error closing stream" + e);
                }
            }
        }

        weatherInformationByDay = this.parseJSONString(forecastJsonString);

        return weatherInformationByDay;
    }

    @Override
    protected String[] doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        String postcode = params[0];

        return this.getWeatherData(postcode);

    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);

        if (strings != null) {
            this.mWeatherAdapter.clear();
            for (String item : strings) {
                this.mWeatherAdapter.add(item);
            }
            this.mWeatherAdapter.notifyDataSetChanged();
        }
    }
}
