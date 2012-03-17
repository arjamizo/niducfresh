package net.netii.niducproject;

import java.util.Calendar;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ListenerFinishButton implements OnClickListener {
	Context context; 
	DBHelper db;
	RowEntry row;
	private final MainActivity act;
	public ListenerFinishButton(Context context, DBHelper db, RowEntry row, MainActivity act) {
		super();
		this.context = context;
		this.db = db;
		this.row = row;
		this.act = act;
		row.setTime(new Integer(-1));
		
	}
	

	@Override
	public void onClick(View v) {
		String keys[]=row.getArrayOfKeys();
		String data[]=row.getArrayOfData();
		StringBuilder strbld=new StringBuilder();
		for(int i=0; i<data.length; i++)
		{
			strbld.append(keys[i]);
			strbld.append("=");
			strbld.append(data[i]);
			strbld.append(",");
		}
		Toast.makeText(context, strbld.toString(), Toast.LENGTH_LONG).show();
		Log.i("cze",strbld.toString());
		if(row.getTime()==-1)
		{
			row.setTime((int) (Calendar.getInstance().getTime().getTime()/1000));		
		}
		else
		{
			row.reset();
			row.setTime(-1);
		}
		row.updateLabel();
		act.updateRows();
	}

}
