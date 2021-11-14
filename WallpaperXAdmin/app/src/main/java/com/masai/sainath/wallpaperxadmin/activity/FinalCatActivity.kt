package com.masai.sainath.wallpaperxadmin.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.masai.sainath.wallpaperxadmin.BomModel
import com.masai.sainath.wallpaperxadmin.adapter.CategoryAdapter
import com.masai.sainath.wallpaperxadmin.databinding.ActivityFinalCatBinding

class FinalCatActivity : AppCompatActivity() {

    lateinit var binding: ActivityFinalCatBinding
    lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseFirestore.getInstance()

        val uid = intent.getStringExtra("uid")
        val name = intent.getStringExtra("name")

        binding.CatTitle.text = name.toString()
        binding.CatSubTitle.text = "${name.toString()}  Wallpaper"

        database.collection("categories").document(uid!!).collection("wallpaper")
            .addSnapshotListener { value, _ ->
                val listofCatWallpaper = arrayListOf<BomModel>()

                val data = value?.toObjects(BomModel::class.java)
                listofCatWallpaper.addAll(data!!)


                binding.revFinal.layoutManager =
                    GridLayoutManager(this, 3)
                binding.revFinal.adapter = CategoryAdapter(this, listofCatWallpaper, uid)
            }

        binding.btnAdd.setOnClickListener {
                if (binding.etPastelink.text.toString().isEmpty()) {
                    Toast.makeText(this, "Paste link pls", Toast.LENGTH_SHORT).show()

                } else {
                    val Finaluid= database.collection("categories").document().id
                    val finalData= BomModel(id=Finaluid,link = binding.etPastelink.text.toString())

                    database.collection("categories").document(uid).
                    collection("wallpaper").document(Finaluid).set(finalData).addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(this,"wallpaper added successfully",Toast.LENGTH_SHORT).show()

                            binding.etPastelink.setText("")
                            binding.etPastelink.clearFocus()

                        }else
                        {
                            Toast.makeText(this,""+ it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                            binding.etPastelink.setText("")
                            binding.etPastelink.clearFocus()
                        }
                    }


            }
        }
    }



}