package com.windrealm.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class MultiSelectList extends Activity {

	private ListView mainListView;
	private mItems[] itemss;
	private ArrayAdapter<mItems> listAdapter;
	ArrayList<String> checked = new ArrayList<String>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Find the ListView resource.
		mainListView = (ListView) findViewById(R.id.mainListView);

		// When item is tapped, toggle checked properties of CheckBox and
		// Planet.
		mainListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View item,
							int position, long id) {
						mItems planet = listAdapter.getItem(position);
						planet.toggleChecked();
						SelectViewHolder viewHolder = (SelectViewHolder) item
								.getTag();
						viewHolder.getCheckBox().setChecked(planet.isChecked());

					}
				});

		// Create and populate planets.
		itemss = (mItems[]) getLastNonConfigurationInstance();

		ArrayList<mItems> planetList = new ArrayList<mItems>();

		planetList.add(new mItems("DJ-Android"));
		planetList.add(new mItems("Android"));
		planetList.add(new mItems("iPhone"));
		planetList.add(new mItems("BlackBerry"));
		planetList.add(new mItems("Java"));
		planetList.add(new mItems("PHP"));
		planetList.add(new mItems(".Net"));

		// Set our custom array adapter as the ListView's adapter.
		listAdapter = new SelectArralAdapter(this, planetList);
		mainListView.setAdapter(listAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, Menu.NONE, "Products");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:

			for (int i = 0; i < checked.size(); i++) {
				Log.d("pos : ", "" + checked.get(i));
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/** Holds planet data. */
	private static class mItems {
		private String name = "";
		private boolean checked = false;

		public mItems() {
		}

		public mItems(String name) {
			this.name = name;
		}

		public mItems(String name, boolean checked) {
			this.name = name;
			this.checked = checked;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		public String toString() {
			return name;
		}

		public void toggleChecked() {
			checked = !checked;
		}
	}

	/** Holds child views for one row. */
	private static class SelectViewHolder {
		private CheckBox checkBox;
		private TextView textView;

		public SelectViewHolder() {
		}

		public SelectViewHolder(TextView textView, CheckBox checkBox) {
			this.checkBox = checkBox;
			this.textView = textView;
		}

		public CheckBox getCheckBox() {
			return checkBox;
		}

		public void setCheckBox(CheckBox checkBox) {
			this.checkBox = checkBox;
		}

		public TextView getTextView() {
			return textView;
		}

		public void setTextView(TextView textView) {
			this.textView = textView;
		}
	}

	/** Custom adapter for displaying an array of Planet objects. */
	private static class SelectArralAdapter extends ArrayAdapter<mItems> {
		private LayoutInflater inflater;

		public SelectArralAdapter(Context context, List<mItems> planetList) {
			super(context, R.layout.simplerow, R.id.rowTextView, planetList);
			// Cache the LayoutInflate to avoid asking for a new one each time.
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Planet to display
			mItems planet = (mItems) this.getItem(position);

			// The child views in each row.
			CheckBox checkBox;
			TextView textView;

			// Create a new row view
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.simplerow, null);

				// Find the child views.
				textView = (TextView) convertView
						.findViewById(R.id.rowTextView);
				checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);
				// Optimization: Tag the row with it's child views, so we don't
				// have to
				// call findViewById() later when we reuse the row.
				convertView.setTag(new SelectViewHolder(textView, checkBox));
				// If CheckBox is toggled, update the planet it is tagged with.
				checkBox.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						mItems planet = (mItems) cb.getTag();
						planet.setChecked(cb.isChecked());
					}
				});
			}
			// Reuse existing row view
			else {
				// Because we use a ViewHolder, we avoid having to call
				// findViewById().
				SelectViewHolder viewHolder = (SelectViewHolder) convertView
						.getTag();
				checkBox = viewHolder.getCheckBox();
				textView = viewHolder.getTextView();
			}

			// Tag the CheckBox with the Planet it is displaying, so that we can
			// access the planet in onClick() when the CheckBox is toggled.
			checkBox.setTag(planet);
			// Display planet data
			checkBox.setChecked(planet.isChecked());
			textView.setText(planet.getName());
			return convertView;
		}
	}

	public Object onRetainNonConfigurationInstance() {
		return itemss;
	}
}