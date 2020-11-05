package kr.hs.emirim.s2019w28.dongji.fragment;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import kr.hs.emirim.s2019w28.dongji.R;


public class Question1 extends Fragment {
    private Fragment Question2;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private Button next1_btn;
    public String result1;
    public Question1() {
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_question1, container, false);

        Question2 = new Question2();
        next1_btn = (Button) viewGroup.findViewById(R.id.next2_btn);
        radioButton = (RadioButton) viewGroup.findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) viewGroup.findViewById(R.id.radioButton2);
        radioGroup = (RadioGroup) viewGroup.findViewById(R.id.radioGroup);

        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.radioButton){
                    result1 = "No";
                } else if(i == R.id.radioButton2){
                    result1 = "Yes";
                }
            }
        };


        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);


        next1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(result1 == null){
                    Toast.makeText(getContext(),"설문에 응해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("answer1",result1);
                    Question2.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.question_container, Question2).commit();
                }

            }
        });

        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
