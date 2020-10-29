package kr.hs.emirim.s2019w28.dongji.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

import kr.hs.emirim.s2019w28.dongji.DetailPageActivity;
import kr.hs.emirim.s2019w28.dongji.R;
import kr.hs.emirim.s2019w28.dongji.model.Post;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {

    public List<Post> post_list;
    public Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public PostRecyclerAdapter(List<Post> post_list) {
        this.post_list = post_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item,parent,false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        final String PostId = post_list.get(position).PostId;
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();


        String postTitle = post_list.get(position).getPost_title();
        String postContent = post_list.get(position).getPost_content();
        String postImage = post_list.get(position).getPost_image();

        holder.setPostImage(postImage);
        holder.setPostData(postTitle,postContent);

        try {
            long milliseconds = post_list.get(position).getTimestamp().getTime();
            String dateString = DateFormat.format("yyyy.MM.dd", new Date(milliseconds)).toString();

            holder.setTime(dateString);

        } catch (Exception e) {
            Toast.makeText(context, "Exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(context, DetailPageActivity.class);
                detail.putExtra("post_id",PostId);
                context.startActivity(detail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return post_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private ConstraintLayout post_item;

        private ImageView post_image;
        private TextView post_title;
        private TextView post_content;

        private TextView post_date;


        public ViewHolder(View v) {
            super(v);
            mView = v;
        }

        public void setPostImage(String downloadUri) {

            post_image = mView.findViewById(R.id.post_image_view);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.default_image);

            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(downloadUri).thumbnail().into(post_image);

        }

        public void setPostData(String title, String content) {

            post_title = mView.findViewById(R.id.post_title_view);
            post_content = mView.findViewById(R.id.post_content_view);

            post_title.setText(title);
            post_content.setText(content);

        }

        public void setTime(String date) {
            post_date = mView.findViewById(R.id.post_date);
            post_date.setText(date);

        }
    }
}
