package com.develop.productlisting.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.develop.productlisting.R
import com.develop.productlisting.data.Constansts
import com.develop.productlisting.data.Product
import com.develop.productlisting.databinding.ItemViewBinding

//class ProductAdapter : PagingDataAdapter<Product, ProductAdapter.ItemViewHolder>(DIFF_CALLBACK){
class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ItemViewHolder>(){

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>(){
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.Id == newItem.Id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }

        }
    }


    private var pData  = mutableListOf<Product>()
    inner class ItemViewHolder(val bind : ItemViewBinding) : RecyclerView.ViewHolder(bind.root){
        fun bindData(data: Product) {
            if(data.ProductPictures.isNotEmpty()
//                && data.ProductPictures.get(0).PictureUrl.isNotEmpty()
            ){
                data.ProductPictures.forEach {
                    if(it.PictureUrl.isNotEmpty()){
                        Glide.with(bind.root.context)
                            .load(data.ProductPictures[0].PictureUrl.toUri())
                            .centerCrop()
                            .into(bind.productImage)
                        return@forEach
                    }
                }
            }else{
                bind.productImage.setImageResource(R.drawable.ic_launcher_background)
            }
            bind.productName.text = data.Name
            bind.productPrice.text = data.Price.toString()
            bind.productDescription.text = data.ShortDescription
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pData.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(pData[position])

    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData(){
        pData.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(lst : List<Product>){
        Log.d(Constansts.LOG_PRODUCT_DATA, "submitList: ${lst.size}")
        pData.addAll(lst)
        notifyDataSetChanged()
    }
}
