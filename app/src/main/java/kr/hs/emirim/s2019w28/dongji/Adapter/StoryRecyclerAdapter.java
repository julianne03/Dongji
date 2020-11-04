package kr.hs.emirim.s2019w28.dongji.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import kr.hs.emirim.s2019w28.dongji.R;
import kr.hs.emirim.s2019w28.dongji.StoryDetailActivity;
import kr.hs.emirim.s2019w28.dongji.model.User;

public class StoryRecyclerAdapter extends RecyclerView.Adapter<StoryRecyclerAdapter.ViewHolder> {

    private List<User> user_list;
    private Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public StoryRecyclerAdapter(List<User> user_list) {
        this.user_list = user_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_list_item,parent,false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryRecyclerAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        holder.findId();

        final String user_name = user_list.get(position).getName();
        final String user_image = user_list.get(position).getImage();

        holder.setUser(user_name,user_image);

        holder.story_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent story_detail = new Intent(context, StoryDetailActivity.class);
                story_detail.putExtra("user_name",user_name);
                story_detail.putExtra("user_image",user_image);
                context.startActivity(story_detail);
            }
        });



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private ConstraintLayout story_item;
        private CircularImageView user_image;
        private TextView user_nickname;


        public ViewHolder(View v) {
            super(v);
            mView = v;
        }

        public void findId() {
            story_item = mView.findViewById(R.id.story_item);
            user_image = mView.findViewById(R.id.story_user_image);
            user_nickname = mView.findViewById(R.id.story_user_nickname);

        }

        public void setUser(String name,String image) {
            user_nickname.setText(name);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_baseline_account_circle_24);

            Glide.with(context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(Uri.parse(image))
                    .thumbnail()
                    .into(user_image);
        }



    }
}
