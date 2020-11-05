package kr.hs.emirim.s2019w28.dongji.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import kr.hs.emirim.s2019w28.dongji.NewPostActivity;
import kr.hs.emirim.s2019w28.dongji.AddStoryActivity;
import kr.hs.emirim.s2019w28.dongji.R;
import kr.hs.emirim.s2019w28.dongji.SetupActivity;
import kr.hs.emirim.s2019w28.dongji.model.Post;
import kr.hs.emirim.s2019w28.dongji.model.Story;
import kr.hs.emirim.s2019w28.dongji.model.User;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private ImageButton add_story;
    private FloatingActionButton add_post;
    private RecyclerView post_list_view;
    private RecyclerView story_list_view;
    private List<Story> story_list;
    private List<User> story_user_list;
    private List<Post> post_list;
    private List<User> user_list;

    private FirebaseFirestore firebaseFirestore;
    private PostRecyclerAdapter postRecyclerAdapter;
    private StoryRecyclerAdapter storyRecyclerAdapter;
    private FirebaseAuth firebaseAuth;
    private ImageView user_page;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_home, container, false);

        post_list = new ArrayList<>();
        user_list = new ArrayList<>();
        story_list = new ArrayList<>();
        story_user_list = new ArrayList<>();

        post_list_view = mView.findViewById(R.id.post_list_view);
        story_list_view = mView.findViewById(R.id.story_list_view);
        add_post = mView.findViewById(R.id.add_post);
        user_page = mView.findViewById(R.id.user_page);
        add_story = mView.findViewById(R.id.add_story);

        add_post.setOnClickListener(this);
        user_page.setOnClickListener(this);
        add_story.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        storyRecyclerAdapter = new StoryRecyclerAdapter(story_list,story_user_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        story_list_view.setLayoutManager(layoutManager);
        story_list_view.setAdapter(storyRecyclerAdapter);


        postRecyclerAdapter = new PostRecyclerAdapter(post_list,user_list);
        post_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        post_list_view.setAdapter(postRecyclerAdapter);


        if(firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore = FirebaseFirestore.getInstance();

            Query storyQuery = firebaseFirestore.collection("Storys")
                    .orderBy("timestamp",Query.Direction.DESCENDING);

            storyQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(firebaseAuth.getCurrentUser() != null) {
                        if(!value.isEmpty()) {
                            story_list.clear();
                            story_user_list.clear();
                        }
                        if(error != null) {
                            System.err.println(error);
                        }

                        for(DocumentChange doc : value.getDocumentChanges()) {

                            if(doc.getType() == DocumentChange.Type.ADDED) {
                                String story_id = doc.getDocument().getId();
                                final Story story = doc.getDocument().toObject(Story.class).withId(story_id);
                                String storyUserId = doc.getDocument().getString("user_id");
                                Log.e("test","storyUserId : "+storyUserId);
                                firebaseFirestore.collection("Users").document(storyUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            User user = task.getResult().toObject(User.class);
                                            story_list.add(story);
                                            story_user_list.add(user);
                                            Log.e("test",story_list.toString());
                                            Log.e("test",story_user_list.toString());
                                        }
                                        storyRecyclerAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }

                    }
                }
            });

            Query query = firebaseFirestore.collection("Posts")
                    .orderBy("timestamp",Query.Direction.DESCENDING);

            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(firebaseAuth.getCurrentUser() != null) {
                        if(!value.isEmpty()) {
                            post_list.clear();
                            user_list.clear();

                        }
                        if(error != null) {
                            System.err.println(error);
                        }

                        for(DocumentChange doc : value.getDocumentChanges()) {

                            if(doc.getType() == DocumentChange.Type.ADDED) {
                                String post_id = doc.getDocument().getId();
                                final Post post = doc.getDocument().toObject(Post.class).withId(post_id);

                                String postUserId = doc.getDocument().getString("user_id");
                                firebaseFirestore.collection("Users").document(postUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            User user = task.getResult().toObject(User.class);

                                            post_list.add(post);
                                            user_list.add(user);

                                        }
                                        postRecyclerAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }

                    }
                }
            });
        }

        return mView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_post :
                Intent newPostIntent = new Intent(getContext(), NewPostActivity.class);
                startActivity(newPostIntent);
                break;
            case R.id.user_page :
                Intent setupIntent = new Intent(getContext(), SetupActivity.class);
                startActivity(setupIntent);
                break;
            case R.id.add_story :
                Intent newStoryIntent = new Intent(getContext(), AddStoryActivity.class);
                startActivity(newStoryIntent);
                break;
            default:
                break;
        }
    }
}