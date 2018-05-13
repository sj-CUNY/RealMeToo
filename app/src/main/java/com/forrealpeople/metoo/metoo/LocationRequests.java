package com.forrealpeople.metoo.metoo;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Shweta on 5/12/2018.
 */

public class LocationRequests extends Activity implements LocationListener{

    FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    LocationRequest mLocationRequest ;

    LocationManager locationManager;
    Location myLocation = new Location("NETWORK_PROVIDER");
    boolean mRequestingLocationUpdates = true;
    int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;
    int REQUEST_CHECK_SETTINGS = 2;

    public LocationRequests(final Activity parent)
    {
       if(!checkLocationPermission(parent))
           return;

        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(parent);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(parent, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                myLocation = location;
                            }
                        }
                    });

        createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();

        SettingsClient client = LocationServices.getSettingsClient(parent);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                getMyLocation(parent);
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(parent,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
                builder.addLocationRequest(mLocationRequest);

        mLocationCallback=new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    myLocation = null;
                }
                for (Location location : locationResult.getLocations()) {
                    myLocation = location;                }
            };
        };
    }

    protected void createLocationRequest()
    {
        mLocationRequest =   LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void onProviderEnabled(String str)
    {

    }

    public void onProviderDisabled(String str)
    {

    }

    public void onLocationChanged(Location loc)
    {
        myLocation = loc;
    }

    public void onStatusChanged(String str, int i, Bundle b)
    {

    }
    public void resume()
    {

        if (mRequestingLocationUpdates) {
         startLocationUpdates();
        }
    }
    public void startLocationUpdates()
    {
       // if (!checkLocationPermission(this.getParent()))
         //   return;
    try {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }catch (SecurityException se)
    {
        se.printStackTrace();
    }
    }
    public void stopLocationUpdates()
    {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);

    }
    public Location getMyLastKnownLocation(Activity parent)
    {
        if (checkLocationPermission(parent)) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(parent, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null)
                        myLocation = location;
                }
            });
        }
        else
            {
                myLocation = null;
            }
        return myLocation;


    }
    public boolean checkLocationPermission(Activity parent)
    {
        boolean perm = false;
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(parent,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(parent,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.shouldShowRequestPermissionRationale(parent,"@string/rationale");
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(parent,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);

            }
        } else {
            // Permission has already been granted
                perm = true;
        }
        return perm;
    }
    public Location getMyLocation(Activity parent)
    {
        boolean permission = checkLocationPermission(parent);
        if (permission == true)
            myLocation = getMyLastKnownLocation(parent);
        return myLocation;
    }
}
