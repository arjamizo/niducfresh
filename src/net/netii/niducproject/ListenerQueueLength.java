package net.netii.niducproject;


import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;

public class ListenerQueueLength implements OnClickListener, OnLongClickListener {

	Context context;
	Button queleLenBtn;

	public ListenerQueueLength(Context context, Button queleLenBtn) {
		super();
		this.context = context;
		this.queleLenBtn = queleLenBtn;
		queleLenBtn.setTag(new Integer(0));
		setLabel();
	}

	private void setLabel() {
		queleLenBtn.setText(Integer.toString(getNumber()));
	}

	public void increment() {
		Integer next=getNumber()+1;
		queleLenBtn.setTag(next);
		setLabel();
	}

	@Override
	public void onClick(View v) {
		increment();
		//Toast.makeText(context, queleLenBtn.getText(), Toast.LENGTH_LONG).show();
	}

	public void decrease() {
		Integer number=getNumber();
		if(number>0)
			number+=-1;
		queleLenBtn.setTag(number);
		setLabel();
		
	}

	public Integer getNumber() {
		return (Integer) queleLenBtn.getTag();
	}

	@Override
	public boolean onLongClick(View v) {
		decrease();
		return true;
	}

}
