package kr.hs.emirim.s2019w28.dongji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import kr.hs.emirim.s2019w28.dongji.fragment.CallFragment;
import kr.hs.emirim.s2019w28.dongji.fragment.CheckFragment;
import kr.hs.emirim.s2019w28.dongji.fragment.HomeFragment;
import kr.hs.emirim.s2019w28.dongji.fragment.VirusFragment;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton logout_btn;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private String current_userId;

    private Uri defaultUserImageUri = null;

    private Fragment HomeFragment;
    private Fragment VirusFragment;
    private Fragment CheckFragment;
    private Fragment CallFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home :
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,HomeFragment).commit();
                    return true;
                case R.id.action_virus :
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,VirusFragment).commit();
                    return true;
                case R.id.action_check :
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,CheckFragment).commit();
                    return true;
                case R.id.action_call :
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,CallFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        HomeFragment = new HomeFragment();
        VirusFragment = new VirusFragment();
        CheckFragment = new CheckFragment();
        CallFragment = new CallFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,HomeFragment).commit();

    }

    @Override
    protected void onStart() {
        super.onStart();



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null) {
            sendToLogin();
        } else {
            UserDefaultSetting();
        }
    }

    private void UserDefaultSetting() {
        current_userId = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("Users").document(current_userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().exists()) {
                        storeFireStore(null,"");
                    }
                }
            }
        });

    }

    private void storeFireStore(Task<Uri> task, String defaultUserId) {
        Uri download_uri;

        if (task != null) {
            download_uri = task.getResult();
        } else {
            download_uri = defaultUserImageUri;
        }
        defaultUserId = firebaseAuth.getCurrentUser().getEmail();
        Map<String, String> userMap = new HashMap<>();
        userMap.put("name",defaultUserId);
        userMap.put("image","https://firebasestorage.googleapis.com/v0/b/dongji-2020.appspot.com/o/profile_images%2Fprofile.jpg?alt=media&token=d33d209b-3151-4256-bb21-ea970baf273a");

        current_userId = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("Users").document(current_userId).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (!task.isSuccessful()) {
                    String error = task.getException().getMessage();
                    Toast.makeText(MainActivity.this, "Firestore Error: " + error, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}