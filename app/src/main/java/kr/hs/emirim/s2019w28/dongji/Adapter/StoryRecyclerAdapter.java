package kr.hs.emirim.s2019w28.dongji.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import kr.hs.emirim.s2019w28.dongji.model.Story;
import kr.hs.emirim.s2019w28.dongji.model.User;

public class StoryRecyclerAdapter extends RecyclerView.Adapter<StoryRecyclerAdapter.ViewHolder> {

    private List<Story> story_list;
    private List<User> user_list;
    private Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public StoryRecyclerAdapter(List<Story> story_list, List<User> user_list) {
        this.story_list = story_list;
        this.user_list = user_list;
    }

    @NonNull
    @Override
    public StoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StoryRecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}
