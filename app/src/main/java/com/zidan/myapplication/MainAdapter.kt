package com.zidan.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class MainAdapter(private val memes: List<Meme>, private val listener: (Meme) -> Unit): RecyclerView.Adapter<MemeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val itemGrid = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return MemeViewHolder(itemGrid)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        holder.bindItem(memes[position], listener)
    }

    override fun getItemCount(): Int = memes.size
}

class MemeViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val image: ImageView = view.findViewById(R.id.img_item_photo)
    fun bindItem(meme: Meme, listener: (Meme) -> Unit){
        Picasso.get().load(meme.img).fit().into(image)
        itemView.setOnClickListener { listener(meme) }
    }
}

class MemeUI: AnkoComponent<ViewGroup>{

    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                padding = dip(16)

                imageView{
                    id = R.id.img_view
                }.lparams{
                    height = dip(100)
                    width = dip(100)
                }
            }
        }
    }
}