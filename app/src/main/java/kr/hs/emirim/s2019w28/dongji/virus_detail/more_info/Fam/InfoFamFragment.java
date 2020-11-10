package kr.hs.emirim.s2019w28.dongji.virus_detail.more_info.Fam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import kr.hs.emirim.s2019w28.dongji.R;


public class InfoFamFragment extends Fragment {
    TabLayout tabs;
    Fragment ffragment1;
    Fragment ffragment2;
    Fragment ffragment3;
    Fragment ffragment4;
    Fragment selected = null;

    public InfoFamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_info_fam, container, false);

        ffragment1 = new fFragment1();
        ffragment2 = new fFragment2();
        ffragment3 = new fFragment3();
        ffragment4 = new fFragment4();


        tabs = mView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("정보"));
        tabs.addTab(tabs.newTab().setText("전파방법"));
        tabs.addTab(tabs.newTab().setText("농가지침"));
        tabs.addTab(tabs.newTab().setText("백신"));

        getFragmentManager().beginTransaction().replace(R.id.contatiner, ffragment1).commit();

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    selected = ffragment1;
                } else if (position == 1) {
                    selected = ffragment2;
                } else if (position == 2) {
                    selected = ffragment3;
                }else if (position == 3) {
                    selected = ffragment4;
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