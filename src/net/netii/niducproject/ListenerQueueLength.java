package net.netii.niducproject;


import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;

public class ListenerQueueLength implements OnClickListener, OnLongClickListener {

	Context context;
	RowEntry rowEntry;

	public ListenerQueueLength(Context context, RowEntry rowEntry) {
		super();
		this.context = context;
		this.rowEntry = rowEntry;
		setLabel();
	}

	private void setLabel() {
		rowEntry.queueLenBtn.setText(Integer.toString(getNumber()));
	}

	public void increment() {
		rowEntry.queueLen=getNumber()+1;
		setLabel();
	}

	@Override
	public void onClick(View v) {
		increment();
		//Toast.makeText(context, queleLenBtn.getText(), Toast.LENGTH_LONG).show();
	}

	public void decrease() {
		rowEntry.queueLen=getNumber()-((getNumber()>0)?1:0);
		setLabel();
		
	}

	public Integer getNumber() {
		return rowEntry.queueLen;
	}

	@Override
	public boolean onLongClick(View v) {
		decrease();
		return true;
	}

}
