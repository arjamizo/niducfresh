package net.netii.niducproject;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private DBHelper dbHelp;
	private LinearLayout wrapper;
	ArrayList<RowEntry> rows = new ArrayList<RowEntry>();

	public void updateRows() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				for (RowEntry row : rows)
					row.updateLabel();
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelp = new DBHelper(this);
		// setContentView(R.layout.main);
		wrapper = new LinearLayout(this);
		wrapper.setOrientation(LinearLayout.VERTICAL);
		wrapper.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT, 0f));

		TextView label = new TextView(this);
		label.setText("proram");
		wrapper.addView(label);

		ScrollView scrollView = new ScrollView(this);
		LinearLayout insideScroll = new LinearLayout(this);
		insideScroll.setOrientation(LinearLayout.VERTICAL);
		insideScroll.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT, 0f));
		scrollView.addView(insideScroll);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					updateRows();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();


		if(rows.isEmpty())
			for (int i = 0; i < 12; i++) {
				RowEntry row = new RowEntry(this, dbHelp, this, i);
				rows.add(row);
				insideScroll.addView(row);
			}
		else
			for(RowEntry row:rows)
				insideScroll.addView(row);

		
		wrapper.addView(scrollView);

		setContentView(wrapper);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.about:
            Intent intent = new Intent(this,AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.send:
			sendDataFromDBToServer();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void sendDataFromDBToServer() {
		//http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html#rawQuery(java.lang.String, java.lang.String[])
		Cursor c = dbHelp.selectData();
		String keys[] = new String[c.getColumnCount()];
		c.moveToFirst();
		do {
			String data[] = new String[c.getColumnCount()];
			for (int i = 0; i < c.getColumnCount(); i++) {
				keys[i] = c.getColumnName(i);
				data[i] = c.getString(i);
			}
			//keys[data.length-1]="usr";
			//data[data.length-1]=android.os.Build.MODEL;
			new DataSenderThread(keys, data, dbHelp);
		} while (c.moveToNext());
	}
}
