package com.orangecaw.android.simage.view.ui.search;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orangecaw.android.simage.R;
import com.orangecaw.android.simage.data.Image;
import com.orangecaw.android.simage.util.PermissionUtil;
import com.orangecaw.android.simage.view.widget.RecyclerViewEmptySupport;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.search_fragment)
@OptionsMenu(R.menu.search_menu)
public class SearchFragment extends Fragment implements SearchContract.View {

    private static final int GRID_SPAN_COUNT = 3;

    private static String[] STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_REQUEST = 1;

    @ViewById(R.id.search_recycler)
    RecyclerViewEmptySupport recyclerView;

    @ViewById(R.id.search_empty)
    TextView emptyView;

    @ViewById(R.id.search_progress)
    ProgressBar progressBar;

    @Bean(SearchAdapter.class)
    SearchAdapter adapter;

    @Bean(SearchPresenter.class)
    SearchContract.Presenter presenter;

    @AfterViews
    void setUp() {
        presenter.setView(this);
        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), GRID_SPAN_COUNT));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(image -> {
            if(!PermissionUtil.hasPermissions(getContext(), STORAGE_PERMISSIONS)) {
                PermissionUtil.requestPermissions(this, STORAGE_PERMISSIONS, PERMISSION_REQUEST);
                presenter.waitDownload(image);
            } else {
                presenter.download(image);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        presenter.watchSearchView(searchView);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST) {
            for(int result : grantResults) {
                if(result != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            presenter.download();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onDestroy() {
        presenter.unSubscribe();
        super.onDestroy();
    }

    @UiThread
    @Override
    public void setImages(List<Image> images) {
        adapter.setImages(images);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToast(@StringRes int stringRes) {
        Toast.makeText(getContext(), stringRes, Toast.LENGTH_SHORT).show();
    }

}
