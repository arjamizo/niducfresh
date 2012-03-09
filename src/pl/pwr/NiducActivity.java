package pl.pwr;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class NiducActivity extends Activity implements OnClickListener {
    private ToggleButton on;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        on = (ToggleButton)findViewById(R.id.ToggleButton1);
        on.setOnClickListener(this);
        Button add=(Button)findViewById(R.id.dodaj);
        add.setOnClickListener(new AddButtonListener(this));
        
    }

	@Override
	public void onClick(View v) {

    	if((on.isChecked()))
        {
        	Toast.makeText(NiducActivity.this, Integer.toString(v.getId()).concat("checked"), Toast.LENGTH_SHORT).show();
        }
    	else
    	{
    		Toast.makeText(NiducActivity.this, Integer.toString(v.getId()).concat("unchecked"), Toast.LENGTH_SHORT).show();
    	}
	}
}