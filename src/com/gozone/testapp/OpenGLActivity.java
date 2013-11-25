package com.gozone.testapp;

import android.opengl.GLSurfaceView;  
import android.os.Bundle;  
import android.app.Activity;  
import android.content.Context;  
import android.view.Menu;
import android.view.MotionEvent;

public class OpenGLActivity extends Activity {  
  
    private GLSurfaceView mGLView;  
    private float mPreviousX = 0;
    private float mPreviousY = 0;
    private GLSurfaceView.Renderer mRenderer;
    
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        mGLView = new MyGLSurfaceView(this);  
        setContentView(mGLView);  
    }  
  
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //getMenuInflater().inflate(R.menu.activity_open_gl, menu);  
        return true;  
    }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        // The following call pauses the rendering thread.  
        // If your OpenGL application is memory intensive,  
        // you should consider de-allocating objects that  
        // consume significant memory here.  
        mGLView.onPause();  
    }  
  
    @Override  
    protected void onResume() {  
        super.onResume();  
        // The following call resumes a paused rendering thread.  
        // If you de-allocated graphic objects for onPause()  
        // this is a good place to re-allocate them.  
        mGLView.onResume();  
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()){
            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2){
                    dx = dx * -1;
                }
                
                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2){
                    dy = dy * -1;
                }
                mRenderer.mAngle += (dx + dy) * TOUCH_SCALE_FACTOR; // = 180.0f / 320
                requestRender();
                mPreviousX = x;
                mPreviousY = y;
                return true;
        }
    }
}  
  
class MyGLSurfaceView extends GLSurfaceView {  
  
    public MyGLSurfaceView(Context context){  
        super(context);  
  
        // Create an OpenGL ES 2.0 context.  
        setEGLContextClientVersion(2);  
  
        // Set the Renderer for drawing on the GLSurfaceView  
        setRenderer(new MyRender());
        
        // Render the view only when there is a change in the drawing data  
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);  

    }
} 