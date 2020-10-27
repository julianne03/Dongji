package kr.hs.emirim.s2019w28.dongji.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import kr.hs.emirim.s2019w28.dongji.CheckActivity;
import kr.hs.emirim.s2019w28.dongji.R;

public class Question3 extends Fragment {

    Fragment Question4;
    private Button next3_btn;

    public Question3() {
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_question3, container, false);

        Question4 = new Question4();
        next3_btn = (Button) viewGroup.findViewById(R.id.next1_btn);

        next3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.question_container, Question4).commit();
            }
        });

        return viewGroup;
    }
}