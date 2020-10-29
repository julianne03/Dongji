package kr.hs.emirim.s2019w28.dongji.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import kr.hs.emirim.s2019w28.dongji.AddStoryActivity;
import kr.hs.emirim.s2019w28.dongji.NewPostActivity;
import kr.hs.emirim.s2019w28.dongji.R;

public class HomeFragment extends Fragment {

    private FloatingActionButton add_post;
    private ImageButton add_story;

    public HomeFragment() {
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
        View mView = inflater.inflate(R.layout.fragment_home, container, false);

        add_post = mView.findViewById(R.id.add_post);

        add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPostIntent = new Intent(getContext(), NewPostActivity.class);
                startActivity(newPostIntent);
            }
        });


        add_story = mView.findViewById(R.id.add_story);

        add_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newStoryIntent = new Intent(getContext(), AddStoryActivity.class);
                startActivity(newStoryIntent);
            }
        });


        return mView;
    }
}