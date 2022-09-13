package it.swipes.app.publisher;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PublisherViewModel extends ViewModel {

    private MutableLiveData imageUrl;

    public MutableLiveData getImageUrl() {
        if (imageUrl == null) {
            imageUrl = new MutableLiveData();
        }
        return imageUrl;
    }
}
