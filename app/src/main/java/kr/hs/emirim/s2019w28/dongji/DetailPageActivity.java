package kr.hs.emirim.s2019w28.dongji;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import kr.hs.emirim.s2019w28.dongji.model.Comments;

public class DetailPageActivity extends AppCompatActivity {

    private RecyclerView comment_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
    }
}