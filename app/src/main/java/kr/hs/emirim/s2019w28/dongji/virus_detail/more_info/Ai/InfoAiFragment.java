package kr.hs.emirim.s2019w28.dongji.virus_detail.more_info.Ai;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import kr.hs.emirim.s2019w28.dongji.R;


public class InfoAiFragment extends Fragment {
    TabLayout tabs;
    Fragment afragment1;
    Fragment afragment2;
    Fragment afragment3;
    Fragment afragment4;
    Fragment selected = null;

    public InfoAiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_info_ai, container, false);

            afragment1 = new aFragment1();
            afragment2 = new aFragment2();
            afragment3 = new aFragment3();
            afragment4 = new aFragment4();


            tabs = mView.findViewById(R.id.tabs);
            tabs.addTab(tabs.newTab().setText("정보"));
            tabs.addTab(tabs.newTab().setText("전파방법"));
            tabs.addTab(tabs.newTab().setText("소독요령"));
            tabs.addTab(tabs.newTab().setText("신고요령"));

            getFragmentManager().beginTransaction().replace(R.id.contatiner, afragment1).commit();

            tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int position = tab.getPosition();
                    if (position == 0) {
                        selected = afragment1;
                    } else if (position == 1) {
                        selected = afragment2;
                    } else if (position == 2) {
                        selected = afragment3;
                    }else if (position == 3) {
                        selected = afragment4;
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