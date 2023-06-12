package com.shahruie.www.gohome;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;



public class Fullscreen extends Dialog implements
View.OnClickListener {

public Fullscreen(Context con,Bitmap bi) {
	super(con);
	this.c=con;
	this .bi=bi;
}

public Context c;
public TextView close;
ImageView im;
Bitmap bi;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
requestWindowFeature(Window.FEATURE_NO_TITLE);
setContentView(R.layout.fullscreenxml);
im=(ImageView)findViewById(R.id.imfull);
im.setImageBitmap(bi);
close=(TextView)findViewById(R.id.tclose);
close.setOnClickListener(this);

}
@Override
public void onClick(View arg0) {
// TODO Auto-generated method stub
switch (arg0.getId()) {
case R.id.tclose:
	dismiss();
	break;
default:
	break;
}

}
}

