package com.jy.fistga.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;
import android.media.ExifInterface;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.io.IOException;

public class EditImageView extends View {

	public static final int STATUS_INIT = 1;
	public static final int STATUS_ZOOM_OUT = 2;
	public static final int STATUS_ZOOM_IN = 3;
	public static final int STATUS_MOVE = 4;
	public static final int STATUS_NONE = 5;
	public static final int MAX_SCALE = 4;

	private Bitmap mSourceBitmap;
	private Paint mPaint;
	private Bitmap mContentBitmap;
	private Rect mClipRect;
	private Path mMaskpath;

	private Matrix mMatrix = new Matrix();
	
	private String mFilePath;
	
	private int mTouchSlop;
	private int mWidth;
	private int mHeight;

	private int mMaxTranX;
	private int mMaxTranY;

	private int mMinTranX;
	private int mMinTranY;
	private int mOrientationRotate;

	private int mCurrentStatus;

	private float mRadius;

	private float mInitScale;
	private float mTotalScale = 1;
	private float mTotalTranX;
	private float mTotalTranY;
	private float mMoveDistanceX;
	private float mMoveDistanceY;
	

	public EditImageView(Context context, String bitmapPath) {
		super(context);
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mCurrentStatus = STATUS_INIT;
		mFilePath = bitmapPath;
		mOrientationRotate = getOrientationRotate();
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public void setBitmap(Bitmap bitmap) {
		mSourceBitmap = bitmap;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		mWidth = right - left;
		mHeight = bottom - top;
		mRadius = (float) Math.min(mWidth / 4, mHeight / 4);
		mMatrix = new Matrix();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);
		switch (mCurrentStatus) {
		case STATUS_INIT:
			initBitmap(canvas);
			break;
		case STATUS_ZOOM_IN:
		case STATUS_ZOOM_OUT:
		case STATUS_MOVE:
			buildMatrix();
			break;
		default:
			break;
		}
		drawContentBitmap(canvas);
		drawMask(canvas);

		// canvas.drawBitmap(getCircleBitmap(mContentBitmap), 0, 0, mPaint);
	}

