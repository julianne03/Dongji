package kr.hs.emirim.s2019w28.dongji;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class NewPostActivity extends AppCompatActivity {

    private EditText post_title;
    private EditText post_content;

    private ImageView post_image;

    private Spinner virus_category;
    private String category_text;

    private Uri post_image_uri = null;

    private ImageView back_btn;
    private Button post_btn;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String current_user_id;

    private Bitmap compressedImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        initial();
        findId();

        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(this, R.array.virus_category, android.R.layout.simple_spinner_dropdown_item);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        virus_category.setAdapter(categoryAdapter);

        virus_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category_text = parent.getItemAtPosition(position).toString();
                Log.e("test",category_text);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(700, 512)
                        .setAspectRatio(1, 1)
                        .start(NewPostActivity.this);


            }
        });

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = post_title.getText().toString();
                final String content = post_content.getText().toString();

                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content) && post_image_uri!=null) {
                    final String randomName = UUID.randomUUID().toString();

                    final StorageReference filepath = storageReference.child("post_images").child(randomName + ".jpg");
                    final UploadTask uploadTask1 = filepath.putFile(post_image_uri);

                    Task<Uri> urlTask = uploadTask1.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return filepath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull final Task<Uri> task) {

                            final String downloadUri = task.getResult().toString();

                            if (task.isSuccessful()) {

                                File newImageFile = new File(post_image_uri.getPath());

                                try {
                                    compressedImageFile = new Compressor(NewPostActivity.this)
                                            .setMaxHeight(30)
                                            .setMaxWidth(30)
                                            .setQuality(2)
                                            .compressToBitmap(newImageFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                uploadTask1.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                        Map<String, Object> PostMap = new HashMap<>();
                                        PostMap.put("post_title", title);
                                        PostMap.put("post_content",content);
                                        PostMap.put("virus_category",category_text);
                                        PostMap.put("post_image_uri",downloadUri);
                                        PostMap.put("user_id", current_user_id);
                                        PostMap.put("timestamp", FieldValue.serverTimestamp());


                                        firebaseFirestore.collection("Posts").add(PostMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                                if(task.isSuccessful()) {
                                                    Toast.makeText(NewPostActivity.this,"글이 추가되었습니다!",Toast.LENGTH_LONG).show();
                                                    Intent mainIntent = new Intent(NewPostActivity.this, MainActivity.class);
                                                    startActivity(mainIntent);


                                                }
                                            }
                                        });

                                        firebaseFirestore.collection("Users/"+current_user_id+"/posts").add(PostMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if(task.isSuccessful()) {
                                                    Log.e("NewPost -> User Post","User 데베에 추가됨.");
                                                }
                                            }
                                        });

                                    }
                        });
                            }
                        }
                    });
                } else {
                    Toast.makeText(NewPostActivity.this,"빈 칸을 채워주세요!",Toast.LENGTH_LONG).show();
                }
                }

            });
    }

    private void findId() {
        post_title = findViewById(R.id.post_title);
        post_content = findViewById(R.id.post_contents);
        post_image = findViewById(R.id.post_image);
        back_btn = findViewById(R.id.back_btn);
        post_btn = findViewById(R.id.post_btn);
        virus_category = findViewById(R.id.virus_category);
    }

    private void initial() {
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        current_user_id = firebaseAuth.getCurrentUser().getUid();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                post_image_uri = result.getUri();

                post_image.setImageURI(post_image_uri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}