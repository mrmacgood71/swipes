package it.swipes.app.publisher;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import it.swipes.app.MainActivity;
import it.swipes.app.R;
import it.swipes.app.publisher.model.Publisher;

public class PublishersRecycleViewAdapter extends RecyclerView.Adapter<PublishersRecycleViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Publisher> mPublics;

    public PublishersRecycleViewAdapter(Context mContext, List<Publisher> mPublics) {
        this.mContext = mContext;
        this.mPublics = mPublics;
        notifyDataSetChanged();
    }

    public void setFilteredList(List<Publisher> filteredList) {
        this.mPublics = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PublishersRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.publics_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PublishersRecycleViewAdapter.ViewHolder holder, int position) {


        holder.id.setText("" + mPublics.get(position).getId());

        Picasso.get()
                .load(mPublics.get(position).getProfilePic())
                .into(holder.icon);

        holder.name.setText(mPublics.get(position).getName());
        holder.description.setText(mPublics.get(position).getDescription());

        Log.d("TAG", "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return mPublics.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView icon;
        TextView id;
        TextView name;
        TextView description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.publisher_id);
            icon = itemView.findViewById(R.id.publisher_icon);
            name = itemView.findViewById(R.id.publisher_name);
            description = itemView.findViewById(R.id.publisher_description);


            itemView.setOnClickListener(view -> {
                MainActivity activity = (MainActivity) itemView.getContext();
                NavController navController = Navigation.findNavController(activity, R.id.app_placeholder);
                Bundle bundle = new Bundle();

                bundle.putString("publisherId", id.getText().toString());
                bundle.putString("publisherName", name.getText().toString());
                bundle.putString("publisherDescription", description.getText().toString());

                navController.navigate(R.id.get_publisher, bundle);

            });
        }
    }
}
