package kr.hs.emirim.s2019w28.dongji.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Date;
import java.text.SimpleDateFormat;

import kr.hs.emirim.s2019w28.dongji.R;


public class Nice_Result extends Fragment {
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    private TextView nowDate;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy년 M월 d일");
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
        View view = inflater.inflate(R.layout.fragment_nice__result, container, false);
        nowDate = (TextView) view.findViewById(R.id.nowDate);
        String time = mFormat.format(date);
        nowDate.setText(time);
        return view;
    }
}