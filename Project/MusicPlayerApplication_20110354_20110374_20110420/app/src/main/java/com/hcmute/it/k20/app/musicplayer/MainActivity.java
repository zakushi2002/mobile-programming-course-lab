package com.hcmute.it.k20.app.musicplayer;

import android.annotation.SuppressLint;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hcmute.it.k20.app.musicplayer.adapter.ViewPagerAdapter;
import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {


    private ViewPager viewPagerForPersonal;
    private BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPagerForPersonal = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);





        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerForPersonal.setAdapter(viewPagerAdapter);
        viewPagerForPersonal.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_popular_song).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_personal).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_feedback).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_popular_song:
                    viewPagerForPersonal.setCurrentItem(0);
                    break;
                case R.id.menu_personal:
                    viewPagerForPersonal.setCurrentItem(1);
                    break;
                case R.id.menu_feedback:
                    viewPagerForPersonal.setCurrentItem(2);
                    break;
            }
            return true;
        });
    }


}