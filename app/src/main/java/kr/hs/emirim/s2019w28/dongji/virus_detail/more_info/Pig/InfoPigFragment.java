package kr.hs.emirim.s2019w28.dongji.virus_detail.more_info.Pig;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import kr.hs.emirim.s2019w28.dongji.R;


public class InfoPigFragment extends Fragment {
    TabLayout tabs;
    Fragment pfragment1;
    Fragment pfragment2;
    Fragment pfragment3;
    Fragment pfragment4;
    Fragment selected = null;

    public InfoPigFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_info_pig, container, false);

        pfragment1 = new pFragment1();
        pfragment2 = new pFragment2();
        pfragment3 = new pFragment3();
        pfragment4 = new pFragment4();


        tabs = mView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("정보"));
        tabs.addTab(tabs.newTab().setText("전파방법"));
        tabs.addTab(tabs.newTab().setText("소독요령"));
        tabs.addTab(tabs.newTab().setText("신고요령"));

        getFragmentManager().beginTransaction().replace(R.id.contatiner, pfragment1).commit();

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    selected = pfragment1;
                } else if (position == 1) {
                    selected = pfragment2;
                } else if (position == 2) {
                    selected = pfragment3;
                }else if (position == 3) {
                    selected = pfragment4;
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