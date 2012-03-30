package net.netii.niducproject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.R.string;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RowEntry extends LinearLayout {
	public ToggleButton tglbtn;
	private CheckBox card;
	private Button shoppingSize;
	private Button queueLen;
	public Button finish;
	private MainActivity act;
	private RadioButton shpSizeS;
	private RadioButton shpSizeM;
	private RadioButton shpSizeL;
	public ListenerQueueLength listenerQueueLength;
	private final Context context;

	public String[] getArrayOfData() {
		Calendar c = Calendar.getInstance();
		String sDate = c.get(Calendar.YEAR) + "-" 
				+ (c.get(Calendar.MONTH)+1)
				+ "-" + c.get(Calendar.DAY_OF_MONTH) 
				+ " " + c.get(Calendar.HOUR_OF_DAY) 
				+ ":" + c.get(Calendar.MINUTE)
				+ ":" + c.get(Calendar.SECOND);
		// List<String> data=new ArrayList<String>();
		
		WifiManager wimanager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		String address= wimanager.getConnectionInfo().getMacAddress();
		
		String strs[] = {
				//Integer.toString((Integer) finish.getTag())

				sDate
				,tglbtn.getText().toString()
				,card.isChecked() ? "1" : "0"
				,Integer.toString((Integer)shoppingSize.getTag())
				,queueLen.getText().toString()
				,Integer.toString((int) (Calendar.getInstance().getTime()
						.getTime() / 1000 - (Integer) finish.getTag()))
				,android.os.Build.MODEL+"_mac:"+address };
		/*
		 * for(String str : strs) data.add(str);
		 */
		return strs;
	}

	public String[] getArrayOfKeys() {
		return new String[] { "start_time", "cash_no", "card",
				"shoppingsize", "queuelen", "taken_time", "user_login" };
	}

	public Integer getTime() {
		return (Integer) finish.getTag();
	}

	public Button setTime(Integer time) {
		finish.setTag(time);
		return finish;

	}
	
	private LinearLayout getOnLayout(int id, Context context, DBHelper db)
	{
		ArrayList<View> elements=new ArrayList<View>();
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 1.0f));
		
		linearLayout.setGravity(Gravity.CENTER | Gravity.LEFT);
		linearLayout.setPadding(0, 15, 0, 15);
		card = new CheckBox(context);
		elements.add(card);

		shoppingSize = new Button(context);
		shoppingSize.setText("M");
		//shoppingSize.setWidth(80);
		//linearLayout.addView(shoppingSize);
		elements.add(shoppingSize);
		shoppingSize.setOnClickListener(new ListenerShoppingSize(getContext(),
				shoppingSize));

		/*shpSizeS = new RadioButton(context);
		addView(shpSizeS);
		shpSizeM = new RadioButton(context);
		addView(shpSizeM);
		shpSizeL = new RadioButton(context);
		addView(shpSizeL);*/

		queueLen = new Button(context);
		queueLen.setText("4");
		//queueLen.setWidth(80);
		elements.add(queueLen);
		//linearLayout.addView(queueLen);
		listenerQueueLength = new ListenerQueueLength(
				getContext(), queueLen);
		queueLen.setOnClickListener(listenerQueueLength);
		queueLen.setOnLongClickListener(listenerQueueLength);

		finish = new Button(context);
		//finish.setWidth(100);
		finish.setText("00:13");
		//linearLayout.addView(finish);
		elements.add(finish);
		finish.setOnClickListener(new ListenerFinishButton(context, db,
				this,context));
		for(View view:elements)
		{
			if(view instanceof CheckBox==false)view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1));
			linearLayout.addView(view);
		}
		return linearLayout;
	}

	public RowEntry(Context context, DBHelper db, MainActivity act, int counter) {
		super(context);
		this.context = context;

		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, 1.0f));
		setGravity(Gravity.CENTER | Gravity.LEFT);
		this.act=act;

		tglbtn = new ToggleButton(context);
		String string=Integer.toString(counter);
		tglbtn.setText(string);
		tglbtn.setTextOff(string);
		tglbtn.setTextOn(string);
		addView(tglbtn);
		
		addView(getOnLayout(counter, act, db));

		
	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public void updateLabel() {
		if (getTime() == -1) {
			finish.setText("start");
		} else {
			int newtime = (int) (Calendar.getInstance().getTime().getTime() / 1000)
					- getTime();
			StringBuilder stringBuilder = new StringBuilder();
			int minutes = newtime/60;
			if(minutes<10)
				stringBuilder.append('0');
			stringBuilder.append(minutes);
			stringBuilder.append(':');
			int seconds = newtime%60;
			if(seconds<10)
				stringBuilder.append('0');
			stringBuilder.append(seconds);
			finish.setText(stringBuilder.toString());
		}
	}
	
	

}