	public Bitmap clipBitmap() {
		int targetWidth = (int) (mRadius * 2);
		int targetHeight = targetWidth;
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(targetBitmap);
		canvas.drawColor(Color.WHITE);
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		Path path = new Path();
		path.addCircle(((float) targetWidth - 1) / 2,
				((float) targetHeight - 1) / 2,
				(Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
				Path.Direction.CCW);
		canvas.clipPath(path);

		int left = (mWidth - targetWidth) / 2;
		int right = left + targetWidth;

		int top = (int) ((mHeight / 2) - (mRadius));
		int bottom = (int) (mHeight / 2 + mRadius);

		Rect src = new Rect(left, top, right, bottom);
		canvas.drawBitmap(mContentBitmap, src, new Rect(0, 0, targetWidth,
				targetHeight), null);

		return targetBitmap;

	}

	/**
	 * 将圆形图片,返回Bitmap
	 * 
	 *            源Bitmap
	 * @return
	 */
	public Bitmap getCircleBitmap() {

		int targetWidth = (int) (mRadius * 2);
		int targetHeight = targetWidth;

		Bitmap output = Bitmap.createBitmap(targetWidth, targetHeight,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();

		int left = (mWidth - targetWidth) / 2;
		int right = left + targetWidth;

		int top = (int) ((mHeight / 2) - (mRadius));
		int bottom = (int) (mHeight / 2 + mRadius);

		Rect src = new Rect(left, top, right, bottom);
		paint.setAntiAlias(true);
		// paint.setColor(color);
		// 画出一个圆
		canvas.drawCircle(((float) targetWidth - 1) / 2,
				((float) targetHeight - 1) / 2,
				(Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
				paint);
		// canvas.translate(-25, -6);
		// 取两层绘制交集,显示上层
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// 将图片画上去
		canvas.drawBitmap(mContentBitmap, src,
				new Rect(0, 0, targetWidth, targetHeight), paint);
		// 返回Bitmap对象
		return output;
	}

	private void drawMask(Canvas canvas) {
		if (mMaskpath == null) {
			mMaskpath = new Path();
			int pointX = mWidth / 2;
			int pointy = mHeight / 2;
			mMaskpath.addCircle((float) pointX, (float) pointy, mRadius,
					Path.Direction.CCW);
			if (mClipRect == null) {
				mClipRect = new Rect();
			}
			mClipRect.set((int) (pointX - mRadius), (int) (pointy - mRadius),
					(int) (pointX + mRadius), (int) (pointy + mRadius));
			mMaxTranX = mClipRect.left;
			mMaxTranY = mClipRect.top;
		}
		canvas.clipPath(mMaskpath, Region.Op.DIFFERENCE);
		canvas.drawColor(Color.parseColor("#B4000000"));
		/*mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(mClipRect, mPaint);*/

	}
	
	private void decodeBitmap(int maxWidth,int maxHeiht){
		BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
		mSourceBitmap = null;
		if (maxWidth == 0 && maxHeiht == 0) {
            decodeOptions.inPreferredConfig = Config.ARGB_8888;
            mSourceBitmap = BitmapFactory.decodeFile(mFilePath, decodeOptions);
        }else{
        	 decodeOptions.inJustDecodeBounds = true;
        	 BitmapFactory.decodeFile(mFilePath, decodeOptions);
        	 int actualWidth = decodeOptions.outWidth;
             int actualHeight = decodeOptions.outHeight;
             
             int desiredWidth = getResizedDimension(maxWidth, maxHeiht,
            		 actualWidth, actualHeight);
     		 int desiredHeight= getResizedDimension(maxHeiht, maxWidth,
     				actualHeight, actualWidth);
     		 
     		 decodeOptions.inJustDecodeBounds = false;
     		 decodeOptions.inSampleSize =
                     findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
     		 
     		 Bitmap tempBitmap  =  BitmapFactory.decodeFile(mFilePath, decodeOptions);
     		 
     		 if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth ||
                     tempBitmap.getHeight() > desiredHeight)) {
                 mSourceBitmap = Bitmap.createScaledBitmap(tempBitmap,
                         desiredWidth, desiredHeight, true);
                 tempBitmap.recycle();
             } else {
            	 mSourceBitmap = tempBitmap;
             }
     		 
     		 
        }
		if(mOrientationRotate != 0){
			Matrix m = new Matrix(); m.postRotate(mOrientationRotate); 
			mSourceBitmap = Bitmap.createBitmap(mSourceBitmap, 0, 0, mSourceBitmap.getWidth(), mSourceBitmap.getHeight(), m, true);
		}
		
		
		
		
	}
	
	private int getOrientationRotate(){
		int deg = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(mFilePath);
			String rotate = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION); 
			int rotateValue = Integer.parseInt(rotate);
			switch (rotateValue) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				deg = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				deg = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				deg = 270;
				break;
			default:
				deg = 0;
				break;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return deg;
			
		
	}
	 private int findBestSampleSize(
	            int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
	        double wr = (double) actualWidth / desiredWidth;
	        double hr = (double) actualHeight / desiredHeight;
	        double ratio = Math.min(wr, hr);
	        float n = 1.0f;
	        while ((n * 2) <= ratio) {
	            n *= 2;
	        }

	        return (int) n;
	    }
	private void initBitmap(Canvas gcanvas) {
	/*	int maxWidth = (mWidth * 5 / 5);
		int maxHeiht = (mHeight * 5 / 5);*/
		
		if(mOrientationRotate  == 90 || mOrientationRotate == 270){
			decodeBitmap(mHeight, mWidth);
		}else{
			decodeBitmap(mWidth, mHeight);
		}
		
		mSourceBitmapWidth = mSourceBitmap.getWidth();
		mSourceBitmapHeight = mSourceBitmap.getHeight();
		mInitScale = 1;
		mTotalScale = mInitScale;
		mMoveDistanceX = (float) ((mWidth - (mSourceBitmapWidth)) / 2);
		mMoveDistanceY = (float) ((mHeight - (mSourceBitmapHeight))/ 2);
		buildMatrix();


		/*int desiredWidth = getResizedDimension(maxWidth, maxHeiht,
				mSourceBitmapWidth, mSourceBitmapHeight);
		int desiredHeight= getResizedDimension(maxHeiht, maxWidth,
				mSourceBitmapHeight, mSourceBitmapWidth);

		double wr = (double) desiredWidth / mSourceBitmapWidth;
		double wh = (double) ddesiredHeight mSourceBitmapHeight;

		mScaleBitmapWidth = desiredWidth;
		mScaleBitmapHeight = dedesiredHeight
		mInitScale = (float) Math.max(wr, wh);
		mTotalScale = mInitScale;
		mMoveDistanceX = (float) ((mWidth - (desiredWidth)) / 2);
		mMoveDistanceY = (float) ((mHeight - (desdesiredHeight))/ 2);*/
		
		
	}

	private void buildMatrix() {
		mMatrix.reset();
		mMatrix.postScale(mTotalScale, mTotalScale);
		mTotalTranX = mTotalTranX + mMoveDistanceX;
		mTotalTranY = mTotalTranY + mMoveDistanceY;
		mMatrix.postTranslate(mTotalTranX, mTotalTranY);
		mScaleBitmapWidth = (int) (mSourceBitmapWidth * mTotalScale);
		mScaleBitmapHeight = (int) (mSourceBitmapHeight * mTotalScale);
	}

	private void drawContentBitmap(Canvas canvas) {
		canvas.drawBitmap(mSourceBitmap, mMatrix, mPaint);

		if (mContentBitmap != null) {
			mContentBitmap.recycle();
			mContentBitmap = null;
		}
		mContentBitmap = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);

		Canvas canvas2 = new Canvas(mContentBitmap);

