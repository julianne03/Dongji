package kr.hs.emirim.s2019w28.dongji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PostActivity extends AppCompatActivity {
    private FloatingActionButton goNewPost_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        goNewPost_btn = (FloatingActionButton) findViewById(R.id.goNewPost_btn);

        goNewPost_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPost();
            }
        });
    }

    private void goToPost() {
        Intent PostIntent = new Intent(this, NewPostActivity.class);
        startActivity(PostIntent);
    }


}