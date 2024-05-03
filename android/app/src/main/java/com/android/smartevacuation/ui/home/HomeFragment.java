package com.android.smartevacuation.ui.home;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.smartevacuation.R;
import com.android.smartevacuation.model.Location;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment implements Observer<Location>, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private HomeViewModel homeViewModel;
    private ImageView imageView;
    private Spinner fireSpinner;
    private FloatingActionButton fab;
    private Bitmap hospital;
    private Bitmap fire;
    private Paint paint = new Paint();
    private float fx;
    private float fy;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        imageView = root.findViewById(R.id.imageView2);
        fireSpinner = root.findViewById(R.id.spinnerFireLocation);
        fab = root.findViewById(R.id.fab);
        hospital = BitmapFactory.decodeResource(getResources(), R.drawable.hospital);
        fire = BitmapFactory.decodeResource(getResources(), R.drawable.fire);

        homeViewModel.getFireLocation().observe(getViewLifecycleOwner(), this);
        homeViewModel.getUserLocation().observe(getViewLifecycleOwner(), this);
        homeViewModel.getShowPath().observe(getViewLifecycleOwner(),value -> renderPath(value));

        fireSpinner.setOnItemSelectedListener(this);

        fab.setOnClickListener(this);

        paint.setColor(Color.RED);
        paint.setStrokeWidth(3f);

        fx = hospital.getWidth()/736.0f;
        fy = hospital.getHeight()/565.0f;

        return root;
    }

    @Override
    public void onChanged(Location location) {
        float fx = hospital.getWidth()/736.0f;
        float fy = hospital.getHeight()/565.0f;
        Bitmap tempBitmap = Bitmap.createBitmap(hospital.getWidth(), hospital.getHeight(), hospital.getConfig());
        Canvas tempCanvas = new Canvas(tempBitmap);
        tempCanvas.drawBitmap(hospital, 0, 0, null);

        if (location != null) {
            tempCanvas.drawBitmap(fire, fx*location.getNormalizedX() - 30*fx, fy*location.getNormalizedY() - 30*fy, null);
        }
        imageView.setImageDrawable(new BitmapDrawable(this.getContext().getResources(), tempBitmap));
    }

    private void renderPath(boolean render) {
        if (!render) {
            return;
        }

        Bitmap tempBitmap = Bitmap.createBitmap(hospital.getWidth(), hospital.getHeight(), hospital.getConfig());
        Canvas tempCanvas = new Canvas(tempBitmap);
        tempCanvas.drawBitmap(hospital, 0, 0, null);

        if (homeViewModel.getFireLocation().getValue() != null) {
            Location location = homeViewModel.getFireLocation().getValue();
            tempCanvas.drawBitmap(fire, fx*location.getNormalizedX() - 30*fx, fy*location.getNormalizedY() - 30*fy, null);
        }
        imageView.setImageDrawable(new BitmapDrawable(this.getContext().getResources(), tempBitmap));



        solution(tempCanvas, homeViewModel.getFireLocation().getValue());



        imageView.setImageDrawable(new BitmapDrawable(this.getContext().getResources(), tempBitmap));
    }


    private void drawLine(Canvas c, float startX, float startY, float stopX, float stopY) {
        c.drawLine(startX*fx, startY*fy, stopX*fx, stopY*fy ,paint);
    }

    private void solution(Canvas tempCanvas, Location value) {
        float start_x = 404;
        float start_y = 200;

        if (value.x == 166 && value.y == 33) {
            drawLine(tempCanvas, start_x, start_y, 458,start_y);
            drawLine(tempCanvas, 458, start_y, 458,0);
        }

        if (value.x == 27 && value.y == 168) {
            drawLine(tempCanvas, start_x, start_y, 458,start_y);
            drawLine(tempCanvas, 458, start_y, 458,0);
        }

        if (value.x == 339 && value.y == 183) {
            drawLine(tempCanvas, start_x, start_y, 458,start_y);
            drawLine(tempCanvas, 458,start_y, 458, 310);
            drawLine(tempCanvas, 458, 310, 596,310);
            drawLine(tempCanvas, 596,310,596, 1000);
        }

        if (value.x == 699 && value.y == 117) {
            drawLine(tempCanvas, start_x, start_y, 458,start_y);
            drawLine(tempCanvas, 458,start_y, 458, 310);
            drawLine(tempCanvas, 458, 310, 596,310);
            drawLine(tempCanvas, 596,310,596, 1000);
        }

        if (value.x == 200 && value.y == 304) {
            drawLine(tempCanvas, start_x, start_y, 458,start_y);
            drawLine(tempCanvas, 458,start_y, 458, 310);
            drawLine(tempCanvas, 458, 310, 596,310);
            drawLine(tempCanvas, 596,310,596, 1000);
        }

        if (value.x == 144 && value.y == 352) {
            drawLine(tempCanvas, start_x, start_y, 458,start_y);
            drawLine(tempCanvas, 458,start_y, 458, 310);
            drawLine(tempCanvas, 458, 310, 596,310);
            drawLine(tempCanvas, 596,310,596, 1000);
        }

        if (value.x == 527 && value.y == 367) {
            drawLine(tempCanvas, start_x, start_y, 458,start_y);
            drawLine(tempCanvas, 458, start_y, 458,0);
        }

        if (value.x == 303 && value.y == 546) {
            drawLine(tempCanvas, start_x, start_y, 458,start_y);
            drawLine(tempCanvas, 458, start_y, 458,0);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getSelectedItem().toString();
        Toast.makeText(this.getContext(), "Fire placed at: " + text, Toast.LENGTH_SHORT).show();

        String [] fields = text.split(",");
        int x = Integer.parseInt(fields[0]);
        int y = Integer.parseInt(fields[1]);

        homeViewModel.getFireLocation().setValue(new Location(x,y));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        builder.setMessage("Follow the route to evacuate the building!")
                .setCancelable(false)
                .setPositiveButton("Go!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        homeViewModel.getShowPath().setValue(true);
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("Fire Alert!");
        alert.show();
    }
}