package fs15.dippolsp.dto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;



/**
 * to create the bucket list database
 * Created by Seth on 10/27/2015.
 */
public class OfflineBucketListDAO extends SQLiteOpenHelper {

    public static final String BUCKETLIST = "BUCKETLIST";
    public static final String CACHE_ID = "CACHE_ID";
    public static final String TRIP_NAME = "TRIP_NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String CITY = "CITY";
    public static final String STATE = "STATE";
    public static final String COUNTRY = "COUNTRY";
    public static final String PICTUREURI = "PICTUREURI";


    public OfflineBucketListDAO(Context ctx){
        super(ctx,"bucket_list.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the sql string and the table
        String createBucketList = "CREATE TABLE " + BUCKETLIST + " ( " + CACHE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TRIP_NAME + " TEXT, " + DESCRIPTION + " TEXT, " + CITY + " TEXT, " + STATE + " TEXT, " +
                COUNTRY + " TEXT, " + PICTUREURI + " TEXT)";
        db.execSQL(createBucketList);
    }


    /**
     * handles upgrades to the database, not used
     * @param db nothing
     * @param oldVersion nothing
     * @param newVersion nothing
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    /**
     * This inserts a bucket list into the database
     * @param bucketListDTO all of the values rolled into one dto
     */
    public void insert(BucketListDTO bucketListDTO){
        // create our content values
        ContentValues cv = new ContentValues();

        // put the user entered words into the content values
        cv.put(TRIP_NAME, bucketListDTO.getTripName());
        cv.put(DESCRIPTION, bucketListDTO.getDescription());
        cv.put(CITY, bucketListDTO.getCity());
        cv.put(STATE, bucketListDTO.getState());
        cv.put(COUNTRY, bucketListDTO.getCountry());
        cv.put(PICTUREURI, bucketListDTO.getPictureUri());

        // insert content values into the database
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(BUCKETLIST, TRIP_NAME, cv);
        db.close();
    }

    /**
     * this returns all of the bucket list dto objects from the database
     * @return
     */
    public List<BucketListDTO> getAllBucket(){
        List<BucketListDTO> bucketList = new ArrayList<BucketListDTO>();

        String selectQuery = "SELECT * FROM " + BUCKETLIST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                //retrieving all of the values from the database
                BucketListDTO bucketListDTO = new BucketListDTO();

                bucketListDTO.setTripName(cursor.getString(1));
                bucketListDTO.setDescription(cursor.getString(2));
                bucketListDTO.setCity(cursor.getString(3));
                bucketListDTO.setState(cursor.getString(4));
                bucketListDTO.setCountry(cursor.getString(5));
                bucketListDTO.setPictureUri(cursor.getString(6));

                //adding bucket to list
                bucketList.add(bucketListDTO);

            }
            while(cursor.moveToNext());

        }
        cursor.close();
        return bucketList;
    }




}
