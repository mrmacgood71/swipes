package it.swipes.app.account.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataModel extends ViewModel {

    private MutableLiveData<String> id;

    private MutableLiveData<String> name;

    private MutableLiveData<String> displayName;

    private MutableLiveData<String> photo;


    public MutableLiveData<String> getId() {
        if (id == null) {
            id = new MutableLiveData<>();
        }
        return id;
    }

    public MutableLiveData<String> getName() {
        if (name == null) {
            name = new MutableLiveData<>();
        }
        return name;
    }

    public MutableLiveData<String> getDisplayName() {
        if (displayName == null) {
            displayName = new MutableLiveData<>();
        }
        return displayName;
    }

    public MutableLiveData<String> getPhoto() {
        if (photo == null) {
            photo = new MutableLiveData<>();
        }
        return photo;
    }
}
