package fs15.dippolsp.dto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * To create the vacations database
 * Created by Seth on 10/22/2015.
 */
public class OfflineVacationDAO extends SQLiteOpenHelper {


    public static final String VACATIONS = "VACATIONS";
    public static final String CACHE_ID = "CACHE_ID";
    public static final String TRIP_NAME = "TRIP_NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String START_DATE = "START_DATE";
    public static final String END_DATE = "END_DATE";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String PICTUREURI = "PICTUREURI";


    public OfflineVacationDAO(Context ctx){
        super(ctx,"vacations.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create sql command and table
        String createVacations = "CREATE TABLE " + VACATIONS + " ( " + CACHE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 TRIP_NAME + " TEXT, " + DESCRIPTION + " TEXT, " + START_DATE + " TEXT, " + END_DATE + " TEXT, " +
                LATITUDE + " TEXT, " + LONGITUDE + " TEXT, " + PICTUREURI + " TEXT)";
        db.execSQL(createVacations);
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
     * This inserts a vacation into the database
     * @param vacation all of the values rolled into one dto
     */
    public void insert(VacationDTO vacation){
        // create our content values
        ContentValues cv = new ContentValues();

        // put the user entered words into the content values
        cv.put(TRIP_NAME, vacation.getTripName());
        cv.put(DESCRIPTION, vacation.getDescription());
        cv.put(START_DATE, vacation.getStartDate());
        cv.put(END_DATE, vacation.getEndDate());
        cv.put(LATITUDE, vacation.getLatitude());
        cv.put(LONGITUDE, vacation.getLongitude());
        cv.put(PICTUREURI, vacation.getPictureUri());

        // insert content values into the database
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(VACATIONS, TRIP_NAME, cv);
        db.close();
    }

    /**
     * this returns all of the database values in a list of dtos
     * @return
     */
    public List<VacationDTO> getAllVacations(){
        List<VacationDTO> vacationList = new ArrayList<VacationDTO>();
        String selectQuery = "SELECT * FROM " + VACATIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                //retrieving all of the values from the database
                VacationDTO vacation = new VacationDTO();

                vacation.setTripName(cursor.getString(1));
                vacation.setDescription(cursor.getString(2));
                vacation.setStartDate(cursor.getString(3));
                vacation.setEndDate(cursor.getString(4));
                vacation.setLatitude(cursor.getString(5));
                vacation.setLongitude(cursor.getString(6));
                vacation.setPictureUri(cursor.getString(7));

                //adding vacation to list
                vacationList.add(vacation);

            }
            while(cursor.moveToNext());

        }
        cursor.close();
        return vacationList;
    }

    /**
     * this fuction searches the list and of vacations
     * i havent used this yet
     * @param searchTerm term that is searched for
     * @return
     */
    public List<VacationDTO> search(String searchTerm) {
        String sql = "SELECT " + TRIP_NAME + ", " + DESCRIPTION + ", " + START_DATE + ", "
                + END_DATE + ", " + LONGITUDE + ", " + LATITUDE
                + " FROM " + VACATIONS + " WHERE "
                + TRIP_NAME + " LIKE '%" + searchTerm + "%' OR "
                + DESCRIPTION + " LIKE '%" + searchTerm + "%' OR "
                + START_DATE + " LIKE '%" + searchTerm + "%' OR "
                + END_DATE + " LIKE '%" + searchTerm + "%' OR "
                + LONGITUDE + " LIKE '%" + searchTerm + "%' OR "
                + LATITUDE + " LIKE '%" + searchTerm + "%'";

        //execute sql
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        //declare collection that will hold results
        ArrayList<VacationDTO> allVacations = new ArrayList<VacationDTO>();

        //iterate over results
        List<VacationDTO> searchVacation = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                //get string and put into dto
                VacationDTO vacationDTO = new VacationDTO();
                vacationDTO.setTripName(cursor.getString(0));
                vacationDTO.setDescription(cursor.getString(1));
                vacationDTO.setStartDate(cursor.getString(2));
                vacationDTO.setEndDate(cursor.getString(3));
                vacationDTO.setLongitude(cursor.getString(4));
                vacationDTO.setLatitude(cursor.getString(5));
                vacationDTO.setPictureUri(cursor.getString(6));

                //add to search results
                searchVacation = new ArrayList<>();
                searchVacation.add(vacationDTO);

                cursor.moveToNext();
            }
            cursor.close();

        }
        return searchVacation;
    }





}
