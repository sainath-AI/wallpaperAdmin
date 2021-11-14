package com.masai.sainath.wallpaperxadmin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.masai.sainath.wallpaperxadmin.adapter.TctAdapter
import com.masai.sainath.wallpaperxadmin.databinding.ActivityTctBinding
import com.masai.sainath.wallpaperxadmin.model.theColorTone

class TctActivity : AppCompatActivity() {

    lateinit var binding: ActivityTctBinding
    lateinit var database: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTctBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database= FirebaseFirestore.getInstance()

        database.collection("thecolortone").addSnapshotListener { value, error ->

            val listOfColorTone= arrayListOf<theColorTone>()
            val data=value?.toObjects(theColorTone::class.java)
            listOfColorTone.addAll(data!!)
            binding.revTct.layoutManager=
                GridLayoutManager(this,3)
            binding.revTct.adapter= TctAdapter(this,listOfColorTone)

        }
        binding.btnAdd.setOnClickListener {
            if(binding.etPastelink.text.toString().isEmpty()&&binding.etcolor.text.toString().isEmpty()){
                Toast.makeText(this,"Paste link pls", Toast.LENGTH_SHORT).show()

            }else{
                addWallpaperToDatabase(binding.etcolor.text.toString(),binding.etPastelink.text.toString())
            }
        }
    }

    private fun addWallpaperToDatabase(color: String, link: String) {
       val uid= database.collection("thecolortone").document().id
        val finalData= theColorTone(id= uid,link=link,color=color)
        database.collection("thecolortone").document(uid).set(finalData).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(this,"wallpaper added successfully",Toast.LENGTH_SHORT).show()

                binding.etPastelink.setText("")
                binding.etcolor.setText("")
                binding.etPastelink.clearFocus()
                binding.etcolor.clearFocus()

            }else
            {
                Toast.makeText(this,""+ it.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                binding.etPastelink.setText("")
                binding.etcolor.setText("")
                binding.etPastelink.clearFocus()
                binding.etcolor.clearFocus()
            }
        }

    }
}