package kr.hs.emirim.s2019w28.dongji.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import kr.hs.emirim.s2019w28.dongji.DetailPageActivity;
import kr.hs.emirim.s2019w28.dongji.R;
import kr.hs.emirim.s2019w28.dongji.model.Comments;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.ViewHolder> {

    public List<Comments> commentsList;
    public Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String post_id;
    private String comment_id;

    public CommentsRecyclerAdapter(List<Comments> commentsList,String post_id,String comment_id) {
        this.commentsList = commentsList;
        this.post_id = post_id;
        this.comment_id = comment_id;
    }

    @NonNull
    @Override
    public CommentsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        context = parent.getContext();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        return new CommentsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentsRecyclerAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        final String CommentId = commentsList.get(position).CommentId;
        final String comment_user_id = commentsList.get(position).getUser_id();
        String commentMessage = commentsList.get(position).getMessage();
        holder.setComment_message(commentMessage);

        final String current_user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    if(task.getResult().exists()) {
                        final String name = task.getResult().getString("name");
                        final String image = task.getResult().getString("image");

                        holder.setUserContext(name, image);

                    }
                }
            }
        });

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Posts/"+post_id+"/Comments")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (!value.isEmpty()) {

                                    if (error != null) {
                                        System.err.println(error);
                                    }

                                    for (DocumentChange doc : value.getDocumentChanges()) {
                                        if (doc.getType() == DocumentChange.Type.ADDED) {

                                            String commentId = doc.getDocument().getId();
                                            comment_id = commentId;

                                            Log.e("comment","comment id 2 : " +comment_id);

                                            if (current_user_id.equals(comment_user_id)) {
                                                firebaseFirestore.collection("Posts/"+post_id+"/Comments").document(comment_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        notifyDataSetChanged();
                                                        Toast.makeText(context,"댓글이 삭제되었습니다!",Toast.LENGTH_LONG).show();

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(context,"자신이 작성한 댓글만 삭제됩니다!",Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }


                                        }
                                    }
                                }
                            }
                        });





            }
        });

    }


    @Override
    public int getItemCount() {
        if (commentsList!=null) {
            return commentsList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView comment_message;

        private TextView comment_user_name;
        private CircularImageView comment_user_image;
        private ImageView delete_btn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            delete_btn = mView.findViewById(R.id.comment_delete_btn);
        }

        public void setComment_message(String message) {
            comment_message = mView.findViewById(R.id.comment_message);
            comment_message.setText(message);

        }

        public void setUserContext(String username, String downloadUri) {

            comment_user_name = mView.findViewById(R.id.comment_user_name);
            comment_user_image = mView.findViewById(R.id.comment_user);

            comment_user_name.setText(username);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_baseline_account_circle_24);

            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(downloadUri).thumbnail().into(comment_user_image);
        }
    }
}
