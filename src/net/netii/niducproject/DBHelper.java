package net.netii.niducproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "niducfresh.db";
	public static final int DATABASE_VERSION = 2;
	
	public static final String NIDUC_TABLE = "niduc_project";
	public static final String CASHDESK_ID = "cashdesk_id";
	public static final String CARD_PAID = "card_paid";
	public static final String SIZE = "shopping_size";
	public static final String DATE = "shopping_date";
	public static final String TIME = "shopping_time";	
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase base) {
		try {
			Log.i("sql", "not-created");
			base.execSQL("CREATE TABLE `niducstorage` ("+
					  "`id` int(11) NOT NULL AUTO_INCREMENT,"+
					  "`start_time` datetime NOT NULL,"+
					  "`cashorcard` tinyint(1) NOT NULL,"+
					  "`queuelen` int(11) NOT NULL,"+
					  "`taken_time` int(11) NOT NULL,"+
					  "`cash_no` smallint(6) NOT NULL,"+
					  "`user_login` varchar(30) NOT NULL,"+
					  "PRIMARY KEY (`id`)"+
					") ENGINE=MyISAM DEFAULT CHARSET=latin2 AUTO_INCREMENT=1 ;");
			Log.i("sql", "created");
		} catch (SQLiteException ex) {
			Log.i("sql", ex.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase base, int arg1, int arg2) {

	}	
	
	
	public String insertData(String start_time, String cashorcard, String queuelen, String taken_time, String cash_no, String user_login){
		
		String query = String.format("INSERT INTO `%s` " +
				"(start_time, cashorcard, queuelen, taken_time, cash_no, user_login) " +
				"VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", 
				start_time, cashorcard, queuelen, taken_time, cash_no, user_login);
				
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