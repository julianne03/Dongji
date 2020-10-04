package kr.hs.emirim.s2019w28.dongji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kr.hs.emirim.s2019w28.dongji.fragment.CallFragment;
import kr.hs.emirim.s2019w28.dongji.fragment.CheckFragment;
import kr.hs.emirim.s2019w28.dongji.fragment.HomeFragment;
import kr.hs.emirim.s2019w28.dongji.fragment.VirusFragment;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton logout_btn;
    private FirebaseAuth firebaseAuth;

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
        }
    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }
}