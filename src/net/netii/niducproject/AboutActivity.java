package net.netii.niducproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ScrollView scrollView = new ScrollView(this);
		scrollView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		RelativeLayout relativeLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		relativeLayout.setGravity(RelativeLayout.CENTER_IN_PARENT);
		relativeLayout.setLayoutParams(relativeLayoutParams);
		TextView textView = new TextView(this);
		textView.setText("O programie.");
		relativeLayout.addView(textView);
		linearLayout.addView(relativeLayout);
		scrollView.addView(linearLayout);
		//setContentView(scrollView);
		setContentView(R.layout.about);
	}

}
