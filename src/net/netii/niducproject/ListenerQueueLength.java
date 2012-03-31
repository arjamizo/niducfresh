package net.netii.niducproject;


import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;

public class ListenerQueueLength implements OnClickListener, OnLongClickListener {

	Context context;
	Button queleLenBtn;
	Integer queueLen;

	public ListenerQueueLength(Context context, Button queleLenBtn, Integer queueLen) {
		super();
		this.context = context;
		this.queleLenBtn = queleLenBtn;
		this.queueLen = queueLen;
		setLabel();
	}

	private void setLabel() {
		queleLenBtn.setText(Integer.toString(getNumber()));
	}

	public void increment() {
		queueLen=getNumber()+1;
		setLabel();
	}

	@Override
	public void onClick(View v) {
		increment();
		//Toast.makeText(context, queleLenBtn.getText(), Toast.LENGTH_LONG).show();
	}

	public void decrease() {
		queueLen=new Integer(getNumber())-getNumber()>0?1:0;
		setLabel();
		
	}

	public Integer getNumber() {
		return queueLen;
	}

	@Override
	public boolean onLongClick(View v) {
		decrease();
		return true;
	}

}
