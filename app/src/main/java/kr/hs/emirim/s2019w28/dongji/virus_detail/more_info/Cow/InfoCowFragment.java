package kr.hs.emirim.s2019w28.dongji.virus_detail.more_info.Cow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import kr.hs.emirim.s2019w28.dongji.R;


public class InfoCowFragment extends Fragment {
    TabLayout tabs;
    Fragment cfragment1;
    Fragment cfragment2;
    Fragment cfragment3;
    Fragment cfragment4;
    Fragment selected = null;

    public InfoCowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_info_cow, container, false);

        cfragment1 = new cFragment1();
        cfragment2 = new cFragment2();
        cfragment3 = new cFragment3();
        cfragment4 = new cFragment4();


        tabs = mView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("정보"));
        tabs.addTab(tabs.newTab().setText("전파방법"));
        tabs.addTab(tabs.newTab().setText("소독요령"));
        tabs.addTab(tabs.newTab().setText("신고요령"));

        getFragmentManager().beginTransaction().replace(R.id.contatiner, cfragment1).commit();

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    selected = cfragment1;
                } else if (position == 1) {
                    selected = cfragment2;
                } else if (position == 2) {
                    selected = cfragment3;
                }else if (position == 3) {
                    selected = cfragment4;
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