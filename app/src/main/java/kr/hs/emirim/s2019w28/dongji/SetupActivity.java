package kr.hs.emirim.s2019w28.dongji;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.hs.emirim.s2019w28.dongji.Adapter.PostRecyclerAdapter;
import kr.hs.emirim.s2019w28.dongji.model.Post;
import kr.hs.emirim.s2019w28.dongji.model.User;

public class SetupActivity extends AppCompatActivity {

    private Uri userImageURI = null;
    private CircularImageView user_image;
    private boolean isChanged = false;

    private String user_id;
    private EditText user_nickname;
    private Button complete_btn;
    private Button logout_btn;
    private Button delete_account_btn;
    private ImageView back_btn;
    private ProgressBar setup_progress;

    private RecyclerView helpful_post_list_view;
    private List<Post> post_list;
    private List<User> user_list;
    private PostRecyclerAdapter postRecyclerAdapter;

    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        user_image = findViewById(R.id.user_image);
        user_nickname = findViewById(R.id.user_nickname);
        complete_btn = findViewById(R.id.complete_btn);
        logout_btn = findViewById(R.id.logout_btn);
        back_btn = findViewById(R.id.set_up_back_btn);
        delete_account_btn = findViewById(R.id.delete_user);
        setup_progress = findViewById(R.id.setup_progress);

        helpful_post_list_view = findViewById(R.id.helpful_post_list_view);

        post_list = new ArrayList<>();
        user_list = new ArrayList<>();


        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        String name = task.getResult().getString("name");
                        String image = task.getResult().getString("image");

                        userImageURI = Uri.parse(image);
                        user_nickname.setText(name);

                        RequestOptions placeholderRequest = new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.ic_baseline_account_circle_24);

                        Glide.with(SetupActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(user_image);

                    } else {
                        Toast.makeText(SetupActivity.this,"데이터가 존재하지 않습니다.",Toast.LENGTH_LONG).show();
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(SetupActivity.this, "Firestore Retrieve Error: " + error, Toast.LENGTH_LONG).show();
                }
            }
        });

        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setup_progress.setVisibility(View.VISIBLE);
                final String user_name = user_nickname.getText().toString();

                if(!TextUtils.isEmpty(user_name) && userImageURI!=null) {
                    if(isChanged) {
                        user_id = firebaseAuth.getCurrentUser().getUid();

                        final StorageReference image_path = storageReference.child("profile_images").child(user_id+".jpg");
                        UploadTask uploadTask = image_path.putFile(userImageURI);
                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if(!task.isSuccessful()) {
                                    throw task.getException();
                                }

                                return image_path.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    storeFireStore(task, user_name);
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(SetupActivity.this, "Image Error: " + error, Toast.LENGTH_LONG).show();

                                }
                            }
                        });

                    }
                } else {
                    Toast.makeText(SetupActivity.this,"빈 칸을 채워주세요!",Toast.LENGTH_LONG).show();
                }
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SetupActivity.this);
                builder.setTitle("로그아웃").setMessage("정말 로그아웃 하시겠습니까?");

                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseAuth.signOut();
                        Toast.makeText(SetupActivity.this,"로그아웃 되셨습니다!",Toast.LENGTH_LONG).show();
                        Intent loginIntent = new Intent(SetupActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
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

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delete_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Users").document(user_id).delete();
                firebaseAuth.getCurrentUser().delete();
                Toast.makeText(SetupActivity.this,"회원 탈퇴하셨습니다ㅠㅠ",Toast.LENGTH_LONG).show();

            }
        });

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.e("setup","if 1 pass");
                    if (ContextCompat.checkSelfPermission(SetupActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Log.e("setup","if 2 pass");
                        ActivityCompat.requestPermissions(SetupActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        Log.e("setup","ask");

                    } else {
                        Log.e("setup","success");
                        BringImagePicker();
                    }
                } else {
                    Log.e("setup","success");
                    BringImagePicker();
                }
            }
        });

        //도움되는 글 가져오기
        postRecyclerAdapter = new PostRecyclerAdapter(post_list,user_list);
        helpful_post_list_view.setLayoutManager(new LinearLayoutManager(this));
        helpful_post_list_view.setAdapter(postRecyclerAdapter);


        Query firstQuery = firebaseFirestore.collection("Users/"+user_id+"/Helps");

        firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                Log.e("test","firstQuery OK");

                if(!value.isEmpty()) {

                    post_list.clear();
                    user_list.clear();

                }
                if (error != null) {
                    System.err.println(error);
                }

                for(DocumentChange doc : value.getDocumentChanges()) {

                    if (doc.getType() == DocumentChange.Type.ADDED) {

                        String postId = doc.getDocument().getId();
                        final Post post = doc.getDocument().toObject(Post.class).withId(postId);

                        String reviewUserId = doc.getDocument().getString("user_id");
                        firebaseFirestore.collection("Users").document(reviewUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if(task.isSuccessful()) {
                                    Log.e("test","inventory firebasfirestore 동작 good");
                                    User user = task.getResult().toObject(User.class);

                                    user_list.add(user);
                                    post_list.add(post);
                                    Log.e("test","firebase add good");

                                }
                                postRecyclerAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }

            }
        });
    }

    private void storeFireStore(Task<Uri> task, String user_name) {
        Uri download_uri;

        if (task != null) {
            download_uri = task.getResult();
        } else {
            download_uri = userImageURI;
        }

        Map<String, String> userMap = new HashMap<>();
        userMap.put("name",user_name);
        userMap.put("image",download_uri.toString());

        firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    setup_progress.setVisibility(View.INVISIBLE);
                    Toast.makeText(SetupActivity.this, "변경 내용이 성공적으로 업데이트 되었습니다.", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(SetupActivity.this, MainActivity.class);
                    mainIntent.putExtra("userImageUri",userImageURI);
                    startActivity(mainIntent);
                    finish();
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(SetupActivity.this, "Firestore Error: " + error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void BringImagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(SetupActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                userImageURI = result.getUri();
                user_image.setImageURI(userImageURI);

                isChanged = true;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}