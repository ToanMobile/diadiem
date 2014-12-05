package com.DiaDiem.app;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by toan on 3/16/14.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Error extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_error, container, false);

        return rootView;
    }
}
