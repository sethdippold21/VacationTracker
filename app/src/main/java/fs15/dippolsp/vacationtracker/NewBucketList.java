package fs15.dippolsp.vacationtracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import fs15.dippolsp.dto.BucketListDTO;
import fs15.dippolsp.dto.OfflineBucketListDAO;
import fs15.dippolsp.dto.OfflineVacationDAO;

/**
 * Class for an activity to add a new bucket list item
 * Created by Seth on 10/27/2015.
 */
public class NewBucketList extends VacationTracker {

    private TextView txtTripName;
    private TextView txtDescription;
    private TextView txtCity;
    private TextView txtState;
    private TextView txtCountry;
    public static final int IMAGE_GALLERY_REQUEST = 77;
    private Uri imageUri;
    private ImageView imgPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bucket_list);

        // find the textviews
        txtTripName = (TextView) findViewById(R.id.txtTripName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtState = (TextView) findViewById(R.id.txtState);
        txtCountry = (TextView) findViewById(R.id.txtCountry);
        imgPicture = (ImageView) findViewById(R.id.imgPicture);
    }

    /**
     * return to bucket list and update
     * @param v view
     */
    public void btnAddBucketConfirm(View v){
        String tripName = txtTripName.getText().toString();
        String description = txtDescription.getText().toString();
        String city = txtCity.getText().toString();
        String state = txtState.getText().toString();
        String country = txtCountry.getText().toString();
        //String pictureUri = imageUri.toString();

        // make a new dto and set values
        BucketListDTO bucketListDTO = new BucketListDTO();

        bucketListDTO.setTripName(tripName);
        bucketListDTO.setDescription(description);
        bucketListDTO.setCity(city);
        bucketListDTO.setState(state);
        bucketListDTO.setCountry(country);
        //12-27-2016 - comment this out for now. was crashing here
        //bucketListDTO.setPictureUri(pictureUri);

        //insert the values into the database using the dao
        OfflineBucketListDAO offlineBucketListDAO = new OfflineBucketListDAO(this);
        offlineBucketListDAO.insert(bucketListDTO);

        // return the user to the main bucket list
        Intent intent = new Intent(this, BucketList.class);
        startActivity(intent);
    }

    /**
     * return to bucketlist without update
     * @param v view
     */
    public void btnCancelClicked(View v){
        Intent intent = new Intent(this, BucketList.class);
        startActivity(intent);
    }


    /**
     * This sends the user to the gallery to choose a photo of their vacation.
     * @param v view
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



    /**
     * returns the menu id for this activity
     * @return id
     */
    @Override
    public int getCurrentMenuId() {
        return R.id.newBucketList;
    }

}
