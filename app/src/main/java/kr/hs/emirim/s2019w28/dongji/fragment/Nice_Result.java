package kr.hs.emirim.s2019w28.dongji.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.hs.emirim.s2019w28.dongji.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Nice_Result#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Nice_Result extends Fragment {


    public Nice_Result() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_nice__result, container, false);
    }
}