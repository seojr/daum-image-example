package com.orangecaw.android.simage.view.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.orangecaw.android.simage.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    private ActionBar actionbar;

    @ViewById(R.id.viewpager)
    ViewPager viewPager;

    @ViewById(R.id.tabs)
    TabLayout tabLayout;

    MainPagerAdapter mSectionsPagerAdapter;

    @AfterViews
    void setUp() {
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        setViewPager();

        // default title
        actionbar.setTitle(mSectionsPagerAdapter.getPageTitle(0));
    }

    private void setViewPager() {
        mSectionsPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionbar.setTitle(mSectionsPagerAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

}
