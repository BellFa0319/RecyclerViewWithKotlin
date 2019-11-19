package bellfa.com.recyclerviewdatabinding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import bellfa.com.recyclerviewdatabinding.databinding.ItemMainBinding

class MainAdapter(private val mContext: Context) : RecyclerViewAdapter<DataItem, MainViewHolder>() {
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val binding = ItemMainBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindItemViewHolder(holder: MainViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.txtDesc.text = item!!.desc
        holder.binding.txtTitle.text = item.title
    }
}