		canvas2.drawBitmap(mSourceBitmap, mMatrix, mPaint);

	}

	private int getResizedDimension(int maxPrimary, int maxSecondary,
			int actualPrimary, int actualSecondary) {
		if (maxPrimary == 0 && maxSecondary == 0) {
			return actualPrimary;
		}

		if (maxPrimary == 0) {
			double ratio = (double) maxSecondary / (double) actualSecondary;
			return (int) (actualPrimary * ratio);
		}

		if (maxSecondary == 0) {
			return maxPrimary;
		}

		double ratio = (double) actualSecondary / (double) actualPrimary;
		int resized = maxPrimary;
		if (resized * ratio > maxSecondary) {
			resized = (int) (maxSecondary / ratio);
		}
		return resized;
	}

	private float mLastDownX = -1;
	private float mLastDownY = -1;
	private float mX;
	private float mY;
	private int mSourceBitmapWidth;
	private int mSourceBitmapHeight;
	private int mScaleBitmapWidth;
	private int mScaleBitmapHeight;
	private double mLastFingerDis;

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getActionMasked();
		switch (action) {
		case MotionEvent.ACTION_POINTER_DOWN:
			if (event.getPointerCount() == 2) {
				mLastFingerDis = distanceBetweenFingers(event);
				mCurrentStatus = STATUS_NONE;
				mMoveDistanceX = 0;
				mMoveDistanceY = 0;
			}
			break;

		case MotionEvent.ACTION_MOVE:
			if (event.getPointerCount() == 1) {
				mX = event.getX(0);
				mY = event.getY(0);

				if (mLastDownX == -1 && mLastDownY == -1) {
					mLastDownX = mX;
					mLastDownY = mY;
					return true;
				}

				mMoveDistanceX = mX - mLastDownX;
				mMoveDistanceY = mY - mLastDownY;
				 
				mLastDownX = mX;
				mLastDownY = mY;

				mMinTranX = mClipRect.right - mScaleBitmapWidth;
				mMinTranY = mClipRect.bottom - mScaleBitmapHeight;
				
				adjust();
				
				mCurrentStatus = STATUS_MOVE;
				invalidate();
			} else if (event.getPointerCount() == 2) {
				double fingerDis = distanceBetweenFingers(event);
				if (fingerDis > mLastFingerDis) {
					mCurrentStatus = STATUS_ZOOM_OUT;
				} else {
					mCurrentStatus = STATUS_ZOOM_IN;
				}
				double scale = fingerDis / mLastFingerDis;
				mTotalScale = (float) (mTotalScale * scale);

				if (mCurrentStatus == STATUS_ZOOM_OUT) {
					if (mTotalScale > 4) {
						mTotalScale = 4;
					}

					mMoveDistanceX = -(mSourceBitmapWidth * mTotalScale - mScaleBitmapWidth) / 2;
					mMoveDistanceY = -(mSourceBitmapHeight * mTotalScale - mScaleBitmapHeight) / 2;
				} else {

					float scaleW = mSourceBitmapWidth * mTotalScale;
					float scaleH = mSourceBitmapHeight * mTotalScale;

					if (scaleW <= mClipRect.width()) {
						scaleW = mClipRect.width();
						mTotalScale = scaleW / mSourceBitmapWidth;
					}

					if (scaleH <= mClipRect.height()) {
						scaleH = mClipRect.height();
						mTotalScale = scaleH / mSourceBitmapHeight;
					}
					mMoveDistanceX = (mScaleBitmapWidth - scaleW) / 2;
					mMoveDistanceY = (mScaleBitmapHeight - scaleH) / 2;

					mMinTranX = (int) (mClipRect.right - scaleW);
					mMinTranY = (int) (mClipRect.bottom - scaleH);
					
					adjust();
				}

				mLastFingerDis = fingerDis;
				invalidate();
			}

			break;
		case MotionEvent.ACTION_POINTER_UP:
			mLastDownX = -1;
			mLastDownY = -1;
			break;
		case MotionEvent.ACTION_UP:

			mLastDownX = -1;
			mLastDownY = -1;
			break;
		}

		return true;
	}
	
	private void adjust(){
		
		float targetX = (mTotalTranX + mMoveDistanceX);
		if (targetX > mMaxTranX) {
			mMoveDistanceX = mMaxTranX - mTotalTranX;
		} else if (targetX < mMinTranX) {
			mMoveDistanceX = mMinTranX - mTotalTranX;
		}

		float targetY = mTotalTranY + mMoveDistanceY;

		if (targetY > mMaxTranY) {
			mMoveDistanceY = mMaxTranY - mTotalTranY;
		} else if (targetY < mMinTranY) {
			mMoveDistanceY = mMinTranY - mTotalTranY;
		}
	}
	private double distanceBetweenFingers(MotionEvent event) {
		float disX = Math.abs(event.getX(0) - event.getX(1));
		float disY = Math.abs(event.getY(0) - event.getY(1));
		return Math.sqrt(disX * disX + disY * disY);
	}


}
