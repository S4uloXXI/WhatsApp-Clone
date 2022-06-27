package com.s4ulo.whatsappclone.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.gms.dynamic.IFragmentWrapper;

public class TabAdapter extends FragmentStatePagerAdapter {
    private String [] tituloAbas = {"CONVERSAS", "CONTATOS"};



    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position){
            case 0 :
               // fragment = new ConversasFragment();
                break;
            case 1 :
              //  fragment = new contatosFragment();
                break;
        }
        return fragment;

    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }


}
