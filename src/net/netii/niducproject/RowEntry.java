package net.netii.niducproject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.R.string;
import android.content.Context;
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
	static int counter=0;
	private RadioButton shpSizeS;
	private RadioButton shpSizeM;
	private RadioButton shpSizeL;
	public ListenerQueueLength listenerQueueLength;

	public String[] getArrayOfData() {
		Calendar c = Calendar.getInstance();
		String sDate = c.get(Calendar.YEAR) + "-" 
				+ (c.get(Calendar.MONTH)+1)
				+ "-" + c.get(Calendar.DAY_OF_MONTH) 
				+ " " + c.get(Calendar.HOUR_OF_DAY) 
				+ ":" + c.get(Calendar.MINUTE)
				+ ":" + c.get(Calendar.SECOND);
		// List<String> data=new ArrayList<String>();
		String strs[] = {
				//Integer.toString((Integer) finish.getTag())

				sDate
				,tglbtn.getText().toString()
				,card.isChecked() ? "1" : "0"
				,Integer.toString((Integer)shoppingSize.getTag())
				,queueLen.getText().toString()
				,Integer.toString((int) (Calendar.getInstance().getTime()
						.getTime() / 1000 - (Integer) finish.getTag()))
				,android.os.Build.MODEL };
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

	public RowEntry(Context context, DBHelper db, MainActivity act) {
		super(context);

		this.act=act;
		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, 1.0f));
		setGravity(Gravity.CENTER | Gravity.LEFT);
		setPadding(0, 15, 0, 15);

		tglbtn = new ToggleButton(context);
		counter+=1;
		String string=Integer.toString(counter);
		tglbtn.setText(string);
		tglbtn.setTextOff(string);
		tglbtn.setTextOn(string);
		addView(tglbtn);

		card = new CheckBox(context);
		addView(card);

		shoppingSize = new Button(context);
		shoppingSize.setText("M");
		shoppingSize.setWidth(80);
		addView(shoppingSize);
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
		queueLen.setWidth(80);
		addView(queueLen);
		listenerQueueLength = new ListenerQueueLength(
				getContext(), queueLen);
		queueLen.setOnClickListener(listenerQueueLength);
		queueLen.setOnLongClickListener(listenerQueueLength);

		finish = new Button(context);
		finish.setWidth(100);
		finish.setText("00:13");
		addView(finish);
		finish.setOnClickListener(new ListenerFinishButton(getContext(), db,
				this,act));
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
