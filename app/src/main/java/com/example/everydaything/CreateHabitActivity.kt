package com.example.everydaything

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.everydaything.db.ContractDbTable
import kotlinx.android.synthetic.main.activity_create_habit.*
import java.io.IOException

//Extention Function
private fun EditText.isBlank() = this.text.toString().isBlank()

class CreateHabitActivity : AppCompatActivity() {

    private val TAG = CreateHabitActivity::class.simpleName
    private val IMG_REQUEST = 1
    private var imgBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)
    }

    fun storeHabit(view: View) {
        if(edit_title.isBlank() || edit_desc.isBlank()) {
            Log.d(TAG, "Title or Description missing")
            displayError("Add Title & Description")
            return
        } else if (imgBitmap == null) {
            Log.d(TAG, "image missing")
            displayError("Add image to habit")
            return
        }
        //error.visibility = View.INVISIBLE
        val title = edit_title.text.toString()
        val desc = edit_desc.text.toString()
        val ht = Habit(title, desc, imgBitmap!!)

        val id = ContractDbTable(this).store(ht)
        if (id == -1L) {
            displayError("Could not be stored...")
        } else {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

    private fun displayError(s: String) {
        error.text = s
        error.visibility = View.VISIBLE
    }

    fun selectImage(view: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val select = Intent.createChooser(intent, "Select image")
        startActivityForResult(select, IMG_REQUEST)
        Log.d(TAG, "Intent to select an image")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMG_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.data != null) {
            Log.d(TAG, "Image selected!!!")

            val bitmap = tryReadBitMap(data.data!!)
            bitmap?.let {
                this.imgBitmap = bitmap
                et_img.setImageBitmap(bitmap)
                Log.d(TAG, "updated image")
            }
        }
    }

    private fun tryReadBitMap(data: Uri): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, data)
        } catch (e : IOException) {
            e.printStackTrace()
            null
        }
    }
}