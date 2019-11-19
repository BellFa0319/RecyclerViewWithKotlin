package bellfa.com.recyclerviewdatabinding;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnLongClickListener,
        CompoundButton.OnCheckedChangeListener{

    protected RecyclerViewItemClickListener listener;
    protected RecyclerViewItemLongClickListener longClickListener;
    protected RecyclerViewItemCheckedChangeListener checkListener;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        if (itemView instanceof AdapterView) {
            return;
        }
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    void setOnItemClickListener(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    void setOnItemLongClickListener(RecyclerViewItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    void setOnItemCheckListener(RecyclerViewItemCheckedChangeListener listener) {
        this.checkListener = listener;
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        if (listener != null) listener.onItemClick(position, v);
    }

    @Override
    public boolean onLongClick(View v) {
        int position = getAdapterPosition();
        if (longClickListener != null) longClickListener.onItemLongClick(position);
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = getAdapterPosition();
        if (checkListener != null) {
            checkListener.onCheckedChanged(position, isChecked);
        }
    }
}
