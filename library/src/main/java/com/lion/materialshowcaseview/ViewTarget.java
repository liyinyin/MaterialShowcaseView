package com.lion.materialshowcaseview;

import android.graphics.Point;
import android.view.View;

public class ViewTarget implements Target {
	
	public enum CentreArea {
		LEFT_EDGE, LEFT, CENTER, RIGHT, RIGHT_EDGE
	}
	
	public enum RadiusCalcCategory {
		DEFAULT, HALF_OF_WIDTH
	}

	private final View mView;
	private final CentreArea mCentreArea;

	public ViewTarget(View view) {
		this(view, CentreArea.CENTER);
	}
	
	public ViewTarget(View view, CentreArea centreArea) {
		mView = view;
		mCentreArea = centreArea;
	}

	@Override
	public Point getPoint() {
		int[] location = new int[2];
		mView.getLocationInWindow(location);
		
//		int x = location[0];
//		int y = location[1];
//		int width = mView.getWidth();
//		int height = mView.getHeight();
//		int deltaX = width/2;
//		
//		if (mView instanceof TextView && !(mView instanceof Button)) {
//			TextPaint paint = ((TextView) mView).getPaint(); 
//			int textRealWidth = (int) paint.measureText(((TextView) mView).getText().toString());
//			
//			Drawable[] drawable = ((TextView)mView).getCompoundDrawables();
//			if (drawable[0] != null) {
//				if (!((TextView) mView).getText().toString().isEmpty()) {
//					deltaX = (int)(1.3*height);
//				}
//			} else {
//				deltaX = Math.min(textRealWidth, width) / 2;
//			}
//		}
//		
//		x += deltaX;
//		y += height/2;
		
		int x = location[0] + mView.getMeasuredWidth() / 2;
        int y = location[1] + mView.getMeasuredHeight() / 2;
		
		return new Point(x, y);
	}

	@Override
	public int getRadius() {

		int radius = 200;

		if (mView != null) {
			
			//radius = Math.max(mView.getMeasuredWidth(), mView.getMeasuredHeight()) / 2;

			int width = mView.getMeasuredWidth();
			int height = mView.getMeasuredHeight();
			if (width > height) {
				if (width > 2 * height) {
					radius = height;
				} else {
					radius = width / 2;
				}
			} else {
				if (height > 2 * width) {
					radius = width;
				} else {
					radius = height / 2;
				}
			}
			
			if (mView.getTag() instanceof RadiusCalcCategory) {
				if ((mView.getTag()) == RadiusCalcCategory.HALF_OF_WIDTH) {
					radius = width / 2;
				}
			}
			
//			if (mView instanceof TextView) {
//				Drawable[] drawable = ((TextView)mView).getCompoundDrawables();
//				if (drawable[0] == null && !((TextView)mView).getText().toString().isEmpty()) {
//					TextPaint paint = ((TextView) mView).getPaint(); 
//					int textRealWidth = (int) paint.measureText(((TextView) mView).getText().toString()) + 1;
//					if (textRealWidth < width) {
//						width = textRealWidth;
//					}
//				}
//			}
//			
//			if (width > height) {
//				if (width > 2 * height) {
//					radius = height;
//					scale = 1.3;
//				} else {
//					radius = width / 2;
//				}
//			} else {
//				if (height > 2 * width) {
//					radius = width;
//					scale = 1.3;
//				} else {
//					radius = height / 2;
//				}
//			}
//			
//			radius += 10; // add a 10 pixel padding to circle
		}

		return radius;
	}
	
	@Override
	public Point getFinalPoint(int radius) {
		Point point = getPoint();
		int x = point.x;
		
		int[] location = new int[2];
		mView.getLocationInWindow(location);
		
		if (mCentreArea == CentreArea.LEFT_EDGE) {
			x = location[0];
		} else if (mCentreArea == CentreArea.LEFT) {
			x = location[0] + radius;
		} else if (mCentreArea == CentreArea.RIGHT) {
			x = location[0] + mView.getMeasuredWidth() - radius;
		}
		
		return new Point(x, point.y);
	}
}
