package edu.illinois.firecat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private MainActivity main;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = this;

        database = FirebaseDatabase.getInstance();

//        DatabaseReference fooRef = database.getReference("foo");
//        DatabaseReference myRef = fooRef.child("message");
//
//        myRef.setValue("Hello, Earth!");

        setUpTitleListener();
        setUpMessageListenerAndMutator();


        final RecyclerView catRecycler = (RecyclerView) findViewById(R.id.catRecycler);
        DatabaseReference catsRef = database.getReference("cats");

        FirebaseRecyclerAdapter<Cat, CatViewHolder> catAdapter = new FirebaseRecyclerAdapter<Cat, CatViewHolder>
                (Cat.class, R.layout.cat_with_image, CatViewHolder.class, catsRef) {

            @Override
            protected void populateViewHolder(CatViewHolder viewHolder,
                                              Cat model, int position) {
                viewHolder.bindCat(model);
            }
        };
        catRecycler.setHasFixedSize(true);
        catRecycler.setLayoutManager(new LinearLayoutManager(this));
        catRecycler.setAdapter(catAdapter);


    }

    private void setUpMessageListenerAndMutator() {
        final TextView messageTextView = (TextView) findViewById(R.id.messageTextView);
        final EditText messageEditText = (EditText) findViewById(R.id.messageEditText);
        final Button messageButton = (Button) findViewById(R.id.messageButton);
        final DatabaseReference myRef = database.getReference("message");

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = messageEditText.getText().toString();
                myRef.setValue(message);
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageTextView.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUpTitleListener() {
        DatabaseReference titleRef = database.getReference("title");
        titleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                main.setTitle(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
