package kr.hs.emirim.s2019w28.dongji.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import kr.hs.emirim.s2019w28.dongji.R;


public class CallFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, null);

        ImageButton btn_1339 = view.findViewById(R.id.Bogun);
        btn_1339.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1339"));
                startActivity(intent);
            }
        });

        ImageButton btn_054 = view.findViewById(R.id.Nong);
        btn_054.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:054-912-0616"));
                startActivity(intent);
            }
        });

        ImageButton btn_1588 = view.findViewById(R.id.Ga);
        btn_1588.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1588-9060"));
                startActivity(intent);
            }
        });
        return view;
    }
}