package com.sikware.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    ImageView ima;
    String msg="drag";
    int x_cord;
    int y_cord;
    private static final String IMAGEVIEW_TAG = "Android Logo";
    private android.widget.RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ima = (ImageView)findViewById(R.id.iv_logo);
        ima.setTag(IMAGEVIEW_TAG);
        ima.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData(v.getTag().toString(),mimeTypes,item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(ima);
                v.startDrag(dragData,myShadow,null,0);//TODO make not deprecated
                return true;
            }
        });
        ima.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch(event.getAction()){
                    case DragEvent.ACTION_DRAG_STARTED:
                        layoutParams = (RelativeLayout.LayoutParams)v.getLayoutParams();
                        Log.d(msg,"Drag Start");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(msg,"Drag Entered");
                        x_cord = (int) event.getX();//might be problems
                        y_cord = (int) event.getY();//and here
                        // ^^^^^ make feilds?
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(msg,"Drag Exit");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        //layoutParams.leftMargin = x_cord;
                        //layoutParams.topMargin = y_cord;
                        //v.setX(x_cord);
                        //v.setY(y_cord);
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d(msg,"Drag Location");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        v.setX(x_cord);
                        v.setY(y_cord);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(msg,"Drag Ended");
                        //do nothing
                        //x_cord = (int) event.getX();
                        //y_cord = (int) event.getY();
                        v.setX(x_cord);
                        v.setY(y_cord);
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.d(msg,"Drop");
                        //do nothing
                        break;
                    default:break;

                }
                return true;
            }
        });

    }
}
