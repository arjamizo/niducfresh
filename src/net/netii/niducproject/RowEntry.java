package net.netii.niducproject;

import android.content.Context;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class RowEntry extends LinearLayout {
	public RowEntry(Context context) {
		super(context);
		
		ToggleButton tglbtn=new ToggleButton(context);
		addView(tglbtn);
		
		CheckBox card=new CheckBox(context);
		addView(card);

		Button shoppingSize=new Button(context);
		addView(shoppingSize);
		
		Button queueLen=new Button(context);
		addView(queueLen);
	}
}
