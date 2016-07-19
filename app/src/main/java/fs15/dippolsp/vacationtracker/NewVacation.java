package fs15.dippolsp.vacationtracker;


import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import fs15.dippolsp.dto.OfflineVacationDAO;
import fs15.dippolsp.dto.VacationDTO;

public class NewVacation extends VacationTracker implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    public final static int MILLISECONDS_PER_SECOND = 1000;
    public final static int MINUTE = 60 * MILLISECONDS_PER_SECOND;
    private double longitude;
    private double latitude;
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    private boolean paused;
    private Button btnPause;
    //all of my data values to add to the db
    private TextView lblLongitudeValue;
    private TextView lblLatitudeValue;
    private TextView txtTripName;
    private TextView txtDescription;
    private TextView txtStartDate;
    private TextView txtEndDate;
    public static final int IMAGE_GALLERY_REQUEST = 7;
    private Uri imageUri;
    private ImageView imgPicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vacation);

        //create google api client for the gps
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // initialize location request
        locationRequest = new LocationRequest();

        //set location request intervals
        locationRequest.setInterval(MINUTE);
        locationRequest.setFastestInterval(15 * MILLISECONDS_PER_SECOND);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        // find all of the text views
        btnPause = (Button) findViewById(R.id.btnPauseGPS);

        lblLongitudeValue = (TextView) findViewById(R.id.lblLongitudeValue);
        lblLatitudeValue = (TextView) findViewById(R.id.lblLatitudeValue);
        txtTripName = (TextView) findViewById(R.id.txtTripName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtStartDate = (TextView) findViewById(R.id.txtStartDate);
        txtEndDate = (TextView) findViewById(R.id.txtEndDate);
        imgPicture = (ImageView) findViewById(R.id.imgPicture);
    }

    @Override
    public int getCurrentMenuId() {
        return R.id.newVacation;
    }

    /**
     * This pauses the gps and updates the on screen values
     * @param v
     */
    public void btnPauseGPSClicked(View v){
        // what happens when you click the pause GPS button

        if(paused == false) {
            // we are unpaused. so pause it
            pauseGPS();
            paused = true;
            //Toast.makeText(this, "Paused", Toast.LENGTH_LONG).show();
            btnPause.setText(getString(R.string.lblResumeGPS));
            // time to unpause
        }else{
            resumeGPS();
            paused = false;
            //Toast.makeText(this, "Resumed", Toast.LENGTH_LONG).show();
            // change label on button
            btnPause.setText(getString(R.string.lblPauseGPS));
        }

    }

    /**
     * This collects all of the data from the new vacation screen, puts it in a dto and sends it to the dao
     * @param v
     */
    public void btnAddVacationConfirm(View v){
        // collect the strings from the textviews
        String tripName = txtTripName.getText().toString();
        String description = txtDescription.getText().toString();
        String startDate = txtStartDate.getText().toString();
        String endDate = txtEndDate.getText().toString();
        String longitude1 = String.valueOf(longitude);
        String latitude1 = String.valueOf(latitude);
        String pictureUri = imageUri.toString();

        // make a new DTO and set values
        VacationDTO vacationDTO = new VacationDTO();

        vacationDTO.setTripName(tripName);
        vacationDTO.setDescription(description);
        vacationDTO.setStartDate(startDate);
        vacationDTO.setEndDate(endDate);
        vacationDTO.setLongitude(longitude1);
        vacationDTO.setLatitude(latitude1);
        vacationDTO.setPictureUri(pictureUri);


        //i cant get this to work quite yet
        OfflineVacationDAO offlineVacationDAO = new OfflineVacationDAO(this);
        offlineVacationDAO.insert(vacationDTO);

        // send the user to the main vacation activity
        Intent intent = new Intent(this, Vacations.class);
        startActivity(intent);
    }

    /**
     * This brings the user back to the main activity with no changes made.
     * @param v
     */
    public void btnCancelClicked(View v){
        Intent intent = new Intent(this, Vacations.class);
        startActivity(intent);
    }

    /**
     * This sends the user to the gallery to choose a photo of their vacation.
     * @param v
     */
    public void btnChoosePhotoClicked(View v){
        // invoke the image gallery using an implicit intent
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        // where do we want to find the data?
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        // finally get a  URI representation
        Uri data = Uri.parse(pictureDirectoryPath);

        // set the data and type. get all image types
        photoPickerIntent.setDataAndType(data, "image/*");

        //we will invoke this activity and get something back from it
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }


    /**
     * get uri from gallery and display it to the user
     * @param requestCode request code
     * @param resultCode result code
     * @param data data passed back
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // return image chosen from gallery
        if (requestCode == IMAGE_GALLERY_REQUEST) {
            //everything processed successfully
            if (resultCode == RESULT_OK) {
                // uri = address of image on sd card
                imageUri = data.getData();

                // invoke next activity!!!!!!!!!!!!!!!!!!!!!!!!!!
                //Intent galleryIntent = new Intent("android.intent.action.INFO");
                //galleryIntent.putExtra("ImageUri", imageUri);
            }

            //display image
            InputStream inputStream;
            try {
                // getting an input stream based on the uri of the image
                inputStream = getContentResolver().openInputStream(imageUri);
                //get a bitmap from the stream
                Bitmap image = BitmapFactory.decodeStream(inputStream);

                //show the image to the user
                imgPicture.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // show message that image is unavailable
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }
        }
    }




    @Override
    public void onConnected(Bundle bundle) {
        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        //dont need
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //dont need
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeGPS();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseGPS();
    }


    private void resumeGPS() {
        if(googleApiClient.isConnected()){
            requestLocationUpdates();
        }
    }

    private PendingResult<Status> pauseGPS() {
        return LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
    }


    /**
     * gets the latitude and longitude
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        updateUIForLocation();
    }

    /**
     * updates ui gps
     */
    private void updateUIForLocation() {
        lblLatitudeValue.setText(Double.toString(latitude));
        lblLongitudeValue.setText(Double.toString(longitude));
    }


    /**
     * save the values on the screen before the orientation changes
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        // this saves data for when screen orientation changes
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putDouble(LATITUDE, latitude);
        outState.putDouble(LONGITUDE, longitude);
        // paused doesnt work
        outState.putBoolean("PAUSED", paused);
        // he used this but we dont have??
        //outState.putSerializable("PLANT", plant);

    }


    /**
     * display values in the ui that were saved right before orientation changed.
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // this restores the saved data when screen orientation changes
        super.onRestoreInstanceState(savedInstanceState);
        latitude = savedInstanceState.getDouble(LATITUDE);
        longitude = savedInstanceState.getDouble(LONGITUDE);
        paused = savedInstanceState.getBoolean("PAUSED");
        updateUIForLocation();


    }
}

