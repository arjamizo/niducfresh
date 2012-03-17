package net.netii.niducproject;

import java.util.ArrayList;

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
	ArrayList<RowEntry> rows=new ArrayList<RowEntry>();
	
	public void updateRows() {
		for(RowEntry row:rows)
			row.updateLabel();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelp = new DBHelper(this);
		//setContentView(R.layout.main);
		wrapper = new LinearLayout(this);
		wrapper.setOrientation(LinearLayout.VERTICAL);
		wrapper.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 0f));
		
		
		TextView label = new TextView(this);
		label.setText("proram");
		wrapper.addView(label);
		
		ScrollView scrollView = new ScrollView(this);
		LinearLayout insideScroll = new LinearLayout(this);
		insideScroll.setOrientation(LinearLayout.VERTICAL);
		insideScroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 0f));
		scrollView.addView(insideScroll);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					updateRows();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			}
		});
		
		for(int i=0; i<12; i++)
		{
			RowEntry row=new RowEntry(this, dbHelp, this);
			rows.add(row);
			insideScroll.addView(row);
		}
		
		//footer
		LinearLayout footer = new LinearLayout(this);
		footer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 1.0f));
		footer.setOrientation(LinearLayout.VERTICAL);
		Button button = new Button(this);
		button.setText("wyslij na server");
		footer.addView(button);
		insideScroll.addView(footer);
		wrapper.addView(scrollView);
		
		setContentView(wrapper);
		
	}
}
