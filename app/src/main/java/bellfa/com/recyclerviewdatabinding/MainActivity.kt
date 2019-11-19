package bellfa.com.recyclerviewdatabinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import bellfa.com.recyclerviewdatabinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter : MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        adapter =  MainAdapter(this)
        binding.recyclerview.layoutManager = LinearLayoutManager(binding.recyclerview.context)
        binding.recyclerview.adapter = adapter

        var itemList:ArrayList<DataItem>  = ArrayList<DataItem>()

        for (i in 0..9){
            var item1: DataItem? = DataItem()

            item1!!.desc = "desc"+ i
            item1!!.title = "title" + i


            itemList.add(item1)
        }

        adapter.addAll(itemList)
        adapter.notifyDataSetChanged()

    }
}
