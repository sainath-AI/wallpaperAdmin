package com.masai.sainath.wallpaperxadmin.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.masai.sainath.wallpaperxadmin.R
import com.masai.sainath.wallpaperxadmin.activity.FinalCatActivity
import com.masai.sainath.wallpaperxadmin.model.CatModel

class CatAdapter(val requireContext: Context, val listOfCategories: ArrayList<CatModel>) : RecyclerView.Adapter<CatAdapter.CatViewholder>() {


    val db = FirebaseFirestore.getInstance()

    inner class CatViewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name=itemView.findViewById<TextView>(R.id.catName)
        val imageview=itemView.findViewById<ImageView>(R.id.catiImage)
        val btndelete = itemView.findViewById<ImageView>(R.id.btnDeletetct)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewholder {
        return CatViewholder(
        LayoutInflater.from(requireContext).inflate(R.layout.item_cat,parent,false))

    }

    override fun onBindViewHolder(holder: CatViewholder, position: Int) {
        holder.name.text=listOfCategories[position].name
        Glide.with(requireContext).load(listOfCategories[position].link).into(holder.imageview)
        holder.itemView.setOnClickListener {
            val intent= Intent(requireContext, FinalCatActivity::class.java)
            intent.putExtra("uid",listOfCategories[position].id)
          //  intent.putExtra("link",listOfCategories[position].link)
            intent.putExtra("name",listOfCategories[position].name)
            requireContext.startActivity(intent)
        }
        holder.btndelete.setOnClickListener {

            val dialog = AlertDialog.Builder(requireContext)
            dialog.setMessage("are you sure want to delete this item")
            dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, _ ->
                db.collection("categories").document(listOfCategories[position].id).delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                requireContext,
                                "deleted Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext,
                                "" + it.exception?.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                dialog.dismiss()

            })
            dialog.setNegativeButton("No", DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            })
            dialog.show()
        }
    }

    override fun getItemCount(): Int {
       return listOfCategories.size
    }

}