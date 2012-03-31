package net.netii.niducproject;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.InputType;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
	LinearLayout configLayout;
	EditText number;
	Button queueLenBtn;
	CheckBox cardBtn;
	int counter;

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
		
		int shoppingSizeFromRadios=
				(shpSizeS.isChecked()?1:0)
						+(shpSizeM.isChecked()?2:0)
								+(shpSizeL.isChecked()?3:0);
						
		
		String strs[] = {
				//Integer.toString((Integer) finish.getTag())

				sDate
				,Integer.toString((Integer)id)
				,card?"1":"0"
				,Integer.toString((Integer)shoppingSizeFromRadios)
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
		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, 1.0f));
		
		layout.setGravity(Gravity.CENTER | Gravity.LEFT);
		layout.setPadding(0, 4, 0, 4);
		cardBtn = new CheckBox(context);
		cardBtn.setOnCheckedChangeListener(new ListenerIfCard(context, this));
		elements.add(cardBtn);

		Button shoppingSizeBtn = new Button(context);
		//elements.add(shoppingSizeBtn);
		shoppingSizeBtn.setOnClickListener(new ListenerShoppingSize(getContext(),
				shoppingSizeBtn, shoppingSize));

		RadioGroup radioGroup = new RadioGroup(context);
		radioGroup.setOrientation(RadioGroup.HORIZONTAL);
		shpSizeS = new RadioButton(context);
		radioGroup.addView(shpSizeS);
		shpSizeM = new RadioButton(context);
		radioGroup.addView(shpSizeM);
		shpSizeL = new RadioButton(context);
		radioGroup.addView(shpSizeL);
		
		layout.addView(radioGroup);

		
		queueLenBtn = new Button(context);
		//queueLen.setWidth(80);
		elements.add(queueLenBtn);
		listenerQueueLength = new ListenerQueueLength(
				getContext(), this);
		queueLenBtn.setOnClickListener(listenerQueueLength);
		queueLenBtn.setOnLongClickListener(listenerQueueLength);

		finishBtn = new Button(context);
		//finish.setWidth(100);
		finishBtn.setText("00:13");
		//linearLayout.addView(finish);
		elements.add(finishBtn);
		ListenerFinishButton listenerFinishButton = new ListenerFinishButton(context, db,
				this,context, finish);
		finishBtn.setOnClickListener(listenerFinishButton);
		finishBtn.setOnLongClickListener(listenerFinishButton);
		
		for(View view:elements)
		{
			if(view instanceof CheckBox==false)view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			layout.addView(view);
		}
		queueLenBtn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		finishBtn.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1f));
		layout.setVisibility(turnedOn?LinearLayout.VISIBLE:LinearLayout.INVISIBLE);
		return layout;
	}

	public void init(Integer counter, boolean beginning) {
		//queueLen=0;
		if(beginning)
			queueLen=0;
		//else 
		//	queueLen-=queueLen>0?1:0;
		shoppingSize=0;
		finish=0;
		turnedOn=!beginning;
		id=counter;
		card=false;

	}

	public RowEntry(Context context, DBHelper db, MainActivity act, int counter) {
		super(context);
		this.context = context;
		this.counter = counter;

		//queueLen=1; //init will decrease it
		init(counter,true);
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
		
		configLayout = getOffLayout(counter, act);
		addView(configLayout);


		listenerToggleButton.toggleTo(false);
	}

	private LinearLayout getOffLayout(int counter, MainActivity act) {
		LinearLayout linearLayout = new LinearLayout(act);
		number = new EditText(act);
		number.setInputType(InputType.TYPE_CLASS_NUMBER);
		number.setText(Integer.toString(counter));
		linearLayout.addView(number);
		return linearLayout;
	}

	public void reset() {
		init(counter,false);

		shpSizeL.setChecked(false);
		shpSizeM.setChecked(false);
		shpSizeS.setChecked(false);
		cardBtn.setChecked(false);
		//queueLenBtn.setText(Integer.toString(queueLen));

	}

	public void updateLabel() {
		if (getTime() == -1) {
			finishBtn.setText("start");
		}else if (getTime() < 0) {
				finishBtn.setText("cancelled");
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
