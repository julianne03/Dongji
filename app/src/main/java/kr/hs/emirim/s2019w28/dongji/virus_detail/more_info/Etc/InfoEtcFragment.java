package kr.hs.emirim.s2019w28.dongji.virus_detail.more_info.Etc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import kr.hs.emirim.s2019w28.dongji.R;


public class InfoEtcFragment extends Fragment {
    TabLayout tabs;
    Fragment efragment1;
    Fragment efragment2;
    Fragment efragment3;
    Fragment efragment4;
    Fragment selected = null;

    public InfoEtcFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_info_etc, container, false);

        efragment1 = new eFragment1();
        efragment2 = new eFragment2();
        efragment3 = new eFragment3();
        efragment4 = new eFragment4();


        tabs = mView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("정보"));
        tabs.addTab(tabs.newTab().setText("전파방법"));
        tabs.addTab(tabs.newTab().setText("소독요령"));
        tabs.addTab(tabs.newTab().setText("신고요령"));

        getFragmentManager().beginTransaction().replace(R.id.contatiner, efragment1).commit();

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    selected = efragment1;
                } else if (position == 1) {
                    selected = efragment2;
                } else if (position == 2) {
                    selected = efragment3;
                }else if (position == 3) {
                    selected = efragment4;
                }


                getFragmentManager().beginTransaction().replace(R.id.contatiner, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return mView;
    }
}