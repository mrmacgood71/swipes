package it.swipes.app.news;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Arrays;
import java.util.List;

import it.swipes.app.R;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final List<Fragment> modes;

    public List<Integer> tabsTitles = Arrays.asList(
            R.string.followed_news_mode_title,
            R.string.recommended_news_mode_title
    );

    public ViewPagerAdapter(
            @NonNull final FragmentActivity fragmentActivity,
            final List<Fragment> modes

    ) {
        super(fragmentActivity);
        this.modes = modes;
    }

    public ViewPagerAdapter(
            @NonNull Fragment fragment,
            List<Fragment> modes) {
        super(fragment);
        this.modes = modes;
    }

    public ViewPagerAdapter(
            @NonNull FragmentManager fragmentManager,
            @NonNull Lifecycle lifecycle,
            List<Fragment> modes
    ) {
        super(fragmentManager, lifecycle);
        this.modes = modes;
    }

    @NonNull
    @Override
    public Fragment createFragment(int modeId) {
        return modes.get(modeId);
    }


    @Override
    public int getItemCount() {
        return modes.size();
    }
}
