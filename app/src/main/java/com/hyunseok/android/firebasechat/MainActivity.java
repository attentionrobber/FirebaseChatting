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

public class MainActivity extends AppCompatActivity {

    EditText et_id, et_pw;
    Button btn_sign, btn_joinus;

    FirebaseDatabase database;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

        et_id = (EditText) findViewById(R.id.et_id);
        et_pw = (EditText) findViewById(R.id.et_pw);

        btn_joinus = (Button) findViewById(R.id.btn_joinus);
        btn_joinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                //intent.putExtra("userid");
                //intent.putExtra("username");
                startActivity(intent);
            }
        });
        btn_sign = (Button) findViewById(R.id.btn_sign);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = et_id.getText().toString();
                final String pw = et_pw.getText().toString();

                // DB 1. Firebase에 userRef.child(id) 레퍼런스에 대한 쿼리를 날린다 -> 검색에 대한 콜백이 돌아옴.
                userRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    // DB 2. Firebase는 데이터쿼리가 완료되면 스냅샷에 담아서 onDataChange 를 호출해준다.
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.w("MainActivity", "getChildrenCount = " + dataSnapshot.getChildrenCount());
                        if(dataSnapshot.getChildrenCount() > 0){
                            String fbPw = dataSnapshot.child("password").getValue().toString();
                            String name = dataSnapshot.child("nickname").getValue().toString();
                            Log.w("MainActivity","pw="+fbPw);
                            if(fbPw.equals(pw)){
                                Intent intent = new Intent(MainActivity.this, RoomListActivity.class);
                                intent.putExtra("userid", id);
                                intent.putExtra("username", name);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "ID가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
