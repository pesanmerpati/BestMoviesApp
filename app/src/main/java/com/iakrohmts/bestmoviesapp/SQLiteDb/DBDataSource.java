package com.iakrohmts.bestmoviesapp.SQLiteDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rohmts on 5/22/2017.
 */

public class DBDataSource {

  Context c;
  SQLiteDatabase database;
  DBHelper dbHelper;

  public DBDataSource (Context context) {
    dbHelper = new DBHelper(context);
  }

  public void open() {
    try {
      database = dbHelper.getWritableDatabase();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void close() {
    try {
      dbHelper.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public long add(String id_movie, String original_title, String posterPath, String overview,
      Double voteAverage, String releaseDate)
  {
    try
    {
      ContentValues cv=new ContentValues();
      cv.put(DBHelper.COLUMN_ID_MOVIE,id_movie);
      cv.put(DBHelper.COLUMN_ORIGINAL_TITLE,original_title);
      cv.put(DBHelper.COLUMN_POSTER_PATH,posterPath);
      cv.put(DBHelper.COLUMN_OVERVIEW,overview);
      cv.put(DBHelper.COLUMN_VOTE_AVERAGE,voteAverage);
      cv.put(DBHelper.COLUMN_RELEASE_DATE,releaseDate);
      return database.insert(DBHelper.TABLE_NAME,DBHelper.COLUMN_ID,cv);

    }catch (SQLException e)
    {
      e.printStackTrace();
    }


    return 0;
  }

  public Cursor getAllMoviews()
  {
    String[] allcolumns={DBHelper.COLUMN_ID,DBHelper.COLUMN_ID_MOVIE,DBHelper.COLUMN_ORIGINAL_TITLE,DBHelper.COLUMN_POSTER_PATH,
    DBHelper.COLUMN_OVERVIEW, DBHelper.COLUMN_VOTE_AVERAGE, DBHelper.COLUMN_RELEASE_DATE};

    return database.query(DBHelper.TABLE_NAME,allcolumns,null,null,null,null,null);
  }

  public long delete(String id_movie)
  {
    try
    {

      return database.delete(DBHelper.TABLE_NAME,DBHelper.COLUMN_ID_MOVIE+" =?",new String[]{id_movie});

    }catch (SQLException e)
    {
      e.printStackTrace();
    }

    return 0;
  }

  public boolean ifExists(String id_movie) {
    Cursor res = getAllMoviews();
    int flag = 0;
    while (res.moveToNext()) {
      String id_movies = res.getString(1);
      if (id_movies.equals(id_movie)) {
        flag++;
      }
    }
    if (flag==0) {
      return false;
    } else {
      return true;
    }
  }

}
