package kr.hs.emirim.s2019w28.dongji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import kr.hs.emirim.s2019w28.dongji.Adapter.CommentsRecyclerAdapter;
import kr.hs.emirim.s2019w28.dongji.model.Comments;
import kr.hs.emirim.s2019w28.dongji.model.User;

public class DetailPageActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView comment_list_view;
    private CommentsRecyclerAdapter commentsRecyclerAdapter;
    private List<Comments> commentsList;
    private List<User> user_list;


    private ImageView detail_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        detail_back_btn = findViewById(R.id.detail_back_btn);

        detail_back_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back_btn :
                finish();
        }
    }
}