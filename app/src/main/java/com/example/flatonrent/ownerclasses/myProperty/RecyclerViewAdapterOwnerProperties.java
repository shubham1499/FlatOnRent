package com.example.flatonrent.ownerclasses.myProperty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.example.flatonrent.R;
import com.example.flatonrent.ownerclasses.ownerDataFields.flatDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecyclerViewAdapterOwnerProperties extends RecyclerView.Adapter<RecyclerViewAdapterOwnerProperties.ViewHolder> {

    Context context;
    List<flatDetails> MainImageUploadInfoList;

    public RecyclerViewAdapterOwnerProperties(Context context, List<flatDetails> TempList) {

        this.MainImageUploadInfoList = TempList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items_owner_properties, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        flatDetails fd = MainImageUploadInfoList.get(position);

        holder.tvuidhere.setText(fd.getUid());
        holder.tvareahere.setText("Area: "+fd.getArea());
        holder.tvrenthere.setText("Rs. "+fd.getRent()+"");
        holder.tvtenantshere.setText("For: "+fd.getTenant());
        holder.tvbhkhere.setText("BHK: "+fd.getBHK()+"");
        holder.fdtemp=fd;
        holder.mCardViewhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=((TextView)view.findViewById(R.id.tvuid)).getText().toString();
                str = FirebaseAuth.getInstance().getUid();
                Intent intent = new Intent(context, owner_after_clicking_hisproperty.class);
                intent.putExtra("Image", str);
                intent.putExtra("Description", (R.drawable.background));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public flatDetails fdtemp;
        public TextView tvbhkhere;
        public TextView tvareahere;
        public TextView tvtenantshere;
        public TextView tvrenthere;
        public ImageView flatimagehere;
        public TextView tvuidhere;
        public View mCardViewhere;
        Context contexttemp;

        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            contexttemp=itemView.getContext();
            flatimagehere = (ImageView) itemView.findViewById(R.id.flatimage);

            tvbhkhere = (TextView) itemView.findViewById(R.id.tvbhk);

            tvareahere = (TextView) itemView.findViewById(R.id.tvarea);

            tvtenantshere=(TextView)  itemView.findViewById(R.id.tvtenants);

            tvuidhere=(TextView)itemView.findViewById(R.id.tvuid);

            mCardViewhere = itemView.findViewById(R.id.myCardView);

            tvrenthere = (TextView) itemView.findViewById(R.id.tvrent);
        }

        @Override
        public void onClick(View view) {
           String str=((TextView)view.findViewById(R.id.tvuid)).getText().toString();
           Toast.makeText(view.getContext(),str+"   "+getLayoutPosition(),Toast.LENGTH_SHORT).show();

        }

        public flatDetails getFdtemp() {
            return fdtemp;
        }

        public void setFdtemp(flatDetails fdtemp) {
            this.fdtemp = fdtemp;
        }

        public TextView getTvbhkhere() {
            return tvbhkhere;
        }

        public void setTvbhkhere(TextView tvbhkhere) {
            this.tvbhkhere = tvbhkhere;
        }

        public TextView getTvareahere() {
            return tvareahere;
        }

        public void setTvareahere(TextView tvareahere) {
            this.tvareahere = tvareahere;
        }

        public TextView getTvtenantshere() {
            return tvtenantshere;
        }

        public void setTvtenantshere(TextView tvtenantshere) {
            this.tvtenantshere = tvtenantshere;
        }

        public TextView getTvrenthere() {
            return tvrenthere;
        }

        public void setTvrenthere(TextView tvrenthere) {
            this.tvrenthere = tvrenthere;
        }

        public ImageView getFlatimagehere() {
            return flatimagehere;
        }

        public void setFlatimagehere(ImageView flatimagehere) {
            this.flatimagehere = flatimagehere;
        }

        public Context getContexttemp() {
            return contexttemp;
        }

        public void setContexttemp(Context contexttemp) {
            this.contexttemp = contexttemp;
        }
    }
}