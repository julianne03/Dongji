package kr.hs.emirim.s2019w28.dongji.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import kr.hs.emirim.s2019w28.dongji.R;

public class Question2 extends Fragment {
    Fragment Question1;
    Fragment Question3;
    private Button next2_btn;
    private Button back1_btn;

    public Question2() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_question2, container, false);
        Question1 = new Question1();
        Question3 = new Question3();

        next2_btn = (Button) viewGroup.findViewById(R.id.next2_btn);
        back1_btn = (Button) viewGroup.findViewById(R.id.back1_btn);

        back1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.question_container, Question1).commit();
            }
        });
        next2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.question_container, Question3).commit();
            }
        });

        return viewGroup;
    }
}