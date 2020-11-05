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

public class Question4 extends Fragment {
    Fragment Question3;

    Fragment Nice_Result;
    Fragment Bad_Result;
    private RadioGroup radioGroup;
    private Button next4_btn;

    public String r1r2;
    public String r3;
    public String result4;

    public Question4() {
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_question4, container, false);
        Question3 = new Question3();

        Nice_Result = new Nice_Result();
        Bad_Result = new Bad_Result();


        radioGroup = (RadioGroup) viewGroup.findViewById(R.id.radioGroup);

        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.radioButton){
                    result4 = "Yes";
                } else if(i == R.id.radioButton2){
                    result4 = "No";
                }
            }
        };


        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);


        next4_btn = (Button) viewGroup.findViewById(R.id.next4_btn);




        next4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(result4 == null){
                    Toast.makeText(getContext(),"설문에 응해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    Bundle bundle = new Bundle();
                    r1r2 = getArguments().getString("b_answer");
                    r3 = getArguments().getString("answer3");
                    if(r1r2=="True"&&r3=="No" && result4=="Yes"){
                        getFragmentManager().beginTransaction().replace(R.id.question_container, Nice_Result).commit();
                    }else{
                        getFragmentManager().beginTransaction().replace(R.id.question_container, Bad_Result).commit();
                    }

                }

            }
        });



        return viewGroup;
    }
}