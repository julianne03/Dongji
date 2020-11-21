package kr.hs.emirim.s2019w28.dongji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText reg_email;
    private EditText reg_password;
    private EditText reg_confirm_password;
    private Button reg_btn;
    private CheckBox personal_checkbox;
    private TextView reg_login_btn;
    private ProgressBar register_progress;
    private ScrollView scrollView;
    private TextView see_text;
    private TextView close_text;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        reg_email = (EditText) findViewById(R.id.reg_email);
        reg_password = (EditText) findViewById(R.id.reg_password);
        reg_confirm_password = (EditText) findViewById(R.id.reg_confirm_password);
        reg_btn = (Button) findViewById(R.id.reg_btn);
        reg_login_btn = findViewById(R.id.reg_login_btn);
        personal_checkbox = findViewById(R.id.personal_checkbox);
        scrollView = findViewById(R.id.scrollView2);
        see_text = findViewById(R.id.see_text);
        close_text = findViewById(R.id.close_text);

        see_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.setVisibility(View.VISIBLE);
                close_text.setVisibility(View.VISIBLE);
                see_text.setVisibility(View.INVISIBLE);
            }
        });

        close_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.setVisibility(View.GONE);
                close_text.setVisibility(View.INVISIBLE);
                see_text.setVisibility(View.VISIBLE);
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(personal_checkbox.isChecked()) {
                    String email = reg_email.getText().toString();
                    String pass = reg_password.getText().toString();
                    String confirm_pass = reg_confirm_password.getText().toString();


                    if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass)) {

                        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                        {
                            Toast.makeText(RegisterActivity.this,"이메일 형식이 올바르지 않습니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (pass.equals(confirm_pass)) {
                            if(pass.length() < 6) {
                                Toast.makeText(RegisterActivity.this, "비밀번호는 6자 이상 입력해주세요.", Toast.LENGTH_LONG).show();
                                return;
                            }
                             else {
                                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(mainIntent);
                                            finish();

                                        } else {
                                            String errorMessage = task.getException().getMessage();
                                            Toast.makeText(RegisterActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }

                        } else {
                            Toast.makeText(RegisterActivity.this, "다시 입력한 비밀번호와 기존 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "빈칸을 채워주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "개인정보방침에 동의해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        reg_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(regIntent);
            }
        });
    }
}