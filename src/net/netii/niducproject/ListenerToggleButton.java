package net.netii.niducproject;

import android.content.Context;
import android.opengl.Visibility;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ListenerToggleButton implements OnCheckedChangeListener {

	public Context context;
	private final RowEntry row;

	public ListenerToggleButton(Context context, RowEntry row) {
				this.context = context;
				this.row = row;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		toggleTo(isChecked);
		
	}

	public void toggleTo(boolean isChecked) {
		//tglbtn.toggle();

		//tglbtn.setChecked(!tglbtn.isChecked());
		row.setTurnedOn(isChecked);
		if(isChecked==false)
		{
			row.checkoutClerkLayout.setVisibility(LinearLayout.INVISIBLE);
		}
		else
		{
			row.checkoutClerkLayout.setVisibility(LinearLayout.VISIBLE);
		}
		//Toast.makeText(context, turnedOn==1?"wl":"wyl", Toast.LENGTH_LONG).show();
	}
	
}
