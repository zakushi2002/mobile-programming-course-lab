package com.hcmute.it.k20.app.musicplayer.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.hcmute.it.k20.app.musicplayer.fragment.*;
import org.jetbrains.annotations.NotNull;

public class PersonalViewPagerAdapter extends FragmentStatePagerAdapter {

    public PersonalViewPagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new PlaylistFragment();
        }
        return new SongFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Song";
            case 1:
                return "Playlist";
        }
        return super.getPageTitle(position);
    }
}
