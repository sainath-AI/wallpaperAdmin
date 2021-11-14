package com.masai.sainath.wallpaperxadmin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.masai.sainath.wallpaperxadmin.R
import com.masai.sainath.wallpaperxadmin.adapter.CatAdapter
import com.masai.sainath.wallpaperxadmin.databinding.ActivityBomBinding
import com.masai.sainath.wallpaperxadmin.databinding.ActivityCatBinding
import com.masai.sainath.wallpaperxadmin.model.CatModel

class CatActivity : AppCompatActivity() {

    lateinit var binding: ActivityCatBinding
    lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseFirestore.getInstance()


        database.collection("categories").addSnapshotListener { value, error ->
            val listOfCategories = arrayListOf<CatModel>()
            val data = value?.toObjects(CatModel::class.java)
            listOfCategories.addAll(data!!)
            binding.revCat.layoutManager = GridLayoutManager(this, 2)
            binding.revCat.adapter = CatAdapter(this, listOfCategories)


        }
        binding.btnAdd3.setOnClickListener {
            if (binding.etName.text.toString().isEmpty()
                && binding.etPasteLink.text.toString().isEmpty()) {
                Toast.makeText(this, "Paste link pls", Toast.LENGTH_SHORT).show()

            } else {
                addLinkToDatabase(
                    binding.etPasteLink.text.toString(),
                    binding.etName.text.toString()
                )

            }
        }
    }

    private fun addLinkToDatabase(link: String, name: String) {

        val uid= database.collection("categories").document().id
        val finalData = CatModel(id = uid ,name = name, link = link)
        database.collection("categories").document(uid).set(finalData).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "wallpaper added successfully", Toast.LENGTH_SHORT).show()
                binding.etPasteLink.setText("")
                binding.etName.setText("")
                binding.etPasteLink.clearFocus()
                binding.etName.clearFocus()

            } else {
                Toast.makeText(this, "" + it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                binding.etPasteLink.setText("")
                binding.etName.setText("")
                binding.etPasteLink.clearFocus()
                binding.etName.clearFocus()
            }
        }
    }
}