package com.ufsj.madeira.projetoic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by JV on 23/09/2017.
 */
public class TabAdapter extends FragmentStatePagerAdapter {
    private String[] nomesTabs = {"CONVERSAS","CONTATOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                //fragment = new ConversasFragment();
                break;
            case 1:
                //fragment = new ContatosFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return nomesTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return nomesTabs[position];
    }
}

