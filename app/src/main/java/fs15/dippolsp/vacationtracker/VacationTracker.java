package fs15.dippolsp.vacationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


/**
 * This is the super class for all classes. I am going to use it for the action bar menu.
 * Created by Seth on 10/22/2015.
 */
public abstract class VacationTracker extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_vacation, menu);

        int currentMenuId = getCurrentMenuId();
        //if we have a menu ID, remove that form our menu
        if(currentMenuId != 0){
            menu.removeItem(currentMenuId);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * this is invoked when the user clicks the bucket list menu option
     * @param menuItem
     */
    public void bucketListClicked(MenuItem menuItem) {
        Intent bucketList = new Intent(this, BucketList.class);
        startActivity(bucketList);

    }

    /**
     * this method is invoked when the user clicks the vacations menu option
     * @param menuItem
     */
    public void vacationsClicked(MenuItem menuItem){
        Intent vacationsIntent = new Intent(this, Vacations.class);
        startActivity(vacationsIntent);

    }


    /**
     * this method is invoked when the user clicks the new bucket list menu option
     * @param menuItem
     */
    public void newBucketListClicked(MenuItem menuItem){
        Intent newBucketIntent = new Intent(this, NewBucketList.class);
        startActivity(newBucketIntent);

    }

    /**
     * this method is invoked when the user clicks the new vacation menu option
     * @param menuItem
     */
    public void newVacationClicked(MenuItem menuItem){
        Intent newVacation = new Intent(this, NewVacation.class);
        startActivity(newVacation);

    }


    public abstract int getCurrentMenuId();

}
