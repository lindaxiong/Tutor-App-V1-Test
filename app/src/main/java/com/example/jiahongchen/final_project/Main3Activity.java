package com.example.jiahongchen.final_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.ThreadLocalRandom;

public class Main3Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, SensorEventListener {

    Button SignOutBtn;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    GoogleApiClient mGoogleApiClient;

    private Button askBtn;
    private Button tbBtn;
    private TextView question;
    Button locBtn;
    Button todoBtn;

    SensorManager sensorManager;
    private Sensor mAccelerometer;
    private static final float SHAKE_THRESHOLD_GRAVITY = 1.25F;
    private static final int MIN_TIME_BETWEEN_SHAKES = 1500;

    private int mShakeCount = 0;
    private long mLastShakeTime = 0;
    TextView shakeCountTextView;

    Boolean change = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignOutBtn = findViewById(R.id.SignOutButton);
        SignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        askBtn = findViewById(R.id.askBtn);
        askBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main3Activity.this, AskQuestionActivity.class));
            }
        });

        tbBtn = findViewById(R.id.tbBtn);
        tbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main3Activity.this, FindTutorActivity.class));
            }
        });

        locBtn = findViewById(R.id.getDirBtn);
        locBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main3Activity.this, MapsActivity.class));
            }
        });

        todoBtn = findViewById(R.id.todoBtn);
        todoBtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           startActivity(new Intent(Main3Activity.this, QuestionListActivity.class));
                                       }

        });

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        shakeCountTextView = (TextView) findViewById(R.id.shakeCountTextView);


    }

    private void signOut() {
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                startActivity(new Intent(Main3Activity.this, SignInActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(Main3Activity.this, "Connection Fail", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSensorChanged(SensorEvent event) {

        // Add code here to handle what happens when a sensor event occurs.
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - mLastShakeTime) > MIN_TIME_BETWEEN_SHAKES) {

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                double acceleration = Math.sqrt(Math.pow(x, 2) +
                        Math.pow(y, 2) +
                        Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;

                if (change && acceleration > SHAKE_THRESHOLD_GRAVITY) {
//                    mShakeCount++;
//                    shakeCountTextView.setText("Shake Count: " + mShakeCount);
//                    askBtn.setBackgroundColor(colorList[mShakeCount%colorList.length]);
//                    askBtn.setTextColor(colorList[1]);
//                    change = false;
//                    dialog();

//                    Log.d("count", String.valueOf(mShakeCount));
//                    Log.d("length", String.valueOf(lucky.length));

                    mLastShakeTime = curTime;
                }
            }
        }
    }

//    private void dialog() {
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Main3Activity.this);
//        mBuilder.setTitle("Hello");
//        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                change = true;
//            }
//        });
//        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                change = true;
//            }
//        });
//
//
//        AlertDialog alertDialog = mBuilder.create();
//        alertDialog.setCanceledOnTouchOutside(false);
//        alertDialog.show();
//
//    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    public void onResume() {
        super.onResume();
        // Add a line to register the Session Manager Listener
        if (mAccelerometer != null) {
            sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        // Add a line to unregister the Sensor Manager
        sensorManager.unregisterListener(this, mAccelerometer);
        super.onPause();
    }
}
