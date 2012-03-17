package net.netii.niducproject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class RowEntry extends LinearLayout {
	private ToggleButton tglbtn;
	private CheckBox card;
	private Button shoppingSize;
	private Button queueLen;
	public Button finish;
	private MainActivity act;

	public String[] getArrayOfData() {
		// List<String> data=new ArrayList<String>();
		String strs[] = {
				Integer.toString((Integer) finish.getTag()),
				tglbtn.getText().toString(),
				card.isChecked() ? "1" : "0",
				shoppingSize.getText().toString(),
				queueLen.getText().toString(),
				Integer.toString((int) (Calendar.getInstance().getTime()
						.getTime() / 1000 - (Integer) finish.getTag())),
				android.os.Build.MODEL };
		/*
		 * for(String str : strs) data.add(str);
		 */
		return strs;
	}

	public String[] getArrayOfKeys() {
		return new String[] { "start_time", "cash_no", "cardorcash",
				"shoppingsize", "queuelen", "taken_time", "usr" };
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

		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT, 1.0f));
		setGravity(Gravity.CENTER | Gravity.LEFT);
		setPadding(0, 15, 0, 15);

		tglbtn = new ToggleButton(context);
		addView(tglbtn);

		card = new CheckBox(context);
		addView(card);

		shoppingSize = new Button(context);
		shoppingSize.setText("M");
		shoppingSize.setWidth(80);
		addView(shoppingSize);
		shoppingSize.setOnClickListener(new ListenerShoppingSize(getContext(),
				shoppingSize));

		queueLen = new Button(context);
		queueLen.setText("4");
		queueLen.setWidth(80);
		addView(queueLen);
		ListenerQueueLength listenerQueueLength = new ListenerQueueLength(
				getContext(), queueLen);
		queueLen.setOnClickListener(listenerQueueLength);
		queueLen.setOnLongClickListener(listenerQueueLength);

		finish = new Button(context);
		finish.setWidth(100);
		finish.setText("00:13");
		addView(finish);
		finish.setOnClickListener(new ListenerFinishButton(getContext(), db,
				this, act));
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
			finish.setText(Integer.toString(newtime));
		}
	}

}
