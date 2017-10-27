package com.iakrohmts.bestmoviesapp.SQLiteDb;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Rohmts on 5/22/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

  public static final String TABLE_NAME = "movies_favourite";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_ID_MOVIE = "id_movie";
  public static final String COLUMN_ORIGINAL_TITLE = "original_title";
  public static final String COLUMN_POSTER_PATH = "poster_patch";
  public static final String COLUMN_OVERVIEW = "overview";
  public static final String COLUMN_VOTE_AVERAGE = "vote_average";
  public static final String COLUMN_RELEASE_DATE = "release_date";
//  public static final String COLUMN_IMAGES = "image";
  private static final String db_name = "myfavoritemovies.db";
  private static final int db_version =1;

  private static final String db_create = "create table "
      + TABLE_NAME + "("
      + COLUMN_ID + " integer primary key autoincrement, "
      + COLUMN_ID_MOVIE + " TEXT not null, "
      + COLUMN_ORIGINAL_TITLE + " TEXT not null, "
      + COLUMN_POSTER_PATH + " TEXT not null, "
      + COLUMN_OVERVIEW + " TEXT not null, "
      + COLUMN_VOTE_AVERAGE + " INTEGER not null, "
      + COLUMN_RELEASE_DATE + " DATE not null);";
//    + COLUMN_IMAGES + " BLOB not null);";


  public DBHelper(Context context) {
    super(context, db_name, null, db_version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    try {
      db.execSQL(db_create);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(DBHelper.class.getName(), "Upgrading database from"
        + "version + "+oldVersion+" to "+newVersion+", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
    onCreate(db);
  }
}
