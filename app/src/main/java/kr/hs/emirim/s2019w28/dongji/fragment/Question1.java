package kr.hs.emirim.s2019w28.dongji.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import kr.hs.emirim.s2019w28.dongji.CheckActivity;
import kr.hs.emirim.s2019w28.dongji.R;


public class Question1 extends Fragment {
    private Fragment Question2;

    private Button next1_btn;

    public Question1() {
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
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_question1, container, false);

        Question2 = new Question2();
        next1_btn = (Button) viewGroup.findViewById(R.id.next1_btn);

        next1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.question_container, Question2).commit();
            }
        });

        return viewGroup;
    }
}