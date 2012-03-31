package net.netii.niducproject;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ListenerIfCard implements OnCheckedChangeListener {

	private final RowEntry rowEntry;

	public ListenerIfCard(Context context, RowEntry rowEntry) {
		this.rowEntry = rowEntry;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		rowEntry.card=isChecked;
	}

}
