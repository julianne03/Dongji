package kr.hs.emirim.s2019w28.dongji.fragment;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import kr.hs.emirim.s2019w28.dongji.R;

public class Question2 extends Fragment {
    Fragment Question1;
    Fragment Question3;
    private RadioGroup radioGroup;
    private Button next2_btn;

    public String result2;
    private String result;
    private String returnB;


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

        radioGroup = (RadioGroup) viewGroup.findViewById(R.id.radioGroup);

        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.radioButton){
                    result2 = "Yes";
                } else if(i == R.id.radioButton2){
                    result2 = "No";
                }
            }
        };


        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);


        next2_btn = (Button) viewGroup.findViewById(R.id.next1_btn);

        next2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(result2 == null){
                    Toast.makeText(getContext(),"설문에 응해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    Bundle bundle = new Bundle();
                    result = getArguments().getString("answer1");
                    if(result=="No"&&result2=="Yes"){
                        returnB = "True";
                    }else{
                        returnB = "False";
                    }
                    bundle.putString("answer2",returnB);
                    Question3.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.question_container, Question3).commit();
                }


            }
        });


        return viewGroup;
    }
}