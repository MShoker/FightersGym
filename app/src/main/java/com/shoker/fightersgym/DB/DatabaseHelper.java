package com.shoker.fightersgym.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shoker.fightersgym.Exercise;
import com.shoker.fightersgym.Food;
import com.shoker.fightersgym.Training;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private  Context mContext;
    private static String DB_NAME = "exercisedb.db";
    private  String DB_PATH = "/data/data/com.shoker.fightersgym/databases/";

    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;

    private boolean mNeedUpdate = false;


    public static final String TABLE_NAME = "exercise";
    private static final String KEY_EXERCIES_ID = "id";
    private static final String KEY_EXERCIES_NAME = "name";
    private static final String KEY_EXERCIES_MUSCLE = "sort";
    private static final String KEY_EXERCIES_RID = "rid";
    private static final String KEY_EXERCIES_EQUIPMENT = "equip";

    public static final String TABLE_NAME2 = "user_training";
    private static final String KEY_TRAINING_ID = "id";
    public static final String KEY_TRAINING_NAME = "name";
    public static final String KEY_TRAINING_DATE = "date";
    public static final String KEY_TRAINING_TIME = "time";
    public static final String KEY_TRAINING_NUMBER_EXERCIES = "n_exercise";
    public static final String KEY_TRAINING_EXERCISES_ID = "exercies_ids";

    public static final String TABLE_NAME3 = "food";
    private static final String KEY_FOOD_CAT = "cat";
    public static final String KEY_FOOD_NAME = "name";
    public static final String KEY_FOOD_ENERGY = "energy";

     public DatabaseHelper(Context context) throws IOException {
            super(context, DB_NAME, null, DB_VERSION);
            this.mContext = context;
            boolean dbexist = checkdatabase();
            if (dbexist) {
                opendatabase();
            } else {
                createdatabase();
                opendatabase();
            }

        }

        public void createdatabase() throws IOException {
            boolean dbexist = checkdatabase();
            if (!dbexist) {
                this.getReadableDatabase();
                try {
                    copydatabase();
                } catch (Exception e) {
                }
            }
        }
        private boolean checkdatabase() {
            boolean checkdb = false;
            try {
                String myPath = DB_PATH + DB_NAME;
                File dbfile = new File(myPath);
                //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
                checkdb = dbfile.exists();
            } catch (SQLiteException e) {
                Log.d("error","error2");
            }

            return checkdb;
        }
        private void copydatabase() throws IOException {

            InputStream myinput = mContext.getAssets().open(DB_NAME);
            String outfilename = DB_PATH + DB_NAME;
            OutputStream myoutput = new FileOutputStream(outfilename);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myinput.read(buffer)) > 0) {
                myoutput.write(buffer, 0, length);
            }
            myoutput.flush();
            myoutput.close();
            myinput.close();

        }

        public void opendatabase() throws SQLException {
            String mypath = DB_PATH + DB_NAME;
            mDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
        }


        public synchronized void close() {
            if (mDataBase != null) {
                mDataBase.close();
            }
            super.close();
        }

    public List<Exercise> getAllExercies() {
        List<Exercise> exercises = new ArrayList<>();
        Cursor cursor = mDataBase.rawQuery( "select * from "+TABLE_NAME, null );
        try {
            if (cursor.moveToFirst()) {
                do {
                    Exercise ex = new Exercise();
                    ex.setId(cursor.getInt(cursor.getColumnIndex(KEY_EXERCIES_ID)));
                    ex.setName(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_NAME)));
                    ex.setSort(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_MUSCLE)));
                    ex.setRID(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_RID)));
                    ex.setEquipment(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_EQUIPMENT)));
                    exercises.add(ex);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return exercises;
    }

    public List<Exercise> getFilteredExercies(String col ,String selection){
        List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " where "+col+" = '" +selection + "'";
        Cursor cursor = mDataBase.rawQuery( query, null );
        try {
            if (cursor.moveToFirst()) {
                do {
                    Exercise ex = new Exercise();
                    ex.setId(cursor.getInt(cursor.getColumnIndex(KEY_EXERCIES_ID)));
                    ex.setName(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_NAME)));
                    ex.setSort(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_MUSCLE)));
                    ex.setRID(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_RID)));
                    ex.setEquipment(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_EQUIPMENT)));
                    exercises.add(ex);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return exercises;
    }

    public List<Exercise> getExtenderFilteriedExercies(String muscle ,String equip){
        List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT * FROM exercise where sort = '"+muscle+"' and equip ='"+equip+"' ";
        Cursor cursor = mDataBase.rawQuery( query, null );
        try {
            if (cursor.moveToFirst()) {
                do {
                    Exercise ex = new Exercise();
                    ex.setId(cursor.getInt(cursor.getColumnIndex(KEY_EXERCIES_ID)));
                    ex.setName(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_NAME)));
                    ex.setSort(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_MUSCLE)));
                    ex.setRID(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_RID)));
                    ex.setEquipment(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_EQUIPMENT)));
                    exercises.add(ex);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return exercises;
    }

    public List<Exercise> getCertinExercise (String [] ex_id){
        List<Exercise> list = new ArrayList<>();

        Cursor cursor =mDataBase.query(TABLE_NAME,null,"id IN (" + makePlaceholders(ex_id.length) + ")",ex_id,null,null,null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Exercise ex = new Exercise();
                    ex.setId(cursor.getInt(cursor.getColumnIndex(KEY_EXERCIES_ID)));
                    ex.setName(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_NAME)));
                    ex.setSort(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_MUSCLE)));
                    ex.setRID(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_RID)));
                    ex.setEquipment(cursor.getString(cursor.getColumnIndex(KEY_EXERCIES_EQUIPMENT)));
                    list.add(ex);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return list;
    }

    String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    public boolean insertTraining (String name, String date, String time, int nofexercies,String exercieses_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TRAINING_NAME, name);
        contentValues.put(KEY_TRAINING_DATE, date);
        contentValues.put(KEY_TRAINING_TIME, time);
        contentValues.put(KEY_TRAINING_NUMBER_EXERCIES, nofexercies);
        contentValues.put(KEY_TRAINING_EXERCISES_ID, exercieses_id);
        mDataBase.insert(TABLE_NAME2, null, contentValues);
        return true;
    }

    public boolean updateTraining (Integer id,String name, String date, String time, int nofexercies,String exercieses_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TRAINING_NAME, name);
        contentValues.put(KEY_TRAINING_DATE, date);
        contentValues.put(KEY_TRAINING_TIME, time);
        contentValues.put(KEY_TRAINING_NUMBER_EXERCIES, nofexercies);
        contentValues.put(KEY_TRAINING_EXERCISES_ID, exercieses_id);
        db.update(TABLE_NAME2, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public List<Training> getAllTrainings() {
        List<Training> trainings = new ArrayList<>();
        Cursor cursor = mDataBase.rawQuery( "select * from "+TABLE_NAME2, null );

        try {
            if (cursor.moveToFirst()) {
                do {
                    Training tr = new Training();
                    tr.setId(cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_ID)));
                    tr.setName(cursor.getString(cursor.getColumnIndex(KEY_TRAINING_NAME)));
                    tr.setDate(cursor.getString(cursor.getColumnIndex(KEY_TRAINING_DATE)));
                    tr.setTime(cursor.getString(cursor.getColumnIndex(KEY_TRAINING_TIME)));
                    tr.setNumberOfExercises(cursor.getInt(cursor.getColumnIndex(KEY_TRAINING_NUMBER_EXERCIES)));
                    tr.setExercisesId(cursor.getString(cursor.getColumnIndex(KEY_TRAINING_EXERCISES_ID)));
                    trainings.add(tr);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return trainings;
    }

    public boolean insertFood (String cat, String name, int enegy) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_FOOD_CAT, cat);
        contentValues.put(KEY_EXERCIES_NAME, name);
        contentValues.put(KEY_FOOD_ENERGY, enegy);
        mDataBase.insert(TABLE_NAME3, null, contentValues);
        return true;
    }


    public List<Food> getFoods(String cat) {
        List<Food> foods = new ArrayList<>();
        Cursor cursor = mDataBase.rawQuery( "select * from "+TABLE_NAME3+" WHERE cat ='"+cat+"'", null );

        try {
            if (cursor.moveToFirst()) {
                do {
                    Food fo = new Food();
                    fo.setName(cursor.getString(cursor.getColumnIndex(KEY_FOOD_NAME)));
                    fo.calories=cursor.getInt(cursor.getColumnIndex(KEY_FOOD_ENERGY));
                    foods.add(fo);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return foods;
    }

    public int energySum(String cat) {
        int energy=0;
        Cursor cursor = mDataBase.rawQuery( "select * from "+TABLE_NAME3+" WHERE cat ='"+cat+"'", null );

        try {
            if (cursor.moveToFirst()) {
                do {
                    energy += cursor.getInt(cursor.getColumnIndex(KEY_FOOD_ENERGY));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return energy;
    }

    public void deleteFoods(){
        mDataBase.execSQL("delete from "+ TABLE_NAME3);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}