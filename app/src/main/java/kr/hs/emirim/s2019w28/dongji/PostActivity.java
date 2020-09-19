package kr.hs.emirim.s2019w28.dongji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PostActivity extends AppCompatActivity {
    private FloatingActionButton goNewPostbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        goNewPostbtn = (FloatingActionButton) findViewById(R.id.goNewPost_btn);

        goNewPostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewPost();
            }
        });

    }

    private void goToNewPost() {
        Intent loginIntent = new Intent(this, NewPostActivity.class);
        startActivity(loginIntent);
    }
}