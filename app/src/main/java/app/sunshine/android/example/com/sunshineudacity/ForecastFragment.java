package app.sunshine.android.example.com.sunshineudacity;

/**
 * Created by constantin-adrianlulea on 3/7/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {
    private static final String LOG_TAG = ForecastFragment.class.getSimpleName();

    private ArrayAdapter<String> mWeatherAdapter;
    private ArrayList<String> mWeatherItems;

    public ForecastFragment() {
        mWeatherItems = new ArrayList<>();
    }

    private void populateList() {
        FetchWeatherTask task = new FetchWeatherTask(this.mWeatherAdapter);

        String postalCodeLocation = "94043";

        task.execute(postalCodeLocation);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            populateList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mWeatherAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                mWeatherItems);

        ListView forecastListView = (ListView) rootView.findViewById(R.id.listview_forecast);

        forecastListView.setAdapter(mWeatherAdapter);
        //forecastListView.setOnItemClickListener(this);

        if (savedInstanceState == null) {
            //this.populateList();
        }

        return rootView;
    }
}
