package com.hcmute.it.k20.app.musicplayer.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.hcmute.it.k20.app.musicplayer.fragment.FeedbackFragment;
import com.hcmute.it.k20.app.musicplayer.fragment.PersonalFragment;
import com.hcmute.it.k20.app.musicplayer.fragment.PopularSongFragment;
import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PopularSongFragment();
            case 1:
                return new PersonalFragment();
            case 2:
                return new FeedbackFragment();
            default:
                return new PopularSongFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
