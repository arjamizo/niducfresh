package net.netii.niducproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;



public class MainActivity extends Activity {

	private DBHelper dbHelp;
	private LinearLayout wrapper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		wrapper = new LinearLayout(this);
		wrapper.setOrientation(LinearLayout.VERTICAL);
		
		TextView label = new TextView(this);
		label.setText("proram");
		wrapper.addView(label);
		
		ScrollView scrollView = new ScrollView(this);
		LinearLayout insideScroll = new LinearLayout(this);
		insideScroll.setOrientation(LinearLayout.VERTICAL);
		scrollView.addView(insideScroll);
		for(int i=0; i<12; i++)
			insideScroll.addView(new RowEntry(this));
		
		//footer
		LinearLayout footer = new LinearLayout(this);
		footer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
		footer.setOrientation(LinearLayout.VERTICAL);
		Button button = new Button(this);
		button.setText("wyslij na server");
		footer.addView(button);
		insideScroll.addView(footer);
		wrapper.addView(scrollView);
		
		setContentView(wrapper);
		
		
		dbHelp = new DBHelper(this);
	}
}
