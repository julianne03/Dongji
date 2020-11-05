package kr.hs.emirim.s2019w28.dongji.fragment;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import kr.hs.emirim.s2019w28.dongji.R;

public class Question3 extends Fragment {
    Fragment Question2;
    Fragment Question4;
    private RadioGroup radioGroup;
    private Button next3_btn;
    private Button back2_btn;
    public String result3;


    private String result;


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
        Question2 = new Question2();
        Question4 = new Question4();
        next3_btn = (Button) viewGroup.findViewById(R.id.next3_btn);
        back2_btn = (Button) viewGroup.findViewById(R.id.back2_btn);

        radioGroup = (RadioGroup) viewGroup.findViewById(R.id.radioGroup);

        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.radioButton){
                    result3 = "Yes";
                } else if(i == R.id.radioButton2){
                    result3 = "No";
                }
            }
        };


        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        next3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(result3 == null){
                    Toast.makeText(getContext(),"설문에 응해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    Bundle bundle = new Bundle(2);
                    result = getArguments().getString("answer2");
                    bundle.putString("b_answer",result);
                    bundle.putString("answer3",result3);
                    Question4.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.question_container, Question4).commit();
                }

            }
        });
        back2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.question_container, Question2).commit();
            }
        });

        return viewGroup;
    }
}