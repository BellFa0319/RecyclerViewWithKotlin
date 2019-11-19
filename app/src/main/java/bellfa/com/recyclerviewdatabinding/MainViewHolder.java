package bellfa.com.recyclerviewdatabinding;

import bellfa.com.recyclerviewdatabinding.databinding.ItemMainBinding;

public class MainViewHolder extends RecyclerViewHolder {
    ItemMainBinding binding;

    public MainViewHolder(ItemMainBinding itemView) {
        super(itemView.getRoot());
        this.binding = itemView;
    }
}
