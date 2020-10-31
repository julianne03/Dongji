package kr.hs.emirim.s2019w28.dongji;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.hs.emirim.s2019w28.dongji.Adapter.CommentsRecyclerAdapter;
import kr.hs.emirim.s2019w28.dongji.model.Comments;
import kr.hs.emirim.s2019w28.dongji.model.User;

public class DetailPageActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView comment_list_view;
    private CommentsRecyclerAdapter commentsRecyclerAdapter;
    private List<Comments> commentsList;
    private List<User> user_list;

    private ImageView post_image;
    private TextView post_title;
    private TextView post_content;
    private EditText comment_field;
    private ImageView comment_send_btn;

    private ImageView detail_back_btn;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String post_id;
    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        init();
        findId();

        detail_back_btn.setOnClickListener(this);
        comment_send_btn.setOnClickListener(this);

        current_user_id = firebaseAuth.getCurrentUser().getUid();
        post_id = getIntent().getStringExtra("post_id");
        Log.e("test",post_id);

        //comments recyclerview
        commentsList = new ArrayList<>();
        commentsRecyclerAdapter = new CommentsRecyclerAdapter(commentsList);
        comment_list_view.setHasFixedSize(true);
        comment_list_view.setLayoutManager(new LinearLayoutManager(this));
        comment_list_view.setAdapter(commentsRecyclerAdapter);

        //Post 정보 가져오기
        final String current_user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("Posts").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    if(task.getResult().exists()) {
                        final String name = task.getResult().getString("name");
                        final String image = task.getResult().getString("image");
                    }
                }
            }
        });


        //Comments 가져오기
        firebaseFirestore.collection("Posts/"+post_id+"/Comments")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (!value.isEmpty()) {

                            if (error != null) {
                                System.err.println(error);
                            }

                            for (DocumentChange doc : value.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    String commentId = doc.getDocument().getId();
                                    Comments comments = doc.getDocument().toObject(Comments.class);
                                    commentsList.add(comments);
                                    commentsRecyclerAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    }
                });

        comment_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment_message = comment_field.getText().toString();
                Map<String,Object> commentsMap = new HashMap<>();
                commentsMap.put("message",comment_message);
                commentsMap.put("user_id",current_user_id);
                commentsMap.put("timestamp", FieldValue.serverTimestamp());

                firebaseFirestore.collection("Posts/" + post_id + "/Comments").add(commentsMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(DetailPageActivity.this, "에러 발생: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }else {
                            comment_field.setText("");
                        }
                    }
                });
            }
        });

    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void findId() {
        comment_list_view = findViewById(R.id.comment_list_view);
        post_image = findViewById(R.id.post_image_detail);
        post_title = findViewById(R.id.post_title_detail);
        post_content = findViewById(R.id.post_content_detail);
        comment_field = findViewById(R.id.comment_field);
        detail_back_btn = findViewById(R.id.detail_back_btn);
        comment_send_btn = findViewById(R.id.comment_send_btn);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back_btn :
                finish();
        }
    }
}