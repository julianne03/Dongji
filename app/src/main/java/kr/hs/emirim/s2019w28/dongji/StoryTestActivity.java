package kr.hs.emirim.s2019w28.dongji;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

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

import java.util.ArrayList;
import java.util.List;

import kr.hs.emirim.s2019w28.dongji.Adapter.PostRecyclerAdapter;
import kr.hs.emirim.s2019w28.dongji.Adapter.StoryRecyclerAdapter;
import kr.hs.emirim.s2019w28.dongji.model.Story;
import kr.hs.emirim.s2019w28.dongji.model.User;

public class StoryTestActivity extends AppCompatActivity {

    private RecyclerView story_list_view;
    private List<Story> story_list;
    private List<User> story_user_list;

    private FirebaseFirestore firebaseFirestore;
    private PostRecyclerAdapter postRecyclerAdapter;
    private StoryRecyclerAdapter storyRecyclerAdapter;
    private FirebaseAuth firebaseAuth;
    private ImageView user_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_test);

        story_list = new ArrayList<>();
        story_user_list = new ArrayList<>();

        story_list_view = findViewById(R.id.story_list_view_test);


        firebaseAuth = FirebaseAuth.getInstance();

        storyRecyclerAdapter = new StoryRecyclerAdapter(story_list,story_user_list);
        story_list_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        story_list_view.setAdapter(storyRecyclerAdapter);




        if(firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore = FirebaseFirestore.getInstance();

            Query storyQuery = firebaseFirestore.collection("Storys")
                    .orderBy("timestamp", Query.Direction.DESCENDING);

            storyQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (firebaseAuth.getCurrentUser() != null) {
                        if (!value.isEmpty()) {
                            story_list.clear();
                            story_user_list.clear();
                        }
                        if (error != null) {
                            System.err.println(error);
                        }

                        for (DocumentChange doc : value.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                String story_id = doc.getDocument().getId();
                                final Story story = doc.getDocument().toObject(Story.class).withId(story_id);
                                String storyUserId = doc.getDocument().getString("user_id");
                                Log.e("test", "storyUserId : " + storyUserId);
                                firebaseFirestore.collection("Users").document(storyUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            User user = task.getResult().toObject(User.class);
                                            Log.e("test","user 값 가져오기 : "+user.getName()+user.getImage());
                                            story_list.add(story);
                                            story_user_list.add(user);
                                            //Log.e("test", story_list.toString());
                                            //Log.e("test", story_user_list.toString());
                                        }
                                        storyRecyclerAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }

                    }
                }
            });
        }
    }
}