package fs15.dippolsp.vacationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import fs15.dippolsp.dto.BucketListDTO;
import fs15.dippolsp.dto.OfflineBucketListDAO;

/**
 * this activity displays all of the bucket list trips in the database
 * Created by Seth on 10/27/2015.
 */
public class BucketList extends VacationTracker {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_list);

        //receive the database
        OfflineBucketListDAO offlineBucketListDAO = new OfflineBucketListDAO(this);
        List<BucketListDTO> allBucket = offlineBucketListDAO.getAllBucket();

        listView = (ListView) findViewById(R.id.bucketListView);

        //set the List entry as the getString of the dto
        ArrayAdapter<BucketListDTO> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allBucket);
        listView.setAdapter(adapter);
    }


    /**
     * this button sends the user to the new bucket list activity
     * @param v
     */
    public void btnNewBucketListClicked(View v){
        //implicit intent
        //sends the user to the new vacation activity
        Intent newBucketIntent = new Intent();
        newBucketIntent.setAction("android.intent.action.NEW_BUCKET_LIST");
        startActivity(newBucketIntent);

        //explicit intent
        //Intent intent = new Intent(this, NewBucketList.class);
        //startActivity(intent);
    }

    /**
     * returns the menu id for this activity
     * @return
     */
    @Override
    public int getCurrentMenuId() {
        return R.id.bucketList;
    }
}
