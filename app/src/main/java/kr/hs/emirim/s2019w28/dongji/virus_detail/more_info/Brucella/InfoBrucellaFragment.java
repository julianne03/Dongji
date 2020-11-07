package kr.hs.emirim.s2019w28.dongji.virus_detail.more_info.Brucella;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import kr.hs.emirim.s2019w28.dongji.R;


public class InfoBrucellaFragment extends Fragment {
    TabLayout tabs;
    Fragment bfragment1;
    Fragment bfragment2;
    Fragment bfragment3;
    Fragment bfragment4;
    Fragment selected = null;

    public InfoBrucellaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_info_brucella, container, false);

        bfragment1 = new bFragment1();
        bfragment2 = new bFragment2();
        bfragment3 = new bFragment3();
        bfragment4 = new bFragment4();


        tabs = mView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("정보"));
        tabs.addTab(tabs.newTab().setText("전파방법"));
        tabs.addTab(tabs.newTab().setText("소독요령"));
        tabs.addTab(tabs.newTab().setText("신고요령"));

        getFragmentManager().beginTransaction().replace(R.id.contatiner, bfragment1).commit();

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    selected = bfragment1;
                } else if (position == 1) {
                    selected = bfragment2;
                } else if (position == 2) {
                    selected = bfragment3;
                }else if (position == 3) {
                    selected = bfragment4;
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