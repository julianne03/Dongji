package kr.hs.emirim.s2019w28.dongji.virus_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import kr.hs.emirim.s2019w28.dongji.R;
import kr.hs.emirim.s2019w28.dongji.fragment.VirusFragment;
import kr.hs.emirim.s2019w28.dongji.model.Post;
import kr.hs.emirim.s2019w28.dongji.model.User;
import kr.hs.emirim.s2019w28.dongji.virus_detail.more_info.Brucella.InfoBrucellaFragment;

public class BrucellaFragment extends Fragment {
    private Fragment VirusFragment;
    private Fragment InfoBrucellaFragment;
    private ImageView go_virus2;
    private ImageView detail_brucella;
    private RecyclerView post_list_view;
    private List<Post> post_list;
    private List<User> user_list;

    private FirebaseFirestore firebaseFirestore;
    private PostRecyclerAdapter postRecyclerAdapter;
    private FirebaseAuth firebaseAuth;


    public BrucellaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_brucella, container, false);
        go_virus2 = mView.findViewById(R.id.go_virus2);
        detail_brucella = mView.findViewById(R.id.detail_brucella);
        post_list = new ArrayList<>();
        user_list = new ArrayList<>();
        post_list_view = mView.findViewById(R.id.post_list_view_brucella);

        go_virus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VirusFragment = new VirusFragment();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, VirusFragment).commit();
            }
        });

        detail_brucella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InfoBrucellaFragment = new InfoBrucellaFragment();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, InfoBrucellaFragment).commit();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        postRecyclerAdapter = new PostRecyclerAdapter(post_list,user_list);
        post_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        post_list_view.setAdapter(postRecyclerAdapter);

        if(firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore = FirebaseFirestore.getInstance();

            Query query = firebaseFirestore.collection("Posts")
                    .whereEqualTo("virus_category","브루셀라증");


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
}