package net.netii.niducproject;

import java.util.Calendar;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Toast;

public class ListenerFinishButton implements OnClickListener, OnLongClickListener {
	Context context; 
	DBHelper db;
	RowEntry row;
	Context act;
	Integer finish;
	public ListenerFinishButton(Context context, DBHelper db, RowEntry row, Context act, Integer finish) {
		super();
		this.context = context;
		this.db = db;
		this.row = row;
		this.act = act;
		this.finish = finish;
		row.setTime(new Integer(-1));
	}
	

	@Override
	public void onClick(View v) {
		String keys[]=row.getArrayOfKeys();
		String data[]=row.getArrayOfData();
		StringBuilder strbld=new StringBuilder();
		if(row.turnedOn==false) {
			Toast.makeText(context, "kasa "+Integer.toString(row.id)+" wylaczona", Toast.LENGTH_LONG).show();
			return;
		}
		for(int i=0; i<data.length; i++)
		{
			strbld.append(keys[i]);
			strbld.append("=");
			strbld.append(data[i]);
			strbld.append(",");
		}
		if(row.getTime()==-1)
		{
			//Toast.makeText(context, "ustawianie nowego czsau", Toast.LENGTH_LONG).show();
			row.setTime((int) (Calendar.getInstance().getTime().getTime()/1000));		
		}
		else if (row.getTime()<0)
		{
			row.setTime(row.getTime()+1);
		}
		else
		{
			//Toast.makeText(context, strbld.toString(), Toast.LENGTH_LONG).show();
			Toast.makeText(context, "dodano do bazy info o kasie nr "+Integer.toString(row.id), Toast.LENGTH_LONG).show();
			Log.i("sql",strbld.toString());
			new DataSenderThread(keys, data, db);
			for(int i=0; i<data.length; i++)
				Log.i("sql",keys[i]+"="+data[i]);

			db.insertData(
					data[java.util.Arrays.asList(keys).indexOf("start_time")]
					, data[java.util.Arrays.asList(keys).indexOf("card")]
					, data[java.util.Arrays.asList(keys).indexOf("queuelen")]
							, data[java.util.Arrays.asList(keys).indexOf("shoppingsize")]
									, data[java.util.Arrays.asList(keys).indexOf("taken_time")]
									, data[java.util.Arrays.asList(keys).indexOf("cash_no")]
											, data[java.util.Arrays.asList(keys).indexOf("user_login")]);
			//(data[0], data[2], data[4], data[5], data[1], data[6]);
			row.listenerQueueLength.decrease();
			row.reset();
			row.setTime(-1);
		}
		row.updateLabel();
		((MainActivity)act).updateRows();
	}


	@Override
	public boolean onLongClick(View v) {
		row.reset();
		row.setTime(-2);
		row.queueLen=0;
		row.queueLenBtn.setText("0");
		row.updateLabel();
		return true;
	}

}
