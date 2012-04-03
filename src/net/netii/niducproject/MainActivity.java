package net.netii.niducproject;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
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
		Cursor cursor = dbHelp.selectData(false);
		int cnt=cursor.getColumnCount();
		String columns="";
		for(int i=0; i<cnt; ++i)
			columns+=", "+cursor.getColumnName(i);
		String dbInfo="db version: "+Integer.toString(dbHelp.getReadableDatabase().getVersion())+" nr of cols in table: "+cnt+": "+columns;
		cursor.close();
		label.setText(getResources().getString(R.string.header)+dbInfo);
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
			sendDataFromDBToServer(getApplicationContext());
			break;
		case R.id.delete:
			dbHelp.setDeletedAll();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void sendDataFromDBToServer(Context context) {
		//http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html#rawQuery(java.lang.String, java.lang.String[])
		Cursor c = dbHelp.selectData(true);
		String keys[] = new String[c.getColumnCount()];
		Toast.makeText(context, "sending...", Toast.LENGTH_LONG);
		if(c.moveToFirst()==false) 
			{
			c.close();
			Toast.makeText(context, "there are no records in db", Toast.LENGTH_LONG);
			return;
			}
		int id=0;
		do {
			String data[] = new String[c.getColumnCount()];
			for (int i = 0; i < c.getColumnCount(); i++) {
				keys[i] = c.getColumnName(i);
				data[i] = c.getString(i);
			}
			//keys[data.length-1]="usr";
			//data[data.length-1]=android.os.Build.MODEL;
			new DataSenderThread(keys, data, dbHelp);
			Toast.makeText(context, "wysylanie rekordu id="+id+" o "+data[0], Toast.LENGTH_LONG);
			id++;
		} while (c.moveToNext());
		c.close();
	}
}
