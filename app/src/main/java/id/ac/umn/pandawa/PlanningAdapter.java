package id.ac.umn.pandawa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class PlanningAdapter extends
        RecyclerView.Adapter<PlanningAdapter.ViewHolder> {

    final LinkedList<TransactionData> mdata;
    final LayoutInflater mInflater;
    final Context mContext;

    public PlanningAdapter(Context context, LinkedList<TransactionData> mdata) {
        this.mContext = context;
        this.mdata = mdata;
        this.mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.history_layout,
                parent, false);

        return new ViewHolder(view, this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionData dataTransaction = mdata.get(position);
        holder.categoryHistory.setText(dataTransaction.getCategory());
        holder.notesHistory.setText("Notes : " + dataTransaction.getNotes());
        holder.priceHistory.setText("Rp. " + dataTransaction.getMoney());
        holder.dateHistory.setText(dataTransaction.getTanggal());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView categoryHistory, notesHistory, priceHistory, dateHistory;
        ImageView fotoUser;
        PlanningAdapter mAdapter;


        public ViewHolder(@NonNull View itemView, PlanningAdapter adapter) {
            super(itemView);
            mAdapter = adapter;

            categoryHistory = itemView.findViewById(R.id.categoryHistory);
            notesHistory = itemView.findViewById(R.id.notesHistory);
            priceHistory = itemView.findViewById(R.id.priceHistory);
            dateHistory = itemView.findViewById(R.id.dateHistory);
            fotoUser = itemView.findViewById(R.id.fotoUser);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
