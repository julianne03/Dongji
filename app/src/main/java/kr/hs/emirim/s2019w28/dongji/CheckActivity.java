package kr.hs.emirim.s2019w28.dongji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import kr.hs.emirim.s2019w28.dongji.fragment.Question1;
import kr.hs.emirim.s2019w28.dongji.fragment.Question2;
import kr.hs.emirim.s2019w28.dongji.fragment.Question3;
import kr.hs.emirim.s2019w28.dongji.fragment.Question4;

public class CheckActivity extends AppCompatActivity {

    private Fragment question1;
    private Fragment question2;
    private Fragment question3;
    private Fragment question4;

    private ImageView number1;
    private ImageView number2;
    private ImageView number3;
    private ImageView number4;

    private Button next1_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        question1 = new Question1();
        question2 = new Question2();
        question3 = new Question3();
        question4 = new Question4();

        number1 = (ImageView)findViewById(R.id.number1);
        number2 = (ImageView)findViewById(R.id.number2);
        number3 = (ImageView)findViewById(R.id.number3);
        number4 = (ImageView)findViewById(R.id.number4);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.question_view, question1);
        fragmentTransaction.commit();

    }

    public void changeFragment(int index) {
        if(index == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.question_view, question2).commit();
            number1.setImageResource(R.drawable.number1);
            number2.setImageResource(R.drawable.number2_selected);
        } else if(index == 3) {
            getSupportFragmentManager().beginTransaction().replace(R.id.question_view, question3).commit();
            number2.setImageResource(R.drawable.number2);
            number3.setImageResource(R.drawable.number3_selected);
        } else if(index == 4) {
            getSupportFragmentManager().beginTransaction().replace(R.id.question_view, question4).commit();
            number3.setImageResource(R.drawable.number3);
            number4.setImageResource(R.drawable.number4_selected);
        }
    }
}