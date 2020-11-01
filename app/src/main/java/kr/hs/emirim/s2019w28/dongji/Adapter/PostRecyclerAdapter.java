package kr.hs.emirim.s2019w28.dongji.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.hs.emirim.s2019w28.dongji.DetailPageActivity;
import kr.hs.emirim.s2019w28.dongji.R;
import kr.hs.emirim.s2019w28.dongji.model.Post;
import kr.hs.emirim.s2019w28.dongji.model.User;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {

    public List<Post> post_list;
    public List<User> user_list;
    public Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public PostRecyclerAdapter(List<Post> post_list,List<User> user_list) {
        this.post_list = post_list;
        this.user_list = user_list;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        final String PostId = post_list.get(position).PostId;
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();


        String postTitle = post_list.get(position).getPost_title();
        String postVirus = post_list.get(position).getVirus_category();
        String postImage = post_list.get(position).getPost_image();

        final String userName = user_list.get(position).getName();
        final String userImage = user_list.get(position).getImage();

        holder.setPostImage(postImage);
        holder.setPostData(postTitle,postVirus);

        try {
            long milliseconds = post_list.get(position).getTimestamp().getTime();
            String dateString = DateFormat.format("yyyy.MM.dd", new Date(milliseconds)).toString();

            holder.setTime(dateString);

        } catch (Exception e) {
            Toast.makeText(context, "Exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        holder.findId();

        //help btn click
        holder.help_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Posts/"+PostId+"/Helps")
                        .document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(!task.getResult().exists()) {
                            firebaseFirestore.collection("Posts")
                                    .document(PostId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.getResult().exists()) {
                                        Map<String,Object> helpsMap = new HashMap<>();
                                        helpsMap.put("user_id",currentUserId);
                                        helpsMap.put("timestamp",FieldValue.serverTimestamp());

                                        firebaseFirestore.collection("Posts/"+PostId+"/Helps")
                                                .document(currentUserId).set(helpsMap);

                                    }
                                }
                            });
                        } else {
                            firebaseFirestore.collection("Posts/"+PostId+"/Helps")
                                    .document(currentUserId).delete();
                        }
                    }
                });
            }
        });



        //help count
        if(currentUserId != null) {
            firebaseFirestore.collection("Posts/"+PostId+"/Helps").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(!value.isEmpty()) {
                        int count = value.size();
                        holder.updateHelpsCount(count);
                    } else {
                        holder.updateHelpsCount(0);
                    }
                }
            });

            //help btn image change
            if(firebaseAuth.getCurrentUser() != null) {
                firebaseFirestore.collection("Posts/"+ PostId + "/Helps").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value.exists()) {
                            holder.help_btn.setImageResource(R.drawable.help_icon_accent);
                        } else {
                            holder.help_btn.setImageResource(R.drawable.help_icon);
                        }
                    }
                });
            }
        }

        holder.post_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(context, DetailPageActivity.class);
                detail.putExtra("post_id",PostId);
                detail.putExtra("user_name",userName);
                detail.putExtra("user_image",userImage);
                Log.e("test",PostId);
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
        private TextView post_virus;

        private TextView post_date;

        private ImageView help_btn;
        private TextView help_count;


        public ViewHolder(View v) {
            super(v);
            mView = v;

            help_btn = mView.findViewById(R.id.post_help_btn);
        }

        public void findId() {
            post_item = mView.findViewById(R.id.post_item);
        }
        public void setPostImage(String downloadUri) {

            post_image = mView.findViewById(R.id.post_image_view);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.default_image);

            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(downloadUri).thumbnail().into(post_image);

        }

        public void setPostData(String title, String virus) {

            post_title = mView.findViewById(R.id.post_title_view);
            post_virus = mView.findViewById(R.id.post_virus_view);

            post_title.setText(title);
            post_virus.setText("# "+virus);

        }

        public void setTime(String date) {
            post_date = mView.findViewById(R.id.post_date);
            post_date.setText(date);

        }

        public void updateHelpsCount(int count) {
            help_count = mView.findViewById(R.id.post_help_count);
            help_count.setText(count+" help");
        }
    }
}
