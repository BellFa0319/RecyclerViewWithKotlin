package bellfa.com.recyclerviewdatabinding

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import java.util.ArrayList

abstract class RecyclerViewAdapter<E, VH : RecyclerViewHolder> : RecyclerView.Adapter<VH>(),
    RecyclerViewItemClickListener, RecyclerViewItemLongClickListener,
    RecyclerViewItemCheckedChangeListener {

    protected var listener: RecyclerViewItemClickListener? = null
    protected var longClickListener: RecyclerViewItemLongClickListener? = null
    protected var checkedChangeListener: RecyclerViewItemCheckedChangeListener? = null

    var items: ArrayList<E>? = ArrayList()
        protected set

    var headerView: View? = null
        set(headerView) {
            if (this.headerView != null) {
                val parent = this.headerView!!.parent
                if (parent != null) {
                    (parent as ViewGroup).removeView(this.headerView)
                }
            }
            field = headerView
        }

    /**
     * HeaderView 를 제외한 개수를 반환한다
     */
    val count: Int
        get() = if (items == null) 0 else items!!.size

    fun setOnItemClickListener(listener: RecyclerViewItemClickListener) {
        this.listener = listener
    }

    fun setOnItemLongClickListener(listener: RecyclerViewItemLongClickListener) {
        this.longClickListener = listener
    }

    fun setOnItemCheckListener(listener: RecyclerViewItemCheckedChangeListener) {
        this.checkedChangeListener = listener
    }

    private fun hasHeaderView(): Boolean {
        return this.headerView != null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val holder: RecyclerViewHolder
        if (viewType == VIEW_TYPE_HEADER) {
            val fr = FrameLayout(parent.context)
            if (parent is RecyclerView) {
                val layoutParams = parent.layoutManager!!.generateDefaultLayoutParams()
                fr.layoutParams = layoutParams
            }
            holder = HeaderViewHolder(fr)
        } else {
            holder = onCreateItemViewHolder(parent, viewType)
            holder.setOnItemClickListener(this)
            holder.setOnItemLongClickListener(this)
            holder.setOnItemCheckListener(this)
        }
        return holder as VH
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        var position = position
        val viewType = getItemViewType(position)
        if (viewType == VIEW_TYPE_HEADER) {
            val parent = this.headerView!!.parent
            if (parent != null) {
                (parent as ViewGroup).removeAllViews()
            }
            val fr = holder.itemView as FrameLayout
            fr.removeAllViews()
            fr.addView(this.headerView)
            fr.requestLayout()

            val lp = holder.itemView.getLayoutParams()
            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
                lp.isFullSpan = true
            }
            onBindHeaderViewHolder(holder as HeaderViewHolder)
        } else {
            if (hasHeaderView()) {
                position -= 1
            }
            onBindItemViewHolder(holder, position)
        }
    }

    abstract fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): VH

    protected fun onBindHeaderViewHolder(holder: HeaderViewHolder) {}

    abstract fun onBindItemViewHolder(holder: VH, position: Int)

    override fun onViewRecycled(holder: VH) {
        if (holder is HeaderViewHolder) {
            onHeaderViewRecycled(holder as HeaderViewHolder)
        } else {
            onItemViewRecycled(holder)
        }
    }

    protected fun onHeaderViewRecycled(headerViewHolder: HeaderViewHolder) {}

    protected fun onItemViewRecycled(holder: VH) {}

    override fun onViewDetachedFromWindow(holder: VH) {
        if (holder is HeaderViewHolder) {
            onHeaderViewDetachedFromWindow(holder as HeaderViewHolder)
        } else {
            onItemViewDetachedFromWindow(holder)
        }
    }

    protected fun onHeaderViewDetachedFromWindow(headerViewHolder: HeaderViewHolder) {}

    protected fun onItemViewDetachedFromWindow(holder: VH) {}

    override fun onItemClick(position: Int, view: View) {
        var position = position
        if (hasHeaderView()) {
            position -= 1
        }
        if (listener != null) {
            listener!!.onItemClick(position, view)
        }
    }

    override fun onItemLongClick(position: Int) {
        var position = position
        if (hasHeaderView()) {
            position -= 1
        }
        if (longClickListener != null) {
            longClickListener!!.onItemLongClick(position)
        }
    }

    override fun onCheckedChanged(position: Int, isChecked: Boolean) {
        var position = position
        if (hasHeaderView()) {
            position -= 1
        }
        if (checkedChangeListener != null) {
            checkedChangeListener!!.onCheckedChanged(position, isChecked)
        }
    }

    /**
     * HeaderView 를 포함한 개수를 반환한다
     */
    @Deprecated("")
    override fun getItemCount(): Int {
        val extra = if (hasHeaderView()) 1 else 0
        val itemSize = if (items == null) 0 else items!!.size
        return itemSize + extra
    }

    override fun getItemViewType(position: Int): Int {
        var position = position
        var viewType = VIEW_TYPE_ITEM
        if (hasHeaderView() && position == 0) {
            viewType = VIEW_TYPE_HEADER
        }

        if (viewType == VIEW_TYPE_ITEM) {
            position = if (hasHeaderView()) position - 1 else position
            return getViewType(position)
        }
        return viewType
    }

    protected fun getViewType(position: Int): Int {
        return VIEW_TYPE_ITEM
    }

    fun getItem(position: Int): E? {
        return if (position < 0 || position >= items!!.size) null else items!![position]
    }

    fun add(data: E) {
        items!!.add(data)
    }

    fun add(index: Int, data: E) {
        items!!.add(index, data)
    }

    fun addAll(data: List<E>) {
        items!!.addAll(data)
    }

    fun addAll(index: Int, data: List<E>) {
        items!!.addAll(index, data)
    }

    fun remove(index: Int) {
        items!!.removeAt(index)
    }

    fun remove(data: E) {
        items!!.remove(data)
    }

    fun indexOf(data: E): Int {
        return items!!.indexOf(data)
    }

    fun clear() {
        if (items != null) {
            items!!.clear()
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerViewHolder(itemView)

    companion object {

        val VIEW_TYPE_ITEM = 0
        val VIEW_TYPE_HEADER = 500
    }
}