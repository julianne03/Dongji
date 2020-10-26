package kr.hs.emirim.s2019w28.dongji.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import kr.hs.emirim.s2019w28.dongji.R;

public class CheckFragment extends Fragment {

    private Fragment question1;
    private Fragment question2;
    private Fragment question3;
    private Fragment question4;

    private ImageView number1;
    private ImageView number2;
    private ImageView number3;
    private ImageView number4;

    private Button next1_btn;


    public CheckFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_check, container, false);

        question1 = new Question1();
        question2 = new Question2();
        question3 = new Question3();
        question4 = new Question4();

        number1 = (ImageView)view.findViewById(R.id.number1);
        number2 = (ImageView)view.findViewById(R.id.number2);
        number3 = (ImageView)view.findViewById(R.id.number3);
        number4 = (ImageView)view.findViewById(R.id.number4);

        getFragmentManager().beginTransaction().replace(R.id.question_container, question1).commit();

        // Inflate the layout for this fragment
        return view;
    }
}