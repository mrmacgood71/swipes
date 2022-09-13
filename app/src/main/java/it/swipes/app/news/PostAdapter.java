package it.swipes.app.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import it.swipes.app.R;
import it.swipes.app.news.model.FullPost;
import it.swipes.app.news.model.PhotoUrl;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public Context context;
    public List<FullPost> posts;

    List<List<Integer>> resources = Arrays.asList(
            Arrays.asList(R.drawable.slide_1, R.drawable.slide_2, R.drawable.slide_3, R.drawable.slide_1),
            Arrays.asList(R.drawable.slide_1, R.drawable.slide_2, R.drawable.slide_3),
            Arrays.asList(R.drawable.slide_1, R.drawable.slide_2),
            Arrays.asList(R.drawable.slide_1, R.drawable.slide_2, R.drawable.slide_3),
            Arrays.asList(R.drawable.slide_1, R.drawable.slide_2, R.drawable.slide_3)
    );

    List<List<PhotoUrl>> images;


    public PostAdapter(Context context, List<FullPost> posts, List<List<PhotoUrl>> images) {
        this.context = context;
        this.posts = posts;
        if (images != null) {
            this.images = images;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        List<Bitmap> bitmapList = new ArrayList<>();

        for (int i = 0; i < images.get(position).size(); i++) {
            Picasso
                    .get()
                    .load(images.get(position).get(i).getUrl())
                    .into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    bitmapList.add(bitmap);

                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Toast.makeText(context, "Content Error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });

            if (i == images.get(position).size() - 1) {
                holder.drawables.addAll(bitmapList);
            }

        }

        holder.publisher.setText(posts.get(position).getAuthor().getName());
        Picasso
                .get()
                .load(posts
                        .get(position)
                        .getAuthor()
                        .getProfilePic())
                .into(holder.imageProfile);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewPager2 slider;
        List<Bitmap> drawables = new ArrayList<>();
        SliderImageAdapter adapter;
        public ImageView imageProfile, comment;
        ImageButton like, download;
        public TextView publisher;
        LinearLayout postHeader;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.profile_image);

            like = itemView.findViewById(R.id.like);

            like.setOnClickListener(view -> {
                like.setImageResource(R.drawable.ic_like_foreground_active);
            });

            imageProfile = itemView.findViewById(R.id.profile_image);

            adapter = new SliderImageAdapter(itemView.getContext(), drawables);

            slider = itemView.findViewById(R.id.slider);
            slider.setAdapter(adapter);
            slider.setClipToPadding(false);
            slider.setClipChildren(false);
            slider.setOffscreenPageLimit(2);
            slider.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

            comment = itemView.findViewById(R.id.comment);
            download = itemView.findViewById(R.id.download_button);
            publisher = itemView.findViewById(R.id.publisher);

        }
    }

    private void publisherInfo(ImageView imageProfile, TextView publisher) {

    }
}
