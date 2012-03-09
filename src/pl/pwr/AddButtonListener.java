package pl.pwr;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddButtonListener implements OnClickListener {

	Activity v = null;

	public AddButtonListener(Activity activity) {
		super();
		this.v = activity;
	}

	@Override
	public void onClick(View view) {
    		int sizeOfPurchasedSet=(((RadioButton)v.findViewById(R.id.male)).isChecked()?1:0)+
    			(((RadioButton)v.findViewById(R.id.srednie)).isChecked()?2:0)+
    			(((RadioButton)v.findViewById(R.id.duze)).isChecked()?3:0);
    		String text=new StringBuilder("new entry: checkoutspot nr [")
    		.append(((TextView)v.findViewById(R.id.kasa)).getText())
    		.append("] paid with [")
    		.append(((CheckBox)v.findViewById(R.id.czykarta)).isChecked()?"card":"cash")
    		.append("] of [")
    		.append(new String[]{"unknown", "small", "medium", "big"}[sizeOfPurchasedSet])
    		.append("] size was added to DB")
    		.toString();
    		Toast.makeText(v, text, Toast.LENGTH_SHORT).show();

    		
	}

}
