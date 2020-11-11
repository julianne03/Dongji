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

    private Fragment check_start;


    public CheckFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_check, container, false);

        check_start = new CheckStartFragment();

        getFragmentManager().beginTransaction().replace(R.id.question_container, check_start).commit();

        // Inflate the layout for this fragment
        return view;
    }
}