package com.masai.sainath.wallpaperxadmin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.masai.sainath.wallpaperxadmin.adapter.BomAdapter
import com.masai.sainath.wallpaperxadmin.BomModel
import com.masai.sainath.wallpaperxadmin.databinding.ActivityBomBinding

class BomActivity : AppCompatActivity() {
    lateinit var binding: ActivityBomBinding
    lateinit var database:FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database= FirebaseFirestore.getInstance()

        database.collection("bestofthemonth").addSnapshotListener { value, error ->
            val  listBestofTheMoth = arrayListOf<BomModel>()

            val data=value?.toObjects(BomModel::class.java)
            listBestofTheMoth.addAll(data!!)

            binding.revBom.layoutManager=
                GridLayoutManager(this    ,3)
            binding.revBom.adapter= BomAdapter(this,listBestofTheMoth)

        }
        binding.btnAdd.setOnClickListener {
            if (binding.etPastelink.text.toString().isEmpty()){
                Toast.makeText(this,"Paste link pls",Toast.LENGTH_SHORT).show()
            }else   {
                addLinkToDatabase(binding.etPastelink.text.toString())
            }
        }
    }

    private fun addLinkToDatabase(wallPaperLink: String) {
       val uid= database.collection("bestofthemonth").document().id
        val finalData= BomModel(uid,wallPaperLink)

        database.collection("bestofthemonth").document(uid).set(finalData).addOnCompleteListener{
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