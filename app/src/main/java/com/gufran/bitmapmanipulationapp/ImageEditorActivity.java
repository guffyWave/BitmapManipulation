package com.gufran.bitmapmanipulationapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gufran.bitmapmanipulationapp.dragview.DragController;
import com.gufran.bitmapmanipulationapp.dragview.DragLayer;

public class ImageEditorActivity extends AppCompatActivity {

    private DragController mDragController;   // Object that sends out drag-drop events while a view is being moved.
    private DragLayer mDragLayer;             // The ViewGroup that supports drag-drop.
    private boolean mLongClickStartsDrag = true;    // If true, it takes a long click to start the drag operation.
    // Otherwise, any touch event starts a drag.
    public static final boolean Debugging = false;

    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editor);

        imageView1 = (ImageView) findViewById(R.id.imageView1);

        mDragController = new DragController(this);
        setupViews();


//        int w = 60;
//        int h = 60;
//        int left = 80;
//        int top = 100;
//        DragLayer.LayoutParams lp = new DragLayer.LayoutParams(w, h, left, top);
//        mDragLayer.addView (newView, lp);
    }

    /**
     * Finds all the views we need and configure them to send click events to the activity.
     */
    private void setupViews() {
        DragController dragController = mDragController;

        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
        mDragLayer.setDragController(dragController);
        dragController.addDropTarget(mDragLayer);

        ImageView i1 = (ImageView) findViewById(R.id.imageView1);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("Press and hold to drag an image.");
            }
        });
        i1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!v.isInTouchMode()) {
                    toast("isInTouchMode returned false. Try touching the view again.");
                    return false;
                }
                return startDrag(v);
            }
        });
        i1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mLongClickStartsDrag) return false;
                boolean handledHere = false;
                final int action = event.getAction();
                // In the situation where a long click is not needed to initiate a drag, simply start on the down event.
                if (action == MotionEvent.ACTION_DOWN) {
                    handledHere = startDrag(v);
                    if (handledHere) v.performClick();
                }
                return handledHere;
            }
        });

        String message = mLongClickStartsDrag ? "Press and hold to start dragging."
                : "Touch a view to start dragging.";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Start dragging a view.
     */
    public boolean startDrag(View v) {
        // Let the DragController initiate a drag-drop sequence.
        // I use the dragInfo to pass along the object being dragged.
        // I'm not sure how the Launcher designers do this.
        Object dragInfo = v;
        mDragController.startDrag(v, mDragLayer, dragInfo, DragController.DRAG_ACTION_MOVE);
        return true;
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
