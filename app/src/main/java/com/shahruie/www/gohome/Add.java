package com.shahruie.www.gohome;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by MR on 05/10/2017.
 */
public class Add  extends AppCompatActivity implements View.OnClickListener {
    DBHandler db;
   String userChoosenTask,imagepath;
    CoordinatorLayout coordinatorLayout;
    Uri audiouri,imguri;
    byte[] inputData;
    private static final int REQUEST_CAMERA =1;
    private static final int SELECT_FILE =2;
    private static final int SELECT_AUDIO =3;
    private static final String TAG = "MainActivity";
    Button btnloadsound,btnloadimg,play;
    ImageView locimg;
    EditText txtname,txtaddress,txtphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
         db = new DBHandler(this);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        Button btnsave = (Button) toolbarTop.findViewById(R.id.btnsave);
        Button btncancel = (Button) toolbarTop.findViewById(R.id.btncancel);
        audiouri=null;

         locimg = (ImageView) findViewById(R.id.imgloc);
         btnloadsound = (Button) findViewById(R.id.btnloadsound);
         play = (Button) findViewById(R.id.play);
         btnloadimg = (Button) findViewById(R.id.btnloadimg);
        txtname=(EditText)findViewById(R.id.txtname);
        txtaddress=(EditText)findViewById(R.id.txtaddress);
        txtphone=(EditText)findViewById(R.id.txtphone);
        setdefulte();
        play.setEnabled(false);
        play.setOnClickListener(this);
        btnloadsound.setOnClickListener(this);
        btnloadimg.setOnClickListener(this);
        btncancel.setOnClickListener(this);
        btnsave.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                 MediaPlayer mpintro;
                mpintro = MediaPlayer.create(this, Uri.parse(audiouri.toString()));
                //mpintro = MediaPlayer.create(this, Uri.parse(loc(i).getvoice());
               // mpintro.setLooping(true);
                mpintro.start();
                break;
            case R.id.btnloadsound:
                Intent intent_upload = new Intent();
                intent_upload.setType("audio/*");
                intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent_upload,SELECT_AUDIO);
                break;
            case R.id.btnloadimg:
                selectImage();
                break;
            case R.id.btncancel:
                //  comment();
                break;
            case R.id.btnsave:
                save();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                this.finish();
               break;
        }

    }
    private void save(){
        String name,address,phone,audio;
        if(audiouri==null)audio="";
        else audio=audiouri.toString();
        if( txtname.getText().toString()=="")
            name= getResources().getString(R.string.namehint);
        else name=txtname.getText().toString();
        if( txtaddress.getText().toString()=="")
            address= getResources().getString(R.string.addresshint);
        else address=txtaddress.getText().toString();
        if( txtphone.getText().toString()=="")
            phone= getResources().getString(R.string.phonehint);
        else phone=txtphone.getText().toString();
        Addresses loc=new Addresses(1,address,name,phone,imagepath,
                audio);
        db.addaddress(loc);
        Toast.makeText(getApplicationContext(),"success add",Toast.LENGTH_SHORT).show();
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Add.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(Add.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                            cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CAMERA);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
//code for deny
                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                onSelectFromGalleryResult(data);

            }
            else if (requestCode == REQUEST_CAMERA){
                onCaptureImageResult(data);
               // camuri=data.getExtras().get("data").toString();
                //imguri=data.getData();
            }
            else if (requestCode == SELECT_AUDIO){
                audiouri = data.getData();
                if(audiouri!=null) play.setEnabled(true);
                else play.setEnabled(false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void setandsave() {
        if (null != imguri) {
            // Saving to Database...
            if (saveImageInDB(imguri)) {
                showMessage("Image Saved in Database...");
                locimg.setImageURI(imguri);
            }
            // Reading from Database after 3 seconds just to show the message
            /*if (loadImageFromDB()) {
                locimg.setImageURI(imguri);
            }*/
        }
    }
    void showMessage(String message) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    Boolean saveImageInDB(Uri selectedImageUri) {

        try {
           // db.open();
            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
            inputData = Utility.getBytes(iStream);
            //db.insertImage(inputData);
            db.close();
            return true;
        } catch (IOException ioe) {
            Log.e(TAG, "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
           // db.close();
            return false;
        }

    }
    Boolean loadImageFromDB() {
        try {
          //  db.open();
           // byte[] bytes = db.retreiveImageFromDB();
            //db.close();
            // Show Image from DB in ImageView
           // locimg.setImageBitmap(Utility.getImage(bytes));
            return true;
        } catch (Exception e) {
            Log.e(TAG, "<loadImageFromDB> Error : " + e.getLocalizedMessage());
            db.close();
            return false;
        }
    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap reducedSizeBitmap=null;
        Uri selectedImage = data.getData();
        if(selectedImage!=null){
        getContentResolver().notifyChange(selectedImage, null);
         reducedSizeBitmap = getBitmap(selectedImage.getPath());}
        if(reducedSizeBitmap != null) {
            imagepath=selectedImage.getPath();
            locimg.setImageBitmap(reducedSizeBitmap);

        }
    }
    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

            Bitmap b = null;
            in = getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }
    private void onCaptureImageResult(Intent data) {
        Uri selectedimage=null;
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inputData=bytes.toByteArray();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        selectedimage=Uri.fromFile(destination);
        if(selectedimage!=null) {
            locimg.setImageBitmap(thumbnail);
            imagepath=selectedimage.getPath();
        }
    }
    private void setdefulte(){
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.prof);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        inputData = stream.toByteArray();
        locimg.setImageDrawable(drawable);
    }

    }

