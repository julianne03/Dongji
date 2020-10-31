package kr.hs.emirim.s2019w28.dongji.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import kr.hs.emirim.s2019w28.dongji.R;
import kr.hs.emirim.s2019w28.dongji.virus_detail.AIFragment;
import kr.hs.emirim.s2019w28.dongji.virus_detail.PigFragment;
import kr.hs.emirim.s2019w28.dongji.virus_detail.BrucellaFragment;
import kr.hs.emirim.s2019w28.dongji.virus_detail.CowFragment;
import kr.hs.emirim.s2019w28.dongji.virus_detail.EtcFragment;
import kr.hs.emirim.s2019w28.dongji.virus_detail.FAMFragment;


public class VirusFragment extends Fragment {

    private Fragment AiFragment;
    private Fragment BrucellaFragment;
    private Fragment CowFragment;
    private Fragment EtcFragment;
    private Fragment FAMFragment;
    private Fragment PigFragment;


    private ImageButton btn_ai;
    private ImageButton btn_pig;
    private ImageButton btn_cow;
    private ImageButton btn_cattle;
    private ImageButton btn_brucella;
    private ImageButton btn_etc;

    public VirusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_virus, container, false);
        btn_ai = (ImageButton) view.findViewById(R.id.btn_Ai);
        btn_pig = (ImageButton) view.findViewById(R.id.btn_pig);
        btn_cow = (ImageButton) view.findViewById(R.id.btn_cow);
        btn_cattle = (ImageButton) view.findViewById(R.id.btn_cattle);
        btn_brucella = (ImageButton) view.findViewById(R.id.btn_brucella);
        btn_etc = (ImageButton) view.findViewById(R.id.btn_etc);

        btn_ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AiFragment = new AIFragment();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, AiFragment).commit();
            }
        });

        btn_pig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PigFragment = new PigFragment();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, PigFragment).commit();
            }
        });

        btn_brucella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BrucellaFragment = new BrucellaFragment();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, BrucellaFragment).commit();
            }
        });


        btn_cow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CowFragment = new CowFragment();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, CowFragment).commit();
            }
        });

        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EtcFragment = new EtcFragment();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, EtcFragment).commit();
            }
        });


        btn_cattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FAMFragment = new FAMFragment();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, FAMFragment).commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}