package com.orangecaw.android.simage.view.ui.archive;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orangecaw.android.simage.R;
import com.orangecaw.android.simage.util.RxEvent;
import com.orangecaw.android.simage.util.PermissionUtil;
import com.orangecaw.android.simage.view.widget.RecyclerViewEmptySupport;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.List;

@EFragment(R.layout.archive_fragment)
public class ArchiveFragment extends Fragment implements ArchiveContract.View {

    private static final int GRID_SPAN_COUNT = 3;

    private static String[] STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_REQUEST = 1;

    @ViewById(R.id.archive_recycler)
    RecyclerViewEmptySupport recyclerView;

    @ViewById(R.id.archive_empty)
    TextView emptyView;

    @ViewById(R.id.archive_progress)
    ProgressBar progressBar;

    @Bean(ArchivePresenter.class)
    ArchiveContract.Presenter presenter;

    @Bean(ArchiveAdapter.class)
    ArchiveAdapter adapter;

    @AfterViews
    void setUp() {
        setRecyclerView();
        registerEvent();
        presenter.setView(this);

        if(!PermissionUtil.hasPermissions(getContext(), STORAGE_PERMISSIONS)) {
            PermissionUtil.requestPermissions(this, STORAGE_PERMISSIONS, PERMISSION_REQUEST);
        } else {
            presenter.loadFiles();
        }

    }

    private void setRecyclerView() {
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), GRID_SPAN_COUNT));
        recyclerView.setAdapter(adapter);
    }

    private void registerEvent() {
        RxEvent.subscribe(file -> addFileFirst(file));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST) {
            for(int result : grantResults) {
                if(result != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            presenter.loadFiles();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onDestroy() {
        presenter.unSubscribe();
        super.onDestroy();
    }

    @Override
    public void setFiles(List<File> files) {
        adapter.setFiles(files);
    }

    private void addFileFirst(File file) {
        adapter.addFile(file, 0);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
