package kr.hs.emirim.s2019w28.dongji;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import kr.hs.emirim.s2019w28.dongji.Adapter.CommentsRecyclerAdapter;
import kr.hs.emirim.s2019w28.dongji.model.Comments;
import kr.hs.emirim.s2019w28.dongji.model.Post;
import kr.hs.emirim.s2019w28.dongji.model.User;

public class DetailPageActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView comment_list_view;
    private CommentsRecyclerAdapter commentsRecyclerAdapter;
    private List<Comments> commentsList;
    private List<User> user_list;

    private ImageView post_image;
    private TextView post_user_name;
    private CircularImageView post_user_image;
    private TextView post_date;
    private TextView post_title;
    private TextView post_content;
    private EditText comment_field;
    private ImageView comment_send_btn;
    private ImageView delete_btn;

    private ImageView detail_back_btn;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private CollectionReference collectionReference;

    private String post_id;
    private String comment_id;
    private String user_name;
    private String user_image;
    private String current_user_id;
    private String post_user_id;

    private ImageView help_btn;
    private TextView help_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        post_id = getIntent().getStringExtra("post_id");

        init();
        findId();

        detail_back_btn.setOnClickListener(this);
        comment_send_btn.setOnClickListener(this);

        current_user_id = firebaseAuth.getCurrentUser().getUid();
        user_name = getIntent().getStringExtra("user_name");
        user_image = getIntent().getStringExtra("user_image");
        post_user_id = getIntent().getStringExtra("post_user_id");

        //comments recyclerview
        commentsList = new ArrayList<>();
        commentsRecyclerAdapter = new CommentsRecyclerAdapter(commentsList,post_id);
        comment_list_view.setHasFixedSize(true);
        comment_list_view.setLayoutManager(new LinearLayoutManager(this));
        comment_list_view.setAdapter(commentsRecyclerAdapter);

        //User 정보 가져오기
        post_user_name.setText(user_name);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_baseline_account_circle_24);

        Glide.with(getApplicationContext()).applyDefaultRequestOptions(requestOptions).load(Uri.parse(user_image)).thumbnail().into(post_user_image);


        //Post 정보 가져오기
        final String current_user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("Posts").document(post_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    if(task.getResult().exists()) {
                        final String title = task.getResult().getString("post_title");
                        final String image = task.getResult().getString("post_image");
                        final String content = task.getResult().getString("post_content");
                        final Date post_timestamp = task.getResult().getDate("timestamp");

                        try {
                            long milliseconds = post_timestamp.getTime();
                            String dateString = DateFormat.format("yyyy.MM.dd", new Date(milliseconds)).toString();

                            post_date.setText(dateString);

                        } catch (Exception e) {
                            Toast.makeText(DetailPageActivity.this, "Exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        post_title.setText(title);
                        post_content.setText(content);

                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.placeholder(R.drawable.default_image);

                        Glide.with(getApplicationContext()).applyDefaultRequestOptions(requestOptions).load(Uri.parse(image)).thumbnail().into(post_image);

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
                                    comment_id = commentId;

                                    Comments comments = doc.getDocument().toObject(Comments.class);
                                    commentsList.add(comments);
                                    Log.e("comment",comments.toString());
                                    commentsRecyclerAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    }
                });

        //add comments
        comment_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newDocID = collectionReference.document().getId();

                Log.d("comment","newDocID : "+newDocID);

                String comment_message = comment_field.getText().toString();
                if(!TextUtils.isEmpty(comment_message)) {
                    Map<String,Object> commentsMap = new HashMap<>();
                    commentsMap.put("message",comment_message);
                    commentsMap.put("user_id",current_user_id);
                    commentsMap.put("timestamp", FieldValue.serverTimestamp());
                    commentsMap.put("comment_id",newDocID);


                    firebaseFirestore.collection("Posts/"+post_id+"/Comments")
                            .document(newDocID)
                            .set(commentsMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(DetailPageActivity.this, "에러 발생: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }else {
                                        Toast.makeText(DetailPageActivity.this, "댓글이 추가되었습니다!", Toast.LENGTH_LONG).show();
                                        comment_field.setText("");
                                    }
                                }
                            });
                } else {
                    Toast.makeText(DetailPageActivity.this, "댓글을 작성해주세요!", Toast.LENGTH_LONG).show();
                }

            }
        });

        //help btn click
        help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Posts/"+post_id+"/Helps")
                        .document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(!task.getResult().exists()) {
                            firebaseFirestore.collection("Posts")
                                    .document(post_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.getResult().exists()) {

                                        Post post = task.getResult().toObject(Post.class);

                                        Map<String,Object> helpsMap = new HashMap<>();
                                        helpsMap.put("post_title", post.getPost_title());
                                        helpsMap.put("post_content",post.getPost_content());
                                        helpsMap.put("virus_category",post.getVirus_category());
                                        helpsMap.put("post_image",post.getPost_image());
                                        helpsMap.put("user_id",post.getUser_id());
                                        helpsMap.put("timestamp",FieldValue.serverTimestamp());

                                        firebaseFirestore.collection("Posts/"+post_id+"/Helps")
                                                .document(current_user_id).set(helpsMap);

                                        firebaseFirestore.collection("Users/" + current_user_id + "/Helps")
                                                .document(post_id).set(helpsMap);

                                    }
                                }
                            });
                        } else {
                            firebaseFirestore.collection("Posts/"+post_id+"/Helps")
                                    .document(current_user_id).delete();

                            firebaseFirestore.collection("Users/"+current_user_id+"/Helps")
                                    .document(post_id).delete();
                        }
                    }
                });
            }
        });

        if(current_user_id.equals(post_user_id)) {
            delete_btn.setVisibility(View.VISIBLE);
            findViewById(R.id.delete_post_text).setVisibility(View.VISIBLE);
        }
        //delete posts
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test","delete btn 눌려짐");
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPageActivity.this);
                builder.setTitle("글 삭제").setMessage("정말 글을 삭제하시겠습니까?");

                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.e("test","current user id : "+current_user_id);
                        Log.e("test","user name:"+post_user_id);
                        if(current_user_id.equals(post_user_id)) {
                            Log.e("test","if문 들어가짐");
                            firebaseFirestore.collection("Posts").document(post_id)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.e("test","delete success");
                                            Toast.makeText(DetailPageActivity.this,"글을 성공적으로 삭제했습니다!",Toast.LENGTH_LONG).show();
                                            Intent main = new Intent(DetailPageActivity.this,MainActivity.class);
                                            startActivity(main);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DetailPageActivity.this,"글을 삭제하는 도중에 오류가 발생했습니다!",Toast.LENGTH_LONG).show();
                                }
                            });

                            firebaseFirestore.collection("Users/"+current_user_id+"/posts")
                                    .document(post_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.e("post delete","Users의 post 삭제 완료");
                                }
                            });

                        }
                    }
                });

                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                final AlertDialog alertDialog = builder.create();

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    }
                });
                alertDialog.show();


            }
        });


        //help count
        if(current_user_id != null) {
            firebaseFirestore.collection("Posts/"+post_id+"/Helps").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(!value.isEmpty()) {
                        int count = value.size();
                        updateHelpsCount(count);
                    } else {
                        updateHelpsCount(0);
                    }
                }
            });

            //help btn image change
            if(firebaseAuth.getCurrentUser() != null) {
                firebaseFirestore.collection("Posts/"+ post_id + "/Helps").document(current_user_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value.exists()) {
                            help_btn.setImageResource(R.drawable.help_icon_accent);
                        } else {
                            help_btn.setImageResource(R.drawable.help_icon);
                        }
                    }
                });
            }
        }

    }

    private void updateHelpsCount(int count) {
        help_count.setText(count+" help");
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = FirebaseFirestore.getInstance().collection("Posts/"+post_id+"/Comments");
    }

    private void findId() {
        comment_list_view = findViewById(R.id.comment_list_view);
        post_image = findViewById(R.id.post_image_detail);
        post_user_name = findViewById(R.id.detail_post_user);
        post_user_image = findViewById(R.id.detail_user);
        post_date = findViewById(R.id.detail_post_date);
        post_title = findViewById(R.id.post_title_detail);
        post_content = findViewById(R.id.post_content_detail);
        comment_field = findViewById(R.id.comment_field);
        detail_back_btn = findViewById(R.id.detail_back_btn);
        comment_send_btn = findViewById(R.id.comment_send_btn);
        help_btn = findViewById(R.id.detail_help_btn);
        help_count = findViewById(R.id.detail_help_count);
        delete_btn = findViewById(R.id.delete_btn);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back_btn :
                finish();
                break;
            default:
                break;
        }
    }
}