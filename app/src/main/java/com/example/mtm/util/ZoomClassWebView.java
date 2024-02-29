package com.example.mtm.util;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

public class ZoomClassWebView extends WebView implements GestureDetector.OnGestureListener {

    private ZoomClassListener2 mListener;

    private GestureDetector gestureDetector;
    private PointF last = new PointF();
    private int mode = NONE;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private static final int CLICK_DISTANCE_THRESHOLD = 5;

    public ZoomClassWebView(Context context) {
        super(context);
        init(context);
    }

    public ZoomClassWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomClassWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        gestureDetector = new GestureDetector(context, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        PointF curr = new PointF(event.getX(), event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                last.set(curr);
                mode = DRAG;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    float deltaX = curr.x - last.x;
                    float deltaY = curr.y - last.y;
                    if (Math.abs(deltaX) > SWIPE_THRESHOLD || Math.abs(deltaY) > SWIPE_THRESHOLD) {
                        // If swipe exceeds threshold, cancel drag mode
                        mode = NONE;
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = NONE;
                break;
            case MotionEvent.ACTION_UP:
                // Check if it's a click event
                if (mode != DRAG && isAClick(last, curr)) {
                    performClick();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (mListener != null) {
            mListener.onSingleTapUp2();
        }


        System.out.println("yaser xxx Single tap detected");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_THRESHOLD) {
            // Vertical swipe
            if (e1.getY() - e2.getY() > SWIPE_THRESHOLD) {
                System.out.println("yaser xxx Swipe up detected");
            } else {
                System.out.println("yaser xxx Swipe down detected");
            }
            return true;
        }
        if (Math.abs(e1.getX() - e2.getX()) > SWIPE_THRESHOLD) {
            // Horizontal swipe
            if (e1.getX() - e2.getX() > SWIPE_THRESHOLD) {

                if (mListener != null) {
                    mListener.onSwipeLeft2();
                }

                System.out.println("yaser xxx Swipe left detected");
            } else {

                if (mListener != null) {
                    mListener.onSwipeRight2();
                }

                System.out.println("yaser xxx Swipe right detected");
            }
            return true;
        }
        return false;
    }

    private boolean isAClick(PointF start, PointF end) {
        return Math.abs(start.x - end.x) < CLICK_DISTANCE_THRESHOLD &&
                Math.abs(start.y - end.y) < CLICK_DISTANCE_THRESHOLD;
    }






    public void setZoomClassListener2(ZoomClassListener2 listener) {
        mListener = listener;
    }



    public interface ZoomClassListener2 {
        void onSwipeRight2();
        void onSwipeLeft2();
        void onSwipeDown2();
        void onSwipeUp2();
        void onSingleTapUp2();
    }

}