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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class AddStoryActivity extends AppCompatActivity {
    private ImageView btn_back;
    private ImageView post_story;
    private Button push_btn;
    private Uri story_image_uri = null;
    private String user_id;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private Bitmap compressedImageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();

        btn_back = (ImageView) findViewById(R.id.btn_back);
        post_story = (ImageView) findViewById(R.id.post_story);
        push_btn = (Button) findViewById(R.id.push_btn);

        post_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(700, 512)
                        .setAspectRatio(1, 1)
                        .start(AddStoryActivity.this);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(AddStoryActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        push_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(story_image_uri!=null) {
                    final String randomName = UUID.randomUUID().toString();

                    final StorageReference filepath = storageReference.child("story_images").child(randomName + ".jpg");
                    final UploadTask uploadTask1 = filepath.putFile(story_image_uri);

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

                                File newImageFile = new File(story_image_uri.getPath());

                                try {
                                    compressedImageFile = new Compressor(AddStoryActivity.this)
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
                                        PostMap.put("story_image", downloadUri);
                                        PostMap.put("user_id", user_id);
                                        PostMap.put("timestamp", FieldValue.serverTimestamp());


                                        firebaseFirestore.collection("Storys").add(PostMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                                if (task.isSuccessful()) {
                                                    Toast.makeText(AddStoryActivity.this, "스토리가 추가되었습니다!", Toast.LENGTH_LONG).show();
                                                    Intent mainIntent = new Intent(AddStoryActivity.this, MainActivity.class);
                                                    startActivity(mainIntent);
                                                }
                                            }
                                        });

                                    }
                                });
                            }
                        }
                    });
                }else{
                    Toast.makeText(AddStoryActivity.this,"사진을 넣어주세요!",Toast.LENGTH_LONG).show();
                }
            }

        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                story_image_uri = result.getUri();

                post_story.setImageURI(story_image_uri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


}