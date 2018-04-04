package com.task.test.weatherapp.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;
import android.util.Log;


import java.util.ArrayList;

/**
 * 
 * @author Skripchenko Yevgen
 * @version 1.0
 */

public class Controller {
	
	 private static int maxRowsInNames = -1;
	 public static int getMaxRowsInNames() {return maxRowsInNames;}
	 public static void setMaxRowsInNames(int maxRowsInNames) {Controller.maxRowsInNames = maxRowsInNames;}
	 private static final String TAG = Controller.class.getSimpleName();
	
	 
		/**
		 */ 
	 
	 public static boolean write(String name, String surname, String email, String telephone) {
		 boolean result = false;
		 try {
			DatabaseOpenHelper dbhelper = DatabaseOpenHelper.getDatabaseOpenHelperInstance();
			SQLiteDatabase db = dbhelper.getWritableDatabase();
			int countRows = -1;
			String addValue = null;
			Cursor cursor = db.query(Contract.Names.TABLE_NAME,null, null, null, null,null,null);// Names.DEFAULT_SORT);new String[] { "count(*)" }
			if (cursor.moveToFirst())
			{
				countRows = cursor.getInt(0);
			}
			cursor.close();
			if ((maxRowsInNames == -1) || (maxRowsInNames >= countRows)) {
				addValue = "INSERT INTO "+ Contract.Names.TABLE_NAME +"("+ Contract.Names.NAME+","+ Contract.Names.SURNAME+","+ Contract.Names.EMAIL+","+ Contract.Names.TELEPHONE+")VALUES("+name+","+surname+","+email+","+telephone+");";

				ContentValues values = new ContentValues();
				values.put(Contract.Names.NAME, name);
				values.put(Contract.Names.SURNAME,surname);
				values.put(Contract.Names.EMAIL, email);
				values.put(Contract.Names.TELEPHONE, telephone);

				db.beginTransaction();
				result = db.insert(Contract.Names.TABLE_NAME, null, values) > 0;
				db.setTransactionSuccessful();
				db.endTransaction();
				db.close();
			}
//			db.beginTransaction();
//			db.execSQL(addValue);
//            db.setTransactionSuccessful();
//            db.endTransaction();
//			db.close();
		} catch (SQLiteException e) {
             Log.e(TAG, "Failed open rimes database. ", e);
		} catch (SQLException e) {
             Log.e(TAG, "Failed to insert Names. ", e);}
		return result;
	 }
	 
	
	 
	 public static ArrayList<TestItem> read(){
		 ArrayList<TestItem> items = new ArrayList<>();
		 DatabaseOpenHelper dbhelper = DatabaseOpenHelper.getDatabaseOpenHelperInstance();
		 SQLiteDatabase db = dbhelper.getReadableDatabase();
		 Cursor cursor = db.query(Contract.Names.TABLE_NAME, null, null, null, null, null, null);
		 while(cursor.moveToNext()) {
			 long id = cursor.getLong(cursor.getColumnIndex(Contract.Names._ID));
			 String name = cursor.getString(cursor.getColumnIndex(Contract.Names.NAME));
			 String surname = cursor.getString(cursor.getColumnIndex(Contract.Names.SURNAME));
			 String email = cursor.getString(cursor.getColumnIndex(Contract.Names.EMAIL));
			 String telephone = cursor.getString(cursor.getColumnIndex(Contract.Names.TELEPHONE));
			 items.add(new TestItem(id,name,surname,email,telephone));
		 }
		 db.close();
		 return items;
	 }



	public static ArrayList<TestItem> readTest(String inputName, String inputSurname, String inputEmail, String inputNumber){
		ArrayList<TestItem> items = new ArrayList<>();
		DatabaseOpenHelper dbhelper = DatabaseOpenHelper.getDatabaseOpenHelperInstance();
		SQLiteDatabase db = dbhelper.getReadableDatabase();

		String whereClause = Contract.Names.NAME+" =? AND "+ Contract.Names.SURNAME+" =? AND "+ Contract.Names.EMAIL+" =? AND "+ Contract.Names.TELEPHONE+" =?";
		String[] whereArgs = new String[]{inputName, inputSurname, inputEmail, inputNumber};

		Cursor cursor = db.query(Contract.Names.TABLE_NAME, null, whereClause, whereArgs, null, null, null);
		while(cursor.moveToNext()) {
			long id = cursor.getLong(cursor.getColumnIndex(Contract.Names._ID));
			String name = cursor.getString(cursor.getColumnIndex(Contract.Names.NAME));
			String surname = cursor.getString(cursor.getColumnIndex(Contract.Names.SURNAME));
			String email = cursor.getString(cursor.getColumnIndex(Contract.Names.EMAIL));
			String telephone = cursor.getString(cursor.getColumnIndex(Contract.Names.TELEPHONE));
			items.add(new TestItem(id,name,surname,email,telephone));
		}
		db.close();
		return items;
	}






	/**
		 */ 
	
	 public static void update(String name, String surname, String email, String number, long id) {
		  try {
              DatabaseOpenHelper dbhelper = DatabaseOpenHelper.getDatabaseOpenHelperInstance();
              SQLiteDatabase sqliteDB = dbhelper.getWritableDatabase();
              String quer = null;
              Cursor cursor = sqliteDB.query(Contract.Names.TABLE_NAME,null, null, null, null,null,null);
              if (cursor.moveToFirst()) {
                    cursor.getInt(0);
              }
              cursor.close();
              quer = String.format("UPDATE " + Contract.Names.TABLE_NAME + " SET " +
            		  				Contract.Names.NAME + " = '" + name +"',"+
            		  				Contract.Names.SURNAME + " = '" + surname + "',"+
            		  				Contract.Names.EMAIL + " = '" + email + "',"+
            		  				Contract.Names.TELEPHONE + " = '" + number +
            		  				"' WHERE " + BaseColumns._ID + " = " + id);
             
              sqliteDB.execSQL(quer);
              sqliteDB.close();
      } catch (SQLiteException e) {
              Log.e(TAG, "Failed open database. ", e);
      } catch (SQLException e) {
              Log.e(TAG, "Failed to update Names. ", e);
      }
	 }
	
		/**
		 */ 
	
	  public static void delete(TestItem item) {
          DatabaseOpenHelper dbhelper= DatabaseOpenHelper.getDatabaseOpenHelperInstance();
          SQLiteDatabase sqliteDB = dbhelper.getWritableDatabase();
          sqliteDB.delete(Contract.Names.TABLE_NAME, BaseColumns._ID  + " = " + item.id, null);
          sqliteDB.close();
	  }
	
	

	
	
}
