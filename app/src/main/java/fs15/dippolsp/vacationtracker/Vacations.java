package fs15.dippolsp.vacationtracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fs15.dippolsp.dto.OfflineVacationDAO;
import fs15.dippolsp.dto.VacationDTO;

/**
 * this activity displays all of the vacations in the database
 * Created by Seth on 10/22/2015.
 */
public class Vacations extends VacationTracker {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacations);

        // Receive the database
        OfflineVacationDAO offlineVacationDAO = new OfflineVacationDAO(this);
        List<VacationDTO> allVacations = offlineVacationDAO.getAllVacations();

        listView = (ListView) findViewById(R.id.listViewVacations);

        //set the List entry as the getString of the dto
        ArrayAdapter<VacationDTO> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allVacations);
        listView.setAdapter(adapter);
    }


    /**
     * this button brings the user to the NewVacation activity
     * @param v
     */
    public void btnNewVacationClicked(View v){
        //implicit intent
        // send the user to the new vacation activity
        Intent newBucketIntent = new Intent();
        newBucketIntent.setAction("android.intent.action.NEW_VACATION");
        startActivity(newBucketIntent);

        //explicit intent
        //Intent intent = new Intent(this, NewVacation.class);
        //startActivity(intent);
    }

    /**
     * returns the menu id for this activity
     * @return
     */
    @Override
    public int getCurrentMenuId() {
        return R.id.vacations;
    }




}
