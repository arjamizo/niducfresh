package net.netii.niducproject;

import android.content.Context;
import android.opengl.Visibility;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
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
			row.checkoutClerkLayout.setVisibility(LinearLayout.GONE);
			//row.removeView(row.checkoutClerkLayout);
			row.configLayout.setVisibility(LinearLayout.VISIBLE);
			//row.addView(row.configLayout);
			row.number.setText(Integer.toString(row.id));
		}
		else
		{
			row.id=Integer.parseInt(row.number.getText().toString());
			row.tglbtn.setTextOn(Integer.toString(row.id));
			row.tglbtn.setTextOff(Integer.toString(row.id));
			row.tglbtn.setText(Integer.toString(row.id));
			row.configLayout.setVisibility(LinearLayout.GONE);
			row.checkoutClerkLayout.setVisibility(LinearLayout.VISIBLE);
		}
		//Toast.makeText(context, turnedOn==1?"wl":"wyl", Toast.LENGTH_LONG).show();
	}
	
}
