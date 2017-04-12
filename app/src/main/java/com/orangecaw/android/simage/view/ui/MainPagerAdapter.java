package com.orangecaw.android.simage.view.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Pair;

import com.orangecaw.android.simage.App;
import com.orangecaw.android.simage.R;
import com.orangecaw.android.simage.view.ui.search.SearchFragment_;
import com.orangecaw.android.simage.view.ui.archive.ArchiveFragment_;

import java.util.ArrayList;
import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Pair<String, Fragment>> items = new ArrayList<>();

    Context context;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        context = App.getContext();
        items.add(new Pair<>(context.getString(R.string.search), new SearchFragment_()));
        items.add(new Pair<>(context.getString(R.string.archive), new ArchiveFragment_()));
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position).second;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).first;
    }
}