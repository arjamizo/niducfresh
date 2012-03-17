package net.netii.niducproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "ndc.db";
	public static final int DATABASE_VERSION = 2;
	
	public static final String TABLE = "storage";
	public static final String CASHDESK_ID = "cashdesk_id";
	public static final String CARD_PAID = "card_paid";
	public static final String SIZE = "shopping_size";
	public static final String DATE = "shopping_date";
	public static final String TIME = "shopping_time";
	private final Context context;	
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;		
	}

	@Override
	public void onCreate(SQLiteDatabase base) {
		try {
			Log.i("sql", "not-created");
			String string = context.getString(R.string.createDb);
			//string.replace("TABLENAME", TABLE);
			Log.i("sql", string);
			base.execSQL(string);
			//Log.i("sql");
		} catch (SQLiteException ex) {
			Log.i("sql", ex.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase base, int arg1, int arg2) {

	}	
	
	public Cursor selectData() {
		return getReadableDatabase().rawQuery("select * from `storage`", null);
	}
	
	public String insertData(String start_time, String cashorcard, String queuelen, String shoppingSize, String taken_time, String cash_no, String user_login){
		
		String query = String.format("INSERT INTO `%s`" +
				"(start_time, cashorcard, queuelen, shoppingsize, taken_time, cash_no, user_login) " +
				"VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')", 
				TABLE, 
				start_time, cashorcard, queuelen, shoppingSize, taken_time, cash_no, user_login);
		Log.i("sql", query);
		try{
			SQLiteDatabase base = getWritableDatabase();			
			base.execSQL(query);
		}catch(SQLiteException ex){
			Log.i("sql", ex.getMessage());
			return "Database error";
		}           
            
		//new DataSenderThread(cashdeskId, cardPaid, size, date, time);
		return query;
	}
}