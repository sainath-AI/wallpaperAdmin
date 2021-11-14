package com.masai.sainath.wallpaperxadmin.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.masai.sainath.wallpaperxadmin.R
import com.masai.sainath.wallpaperxadmin.model.theColorTone


class TctAdapter(
    private val requireContext: Context, private val listofColorTone:
    ArrayList<theColorTone>
) :
    RecyclerView.Adapter<TctAdapter.TctViewholder>() {

    val db = FirebaseFirestore.getInstance()


    inner class TctViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardview = itemView.findViewById<CardView>(R.id.itemCard)
        val btndelete = itemView.findViewById<ImageView>(R.id.btnDeletetct)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TctViewholder {
        return TctViewholder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_colortone, parent, false)
        )

    }

    override fun onBindViewHolder(holder: TctViewholder, position: Int) {

        val color = listofColorTone[position].color
        holder.cardview.setCardBackgroundColor(Color.parseColor(color!!))

        holder.btndelete.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext)
            dialog.setMessage("are you sure want to delete this item")
            dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, _ ->
                db.collection("thecolortone").document(listofColorTone[position].id).delete()
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
        return listofColorTone.size
    }

}