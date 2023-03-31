package com.hcmute.it.k20.app.musicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.hcmute.it.k20.app.musicplayer.R;
import com.hcmute.it.k20.app.musicplayer.adapter.PersonalViewPagerAdapter;
import com.hcmute.it.k20.app.musicplayer.widget.CustomViewPager;


public class PersonalFragment extends Fragment {

    private TabLayout tableLayout;
    private CustomViewPager viewPager;
    private View v;
    public PersonalFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_personal, container, false);
        tableLayout = v.findViewById(R.id.personal_tab_layout);
        viewPager = v.findViewById(R.id.personal_viewpager);
        PersonalViewPagerAdapter adapter = new PersonalViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
        tableLayout.setupWithViewPager(viewPager);
        return v;
    }
}