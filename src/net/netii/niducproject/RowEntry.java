package net.netii.niducproject;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RowEntry extends LinearLayout {
	Integer id;
	boolean turnedOn;
	boolean card;
	Integer shoppingSize;
	Integer queueLen;
	Integer finish;
	private MainActivity act;
	private RadioButton shpSizeS;
	private RadioButton shpSizeM;
	private RadioButton shpSizeL;
	public ListenerQueueLength listenerQueueLength;
	private Context context;
	public Button finishBtn;
	public ToggleButton tglbtn;
	LinearLayout checkoutClerkLayout;

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
				,Integer.toString((Integer)id)
				,card?"1":"0"
				,Integer.toString((Integer)shoppingSize)
				,Integer.toString(queueLen)
				,Integer.toString((int) (Calendar.getInstance().getTime().getTime() / 1000 - finish))
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
		return finish;
	}

	public int setTime(Integer time) {
		finish=time;
		return finish;

	}
	
	private LinearLayout getOnLayout(int id, Context context, DBHelper db)
	{

		ArrayList<View> elements=new ArrayList<View>();
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 1.0f));
		
		linearLayout.setGravity(Gravity.CENTER | Gravity.LEFT);
		linearLayout.setPadding(0, 4, 0, 4);
		CheckBox cardBtn = new CheckBox(context);
		cardBtn.setOnCheckedChangeListener(new ListenerIfCard(context, this));
		elements.add(cardBtn);

		Button shoppingSizeBtn = new Button(context);
		//elements.add(shoppingSizeBtn);
		shoppingSizeBtn.setOnClickListener(new ListenerShoppingSize(getContext(),
				shoppingSizeBtn, shoppingSize));

		RadioGroup radioGroup = new RadioGroup(context);
		shpSizeS = new RadioButton(context);
		radioGroup.addView(shpSizeS);
		shpSizeM = new RadioButton(context);
		radioGroup.addView(shpSizeM);
		shpSizeL = new RadioButton(context);
		radioGroup.addView(shpSizeL);
		
		addView(radioGroup);

		
		Button queueLenBtn = new Button(context);
		//queueLen.setWidth(80);
		elements.add(queueLenBtn);
		listenerQueueLength = new ListenerQueueLength(
				getContext(), queueLenBtn, queueLen);
		queueLenBtn.setOnClickListener(listenerQueueLength);
		queueLenBtn.setOnLongClickListener(listenerQueueLength);

		finishBtn = new Button(context);
		//finish.setWidth(100);
		finishBtn.setText("00:13");
		//linearLayout.addView(finish);
		elements.add(finishBtn);
		finishBtn.setOnClickListener(new ListenerFinishButton(context, db,
				this,context, finish));
		
		for(View view:elements)
		{
			if(view instanceof CheckBox==false)view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1));
			linearLayout.addView(view);
		}
		linearLayout.setVisibility(turnedOn?LinearLayout.VISIBLE:LinearLayout.INVISIBLE);
		return linearLayout;
	}

	public void init() {
		queueLen=0;
		shoppingSize=0;
		finish=0;
		turnedOn=false;
		id=0;
		card=false;
	}

	public RowEntry(Context context, DBHelper db, MainActivity act, int counter) {
		super(context);
		this.context = context;

		init();
		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, 1.0f));
		setGravity(Gravity.CENTER | Gravity.LEFT);
		this.act=act;

		tglbtn = new ToggleButton(context);
		String string=Integer.toString(counter);
		tglbtn.setText(string);
		tglbtn.setTextOff(string);
		tglbtn.setTextOn(string);
		//Toast.makeText(act, "cze", Toast.LENGTH_LONG).show();
		ListenerToggleButton listenerToggleButton = new ListenerToggleButton(context, this);
		tglbtn.setOnCheckedChangeListener(listenerToggleButton);
		//listenerToggleButton.onCheckedChanged(tglbtn, true);
		addView(tglbtn);
		
		
		checkoutClerkLayout = getOnLayout(counter, act, db);
		addView(checkoutClerkLayout);


		listenerToggleButton.toggleTo(false);
	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public void updateLabel() {
		if (getTime() == -1) {
			finishBtn.setText("start");
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
			finishBtn.setText(stringBuilder.toString());
		}
	}

	public void setTurnedOn(boolean isChecked) {
		turnedOn=isChecked;
	}
	
	

}
