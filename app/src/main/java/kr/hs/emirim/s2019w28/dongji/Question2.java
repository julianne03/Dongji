package kr.hs.emirim.s2019w28.dongji;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Question2 extends Fragment {

    CheckActivity checkActivity;

    private Button next2_btn;

    public Question2() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        checkActivity = (CheckActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        checkActivity = null;
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
        next2_btn = (Button) viewGroup.findViewById(R.id.next2_btn);

        next2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkActivity.changeFragment(3);
            }
        });

        return viewGroup;
    }
}