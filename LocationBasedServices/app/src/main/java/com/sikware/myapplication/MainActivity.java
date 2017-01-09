package com.sikware.myapplication;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mLocationClient;
    private TextView addressLabel;
    private TextView locationLabel;
    private Button getLocationBtn;
    private Button disconnectBtn;
    private Button connectBtn;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationLabel = (TextView) findViewById(R.id.locationLabel);
        addressLabel = (TextView) findViewById(R.id.addressLabel);

        getLocationBtn = (Button) findViewById(R.id.getLocation);
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCurrentLocation();
            }
        });

        disconnectBtn = (Button) findViewById(R.id.disconnect);
        disconnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.disconnect();
                locationLabel.setText("Got Disconnected....");
            }
        });

        connectBtn = (Button) findViewById(R.id.connect);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.connect();
                locationLabel.setText("Got Connected....");
            }
        });
        //updated way to handle, need to use GoogleAPIClient instead
        if (mLocationClient == null) {
            mLocationClient = new GoogleApiClient
                    .Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }
    }

    public void displayCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Ooopps we don't have permission for that", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_CODE_ASK_PERMISSIONS);
            return;
            }
        }

        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
        String msg = "Current Location: " +
                Double.toString(currentLocation.getLatitude()) + "," +
                Double.toString(currentLocation.getLongitude());
        locationLabel.setText(msg);
        (new GetAddressTask(this)).execute(currentLocation);
    }

    //lifeCycles
    @Override
    protected void onStart(){
        super.onStart();
        mLocationClient.connect();
        locationLabel.setText("Got Connected....");
    }
    @Override
    protected void onStop(){
        mLocationClient.disconnect();
        super.onStop();
        locationLabel.setText("Got Disconnected....");
    }


    //locationService
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    }
    //@Override
    public void onDisconnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Disconnected, re-Connect now!!!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection suspended, re-Connect now!!!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Conecction failure : "+ connectionResult.getErrorCode(),Toast.LENGTH_SHORT).show();
    }


    //AdressTask.class
    private class GetAddressTask extends AsyncTask<Location, Void, String> {
        Context mContext;
        public GetAddressTask(Context context){
            super();
            mContext=context;
        }
        @Override
        protected void onPostExecute(String address){
            addressLabel.setText(address);
        }
        @Override
        protected String doInBackground(Location... params) {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            Location loc = params[0];
            List<Address> addresses = null;
            try{
                addresses = geocoder.getFromLocation(loc.getLatitude(),loc.getLongitude(), 1);
            }
            catch(IOException i){
                Log.e("LocationSampleActivity","IO Exception getLocation()");
                i.printStackTrace();
                return ("IO Exception trying to get address");
            }
            catch (IllegalArgumentException a){
                String errorString = "Illegal Arguments " +
                        Double.toString(loc.getLatitude()) +
                        Double.toString(loc.getLongitude()) +
                        " passed to address service";
                Log.e("LocationSampleActivitiy", errorString);
                a.printStackTrace();
                return errorString;
            }
            if(addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                String addressText = String.format(
                        "%s, %s, %s,",
                        address.getMaxAddressLineIndex() > 0 ?
                                address.getAddressLine(0) : "",
                        address.getLocality(),
                        address.getCountryName()
                );
                return addressText;
            }
            else{return "No Address Found";}
        }

    }


}
