package kr.hs.emirim.s2019w28.dongji.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import kr.hs.emirim.s2019w28.dongji.R;


public class CheckStartFragment extends Fragment {

    private Fragment question1;
    private ImageView start_btn;

    public CheckStartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_check_start, container, false);

        start_btn = v.findViewById(R.id.question_start_btn);
        question1 = new Question1();

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.question_container, question1).commit();
            }
        });

        return v;
    }
}