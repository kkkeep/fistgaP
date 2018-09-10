/*
package com.jy.fistga.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.jy.fistga.view.EditeImageView;
import com.t.f.glibrary.R;
import com.t.f.glibrary.util.Constant;
import com.t.f.glibrary.util.MResource;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class GetBitmapActivity extends Activity{
	
	public static final String FILE_PATH = "image_path";
	public static  String TEMP_FILE_NAME = "temp.jpg";
	public static final int REQUEST_CODE_CAMERA = 0x100;
	public static final int REQUEST_CODE_GALLERY = 0x200;
	
	public static final int GET_BITMAP_RESULT_OK = 0x300;
	
	private FrameLayout mFrameLayout;
	private Button mButtonCamera;
	private Button mButtonGallery;
	private Button mButtonOk;
	private String mFilePaht;
	private File mImageFile;
	
	private int mIdCamera;
	private int mIdGallery;
	private int mIdOk;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(MResource.getIdByName(getApplicationContext(), Constant.LAYOUT, "lib_activity_get_bitmap"));
		
		mFilePaht = getIntent().getStringExtra(FILE_PATH);
		
		if(TextUtils.isEmpty(mFilePaht)){
			Toast.makeText(getApplicationContext(), "拍照后保存路径为空", 1).show();
			finish();
			return;
		}
		
		File file = new File(mFilePaht);
		
		if(!file.exists()){
			file.mkdirs();
		}
		TEMP_FILE_NAME = System.currentTimeMillis() + ".jpg";
		mImageFile =new File(mFilePaht,TEMP_FILE_NAME);
		if(mImageFile.exists()){
			mImageFile.delete();
		}
		mIdCamera = MResource.getIdByName(getApplicationContext(), Constant.ID, "btn_getbitmap_from_camera");
		mIdGallery =  MResource.getIdByName(getApplicationContext(), Constant.ID, "btn_getbitmap_from_gallery");
		mIdOk = MResource.getIdByName(getApplicationContext(), Constant.ID, "btn_getbitmap_ok");
		
		mFrameLayout = (FrameLayout) findViewById(MResource.getIdByName(getApplicationContext(), Constant.ID, "fl_clip_zone")); 
		mButtonCamera = (Button) findViewById(mIdCamera);
		mButtonGallery = (Button) findViewById(mIdGallery);
		mButtonOk = (Button) findViewById(mIdOk);
		
		mButtonCamera.setOnClickListener(mClickListener);
		mButtonGallery.setOnClickListener(mClickListener);
		mButtonOk.setOnClickListener(mClickListener);
		
		mButtonCamera.setVisibility(View.VISIBLE);
		mButtonGallery.setVisibility(View.VISIBLE);
		mButtonOk.setVisibility(View.GONE);
		
	}
	
	
	private OnClickListener mClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			if(id == mIdCamera){
				Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Uri u=Uri.fromFile(mImageFile); 
				intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, u); 
				startActivityForResult(intent, REQUEST_CODE_CAMERA); 
			}else if(id == mIdGallery){
				Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
				getAlbum.setType("image/*");
				startActivityForResult(getAlbum, REQUEST_CODE_GALLERY);
			}else if(id == mIdOk){
				Intent intent = new Intent();
				Bitmap bitmap = mClipView.getCircleBitmap();
				File file = new File(mFilePaht, "photo.jpeg");
				FileOutputStream outputStream = null;
				try {
					outputStream = new FileOutputStream(file);
					bitmap.compress(CompressFormat.PNG, 100, outputStream);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}finally{
					if(outputStream != null){
						try {
							outputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				intent.putExtra("data", file.getAbsolutePath());
				setResult(GET_BITMAP_RESULT_OK, intent);
				finish();
			}
		}
	};
	
	*/
/*public void onConfigurationChanged(android.content.res.Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	};*//*

	private EditeImageView mClipView;
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String filePath = null;
			Uri originalUri = null;
			if (requestCode == REQUEST_CODE_CAMERA){
				try {
					String path = MediaStore.Images.Media.insertImage(getContentResolver(),mImageFile.getAbsolutePath(), null, null);
					originalUri =Uri.parse(path);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				filePath = mImageFile.getAbsolutePath();
			}else if(requestCode == REQUEST_CODE_GALLERY){
			    originalUri = data.getData();
              
			    if (originalUri != null && "content".equals(originalUri.getScheme())) {
			    	Cursor cursor = this.getContentResolver().query(originalUri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null);
			    	cursor.moveToFirst();   
			    	filePath = cursor.getString(0);
			    	cursor.close();
			    }
			    else {
			    	filePath = originalUri.getPath();
			    }
			}
			
			mClipView = new EditeImageView(getApplicationContext(),filePath);
			mFrameLayout.addView(mClipView);
			
			mButtonCamera.setVisibility(NewsView.GONE);
			mButtonGallery.setVisibility(NewsView.GONE);
			mButtonOk.setVisibility(NewsView.VISIBLE);
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
}
*/
