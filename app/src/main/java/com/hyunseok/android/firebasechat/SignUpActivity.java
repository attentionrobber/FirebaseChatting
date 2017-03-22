package com.hyunseok.android.firebasechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    EditText et_id_signup, et_pw_signup, et_nickname_signup;
    Button btn_confirm, btn_cancle;

    FirebaseDatabase database;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

        et_id_signup = (EditText) findViewById(R.id.et_id_signup);
        et_pw_signup = (EditText) findViewById(R.id.et_pw_signup);
        et_nickname_signup = (EditText) findViewById(R.id.et_nickname_signup);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = et_id_signup.getText().toString();
                final String pw = et_pw_signup.getText().toString();
                final String nickname = et_nickname_signup.getText().toString();

                if(checkStrIsEmpty(id) && checkStrIsEmpty(pw) && checkStrIsEmpty(nickname)) {
                    // DB 1. Firebase에 userRef.child(id) 레퍼런스에 대한 쿼리를 날린다 -> 검색에 대한 콜백이 돌아옴.
                    userRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        // DB 2. Firebase는 데이터쿼리가 완료되면 스냅샷에 담아서 onDataChange 를 호출해준다.
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) { // 중복된 ID일 경우
                                Toast.makeText(SignUpActivity.this, "이미 존재하는 ID입니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                DatabaseReference idRef = userRef.child(id);

                                Map<String, String> userValues = new HashMap<>();
                                userValues.put("password", pw);
                                userValues.put("nickname", nickname);

//                            Map<String, Object> userMap = new HashMap<>();
//                            userMap.put(id, userValues);

                                idRef.setValue(userValues);
                                //idRef.updateChildren(userMap);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "빈칸을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private boolean checkStrIsEmpty(String str) {
        if(str.equals("")) {
            return false;
        }
        return true;
    }
}
