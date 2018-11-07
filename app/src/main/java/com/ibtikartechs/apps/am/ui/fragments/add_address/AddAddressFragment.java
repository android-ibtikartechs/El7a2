package com.ibtikartechs.apps.am.ui.fragments.add_address;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.ibtikartechs.apps.am.MvpApp;
import com.ibtikartechs.apps.am.R;
import com.ibtikartechs.apps.am.data.DataManager;
import com.ibtikartechs.apps.am.data.adapters.CityAutoCompleteAdapter;
import com.ibtikartechs.apps.am.data.adapters.PlaceAutoCompleteAdapter;
import com.ibtikartechs.apps.am.data.models.AddressModel;
import com.ibtikartechs.apps.am.ui.activities.base.BaseFragment;
import com.ibtikartechs.apps.am.ui_utilities.CustomAutoCompleteTextView;
import com.ibtikartechs.apps.am.ui_utilities.CustomFontEditText;
import com.ibtikartechs.apps.am.ui_utilities.CustomFontTextView;
import com.ibtikartechs.apps.am.ui_utilities.WorkaroundMapFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAddressFragment extends BaseFragment implements AddAddressMvpView, OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, PlaceAutoCompleteAdapter.OnAutoLocationItemClickListner, CityAutoCompleteAdapter.OnAutoGovernItemClickListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Place place;
    boolean mLocationPermissionGranted = false;
    final int LOCATION_PERMISION_REQUIST_CODE = 103;
    final String TAG = AddAddressFragment.class.getSimpleName();
    GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private float DEFAULT_ZOOM = 15f;
    private PlaceAutoCompleteAdapter autoCompleteAdapter;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));
    private GeoDataClient mGeoDataClient;
    String lastLatitude = "";
    String lastLongitude = "";
    Handler mHandler;
    ScrollView mScrollView;
    OnNextStepListener onNextStepListener;
    @BindView(R.id.tv_address)
    CustomFontEditText etMapAddress;

    @BindView(R.id.et_search)
    AutoCompleteTextView etSearch;
    @BindView(R.id.ic_gps)
    ImageView icGps;
    @BindView(R.id.etName)
    CustomFontEditText etName;
    @BindView(R.id.etPhone)
    CustomFontEditText etPhone;
    @BindView(R.id.etPostNumber)
    CustomFontEditText etPostNumber;
    @BindView(R.id.btnNextStep)
    Button btnNextStep;
    @BindView(R.id.governrote_auto_tex_view_sign_up)
    CustomAutoCompleteTextView autoTexGovernrote;

    @BindView(R.id.city_auto_tex_view_sign_up)
    CustomAutoCompleteTextView autoTexCity;

    private ProgressDialog pDialog;

    CityAutoCompleteAdapter cityAutoCompleteAdapterForAreas;


    String governId = "";
    String cityId = "";

    AddAddressPresenter presenter;


    public AddAddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAddressFragment newInstance(String param1, String param2, OnNextStepListener onNextStepListener) {
        AddAddressFragment fragment = new AddAddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.onNextStepListener = onNextStepListener;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        DataManager dataManager = ((MvpApp) getActivity().getApplication()).getDataManager();
        presenter = new AddAddressPresenter(dataManager);
        presenter.onAttach(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_add_address, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pDialog = new ProgressDialog(getActivity(),ProgressDialog.THEME_HOLO_DARK);
        pDialog.setCancelable(false);


        etName.setText(presenter.getUserData().getName());
        etPhone.setText(presenter.getUserData().getMobNum());

        CityAutoCompleteAdapter cityAutoCompleteAdapter = new CityAutoCompleteAdapter(getActivity(), true);
        cityAutoCompleteAdapter.setOnAutoLocationItemClickListner(this);
        autoTexGovernrote.setAdapter(cityAutoCompleteAdapter);

        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String address = etMapAddress.getText().toString();
                String mobNum = etPhone.getText().toString();
                String postNumber = etPostNumber.getText().toString();
                presenter.addAddress(name,cityId, postNumber, lastLatitude, lastLongitude, mobNum, address);
            }
        });
        getLocationPermisions();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getDeviceLocation();
        setupSearchBox();

        // TODO 6 if you want to mark current location with blue marker
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        // TODO 7 to hide the button that make the map at the center of current location if you need a search interface
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        Point x_y_points = new Point((getChildFragmentManager().findFragmentById(R.id.map)).getView().getWidth()/2, (getChildFragmentManager().findFragmentById(R.id.map)).getView().getHeight()/2);
        LatLng latLng =
                mMap.getProjection().fromScreenLocation(x_y_points);
        Log.d(TAG, "centerLocation lat: "+ latLng.latitude);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Point x_y_points = new Point((getChildFragmentManager().findFragmentById(R.id.map)).getView().getWidth()/2, (getChildFragmentManager().findFragmentById(R.id.map)).getView().getHeight()/2);
                LatLng latLg =
                        mMap.getProjection().fromScreenLocation(x_y_points);
                Log.d(TAG, "centerLocation lat: "+ latLg.latitude);
            }
        });



        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Point x_y_points = new Point(((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getView().getWidth()/2, ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getView().getHeight()/2);
                final LatLng latLg =
                        mMap.getProjection().fromScreenLocation(x_y_points);
                Log.d(TAG, "centerLocation lat: "+ latLg.latitude);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        List<Address> addresses;
                        Geocoder geocoder = new Geocoder(getContext());
                        try {

                            addresses = geocoder.getFromLocation(latLg.latitude, latLg.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            if (addresses.size()!=0) {
                                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                                etMapAddress.setText(address);
                                Double latt = addresses.get(0).getLatitude();
                                Log.d("TAG", "SubAdminArea: " + addresses.get(0).getAdminArea());
                                Log.d("TAG", "SubAdminArea: " + addresses.get(0).getSubAdminArea());
                                Log.d("TAG", "CountryName: " + addresses.get(0).getCountryName());
                                Log.d("TAG", "FeatureName: " + addresses.get(0).getFeatureName());
                                Log.d("TAG", "Locality: " + addresses.get(0).getLocality());
                                Log.d("TAG", "SubLocality: " + addresses.get(0).getSubLocality());
                                Log.d("TAG", "PostalCode: " + addresses.get(0).getPostalCode());
                                Log.d("TAG", "Premises: " + addresses.get(0).getPremises());
                                Log.d("TAG", "Thoroughfare: " + addresses.get(0).getThoroughfare());
                                Log.d("TAG", "SubThoroughfare: " + addresses.get(0).getSubThoroughfare());
                                Log.d("TAG", "Url: " + addresses.get(0).getUrl());
                                lastLatitude = latt.toString();
                                Log.d(TAG, "run: "+"latt = "+ lastLatitude);
                                Double longitude = addresses.get(0).getLongitude();
                                lastLongitude = longitude.toString();
                            }
                            else
                                etMapAddress.setText("عفوا لا يتوفر عنوان لهذه المنطقة");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onAutoLocationItemClicked(String placeId) {
        getPlace(placeId);
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the current device location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            if (mLocationPermissionGranted)
            {
                com.google.android.gms.tasks.Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: location found");
                            Location currentLocation = (Location) task.getResult();

                            if (currentLocation!=null )
                                moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location");
                        }
                        else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(getActivity(), "unable to find current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation: SecurityException" + e);
        }

    }

    private void moveCamera (LatLng latLng, float zoom, String title)
    {
        Log.d(TAG, "moveCamera: to lat : " + latLng.latitude + ", long : " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));



        // TODO 12 to hide keyboard when moving camera
        hideKeyboard();
    }

    public void hideKeyboard() {
        View view = getActivity().findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void getPlace (String placeId) {
        mGeoDataClient.getPlaceById(placeId).addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {
                    PlaceBufferResponse places = task.getResult();
                    place = places.get(0);
                    Log.i(TAG, "Place found: " + place.getLatLng().toString());
                    moveCamera(place.getLatLng(),DEFAULT_ZOOM,"My Location");
                    Log.d(TAG, "onAutoLocationItemClicked: "+place.getName());
                    etSearch.setText(place.getAddress());
                    etSearch.dismissDropDown();
                    places.release();
                } else {
                    Log.e(TAG, "Place not found.");
                }
            }
        });
    }

    private void getLocationPermisions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), permissions[i]) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISION_REQUIST_CODE);
            }
        }
        if (mLocationPermissionGranted)
            initMap();
    }

    private void initMap() {
        WorkaroundMapFragment mapFragment = (WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mScrollView = (ScrollView) getActivity().findViewById(R.id.sv_container);
        mapFragment.getMapAsync(AddAddressFragment.this);
        ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

    }

    private void setupSearchBox ()
    {
        // Todo 14 to activate autocomplete
        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);
        autoCompleteAdapter = new PlaceAutoCompleteAdapter(getContext(),mGeoDataClient, LAT_LNG_BOUNDS,null);
        autoCompleteAdapter.setOnAutoLocationItemClickListner(this);
        etSearch.setAdapter(autoCompleteAdapter);



        etSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_NEXT ||
                        keyEvent.getAction() == KeyEvent.ACTION_DOWN ||
                        keyEvent.getAction() == KeyEvent.KEYCODE_ENTER)
                {
                    geoLocate();
                    return true;
                }


                return false;
            }
        });

        // TODO 12 gps icon to get device location again
        icGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });

    }

    private void geoLocate() {
        Log.d(TAG, "geoLocate: ");
        String searchQuery = etSearch.getText().toString();
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocationName(searchQuery, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (list.size()>0)
        {
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: " + address.toString());

            // TODO 10 Move camera to the location (lat & lang)
            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM, address.getAddressLine(0));

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISION_REQUIST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    initMap();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onAutoLocationItemClicked(String placeId, String name, boolean isGov) {
        if (isGov) {
            autoTexGovernrote.setText(name);
            governId = placeId;
            autoTexGovernrote.dismissDropDown();
            autoTexCity.setEnabled(true);
            cityAutoCompleteAdapterForAreas = new CityAutoCompleteAdapter(getActivity(), false);
            cityAutoCompleteAdapterForAreas.setGovId(placeId);
            cityAutoCompleteAdapterForAreas.setOnAutoLocationItemClickListner(this);
            autoTexCity.setAdapter( cityAutoCompleteAdapterForAreas);
        }
        else {
            autoTexCity.setText(name);
            cityId = placeId;
            autoTexCity.dismissDropDown();
        }
        hideKeyboard();
    }

    @Override
    public void showToast(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void refreshLocationPermision() {
        getLocationPermisions();
    }

    @Override
    public void showProgressDialog(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!pDialog.isShowing()) {
                    pDialog.setMessage(msg);
                    pDialog.show();
                }
            }
        });
    }

    @Override
    public void hideProgressDialog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        });

    }

    @Override
    public void passAddress(final AddressModel addressModel) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onNextStepListener.onNextStepclicked(addressModel);
            }
        });

    }


    public interface OnNextStepListener {
        public void onNextStepclicked(AddressModel addressModel);
    }

    public void setOnNextStepListener(OnNextStepListener onNextStepListener)
    {
        this.onNextStepListener = onNextStepListener;
    }

}
