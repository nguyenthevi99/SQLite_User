package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    DataUser dataUser;
    Button btn_add, btn_remove, btn_cancel;
    EditText name_edt;
    ListView lv_name;
    ArrayList nameList;
    ArrayList idList;
    ArrayAdapter adapter;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataUser =
                new DataUser(this, "userdb.sqlite", null, 1);

        nameList = new ArrayList();
        idList = new ArrayList();

        name_edt = findViewById(R.id.name_edt);
        btn_add = findViewById(R.id.add_btn);
        btn_remove = findViewById(R.id.remobve_btn);
        btn_cancel = findViewById(R.id.cancel_btn);
        lv_name = findViewById(R.id.name_lv);


        getNameList();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList);
        lv_name.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataUser.addUser(new User(name_edt.getText().toString()));
                getNameList();
                adapter.notifyDataSetChanged();
            }
        });

        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, idList.get(index) + "here", Toast.LENGTH_SHORT).show();
                dataUser.removeUser((int)idList.get(index));
                getNameList();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "SUCCESFUL", Toast.LENGTH_SHORT).show();
            }
        });
        lv_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                index = i;
            }
        });
    }

    private ArrayList getNameList() {
        nameList.clear();
        idList.clear();
        for (Iterator iterator = dataUser.getAll().iterator(); iterator.hasNext(); ) {
            User user = (User) iterator.next();
            nameList.add(user.getName());
            idList.add(user.getId());
        }
        return nameList;
    }
}