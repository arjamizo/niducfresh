package net.netii.niducproject;

import java.util.ArrayList;
import java.util.List;

import android.R.layout;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private DBHelper dbHelp;
	private LinearLayout wrapper;
	private List<View> rows=new ArrayList<View>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		wrapper = new LinearLayout(this);
		wrapper.setOrientation(LinearLayout.VERTICAL);
		setContentView(wrapper);
		
		ScrollView scroll=new ScrollView(this);
		MarginLayoutParams scrollLayoutParams = new MarginLayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		scrollLayoutParams.setMargins(0, 0, 0, 50);
		scroll.setLayoutParams(scrollLayoutParams);
		
		LinearLayout insideScroll = new LinearLayout(this);
		//insideScroll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		insideScroll.setOrientation(LinearLayout.VERTICAL);
		for(int i=0; i<12; ++i)
		{
			//insideScroll.addView(new RowEntry(this));
//			LinearLayout ll = (LinearLayout) findViewById(R.id.main_layout);
//			LayoutInflater inflater = (LayoutInflater)getSystemService
//				      (Context.LAYOUT_INFLATER_SERVICE);

			//http://tech.chitgoks.com/2008/03/19/android-how-to-load-layout-xml-files-dynamically-during-runtime/
			insideScroll.addView(LayoutInflater.from(getBaseContext()).inflate(R.layout.row,
	                scroll, false));
			//inflated.findViewById(id);
		}
		scroll.addView(insideScroll);

		TextView text=new TextView(this);
		text.setText("labels");
		wrapper.addView(text);
		wrapper.addView(scroll);
		wrapper.addView(LayoutInflater.from(getBaseContext()).inflate(R.layout.foot,
				wrapper, false));
		dbHelp = new DBHelper(this);
	}
}
