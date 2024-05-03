package com.android.smartevacuation.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.smartevacuation.model.Location;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Location> userLocation = new MutableLiveData<>();
    private MutableLiveData<Location> fireLocation = new MutableLiveData<>();
    private MutableLiveData<Boolean> showPath = new MutableLiveData<>();

    public HomeViewModel() {
        userLocation.setValue(null);
        fireLocation.setValue(null);
        showPath.setValue(false);
    }

    public MutableLiveData<Location> getUserLocation() {
        return userLocation;
    }

    public MutableLiveData<Location> getFireLocation() {
        return fireLocation;
    }

    public MutableLiveData<Boolean> getShowPath() {
        return showPath;
    }
}