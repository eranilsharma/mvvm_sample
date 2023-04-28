package com.sharma.ankitapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sharma.ankitapp.R
import com.sharma.ankitapp.databinding.ItemSubjectBinding
import com.sharma.ankitapp.ui.home.model.SubjectList
import com.sharma.ankitapp.ui.home.model.SubjectListItem
import com.sharma.ankitapp.utils.xtnLoadImage

class SubjectListAdapter(val context: Context, val data: SubjectList,private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<SubjectListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ItemSubjectBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView.text=data[position].mclassname
       // holder.binding.button2.text="Let's Start"
        xtnLoadImage(data[position].classimgurl, R.drawable.bg_placeholder,holder.binding.imageView2)
        holder.binding.button2.setOnClickListener {
            onClickListener.onClick(data[position])
        }
    }


}
class OnClickListener(val clickListener: (subject: SubjectListItem) -> Unit) {
    fun onClick(subject: SubjectListItem) = clickListener(subject)
}
