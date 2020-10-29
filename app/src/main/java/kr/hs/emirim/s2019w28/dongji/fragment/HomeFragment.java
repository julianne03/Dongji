package kr.hs.emirim.s2019w28.dongji.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import kr.hs.emirim.s2019w28.dongji.Adapter.PostRecyclerAdapter;
import kr.hs.emirim.s2019w28.dongji.NewPostActivity;
import kr.hs.emirim.s2019w28.dongji.R;
import kr.hs.emirim.s2019w28.dongji.model.Post;

public class HomeFragment extends Fragment {

    private FloatingActionButton add_post;
    private RecyclerView post_list_view;
    private List<Post> post_list;

    private FirebaseFirestore firebaseFirestore;
    private PostRecyclerAdapter postRecyclerAdapter;
    private FirebaseAuth firebaseAuth;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_home, container, false);

        post_list = new ArrayList<>();
        post_list_view = mView.findViewById(R.id.post_list_view);

        add_post = mView.findViewById(R.id.add_post);

        add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPostIntent = new Intent(getContext(), NewPostActivity.class);
                startActivity(newPostIntent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        postRecyclerAdapter = new PostRecyclerAdapter(post_list);
        post_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        post_list_view.setAdapter(postRecyclerAdapter);

        if(firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore = FirebaseFirestore.getInstance();

            Query query = firebaseFirestore.collection("Posts")
                    .orderBy("timestamp",Query.Direction.DESCENDING);

            query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(firebaseAuth.getCurrentUser() != null) {
                        if(!value.isEmpty()) {
                            post_list.clear();

                        }
                        if(error != null) {
                            System.err.println(error);
                        }

                        for(DocumentChange doc : value.getDocumentChanges()) {

                            if(doc.getType() == DocumentChange.Type.ADDED) {
                                String post_id = doc.getDocument().getId();
                                final Post post = doc.getDocument().toObject(Post.class).withId(post_id);

                                post_list.add(post);
                            }
                        }
                        postRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            });
        }


        add_story = mView.findViewById(R.id.add_story);

        add_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newStoryIntent = new Intent(getContext(), AddStoryActivity.class);
                startActivity(newStoryIntent);
            }
        });


        return mView;
    }
}