package com.example.user.bingochat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private View mMainview;


    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       mMainview =inflater.inflate(R.layout.fragment_navigation, container, false);
        mDrawerLayout=(DrawerLayout)mMainview.findViewById(R.id.drawer_layout);
        // mToggle=new ActionBarDrawerToggle(NavigationFragment.this,mDrawerLayout,R.string.open,R.string.close);
         mDrawerLayout.addDrawerListener(mToggle);
         return mMainview;
    }

}
