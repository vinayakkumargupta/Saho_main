package com.example.saho_main.Quickshare

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.saho_main.databinding.ActivityQuickshareBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class QuickshareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuickshareBinding
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 101
        private const val REQUEST_CODE_SAVE_PDF = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuickshareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE
                )
            }
        }

        if (isFormDataStored()) {
            binding.formLayout.isVisible = false
            binding.pdfbanner.isVisible = true
            binding.sharebutton.isVisible = true
        } else {
            binding.formLayout.isVisible = true
            binding.pdfbanner.isVisible = false
            binding.sharebutton.isVisible = false
        }

        binding.submit.setOnClickListener {
            if (validateForm()) {
                saveFormData()
            }
            binding.formLayout.isVisible = false
            finish()
        }
        binding.sharebutton.setOnClickListener { v ->
            // Use SAF to save the PDF
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.setType("application/pdf")
            intent.putExtra(Intent.EXTRA_TITLE, "UserData.pdf")
            startActivityForResult(intent, REQUEST_CODE_SAVE_PDF)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SAVE_PDF && resultCode == RESULT_OK) {
            if (data != null && data.data != null) {
                val uri = data.data
                try {
                    // Generate PDF content
                    val pdfDocument = PdfDocument()
                    val pageNumber = 1
                    val pageInfo = PageInfo.Builder(595, 842, pageNumber).create()
                    val page = pdfDocument.startPage(pageInfo)
                    val canvas = page.canvas
                    val paint = Paint()
                    paint.textSize = 12f
                    val name = "Name: " + sharedPreferences.getString("name", "")
                    val bloodGroup = "Blood Group: " + sharedPreferences.getString("bloodGroup", "")
                    val gender = "Gender: " + sharedPreferences.getString("gender", "")
                    val weight = "Weight: " + sharedPreferences.getString("weight", "")
                    val height = "Height: " + sharedPreferences.getString("height", "")
                    val address = "Address: " + sharedPreferences.getString("address", "")
                    val textArray = arrayOf(name, bloodGroup, gender, weight, height, address)
                    var yPosition = 50f // Starting y-position for text
                    for (text in textArray) {
                        canvas.drawText(text, 50f, yPosition, paint)
                        yPosition += 20f // Increase y-position for the next line
                    }

                    // Finish the page
                    pdfDocument.finishPage(page)

                    // Use the URI to write the PDF content
                    val outputStream = contentResolver.openOutputStream(uri!!)
                    pdfDocument.writeTo(outputStream)
                    Toast.makeText(this, "PDF generated successfully", Toast.LENGTH_SHORT).show()
                    // Close resources
                    outputStream!!.close()
                    pdfDocument.close()
                    sharePDF(uri) // Call share method with the saved PDF uri
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed to generate PDF", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isFormDataStored(): Boolean {
        return sharedPreferences.contains("name") &&
                sharedPreferences.contains("bloodGroup") &&
                sharedPreferences.contains("gender") &&
                sharedPreferences.contains("weight") &&
                sharedPreferences.contains("height") &&
                sharedPreferences.contains("address")
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (binding.nameEditText.text.toString().isEmpty()) {
            isValid = false
            binding.nameEditText.error = "Name is required"
        }

        val bloodGroup = binding.bloodGroupSpinner.selectedItem.toString()
        if (bloodGroup.isEmpty()) {
            isValid = false
            Toast.makeText(this, "Blood Group is required", Toast.LENGTH_SHORT).show()
        }

        val gender = binding.genderSpinner.selectedItem.toString()
        if (gender.isEmpty()) {
            isValid = false
            Toast.makeText(this, "Gender is required", Toast.LENGTH_SHORT).show()
        }

        if (binding.weightEditText.text.toString().isEmpty()) {
            isValid = false
            binding.weightEditText.error = "Weight is required"
        }

        if (binding.heightEditText.text.toString().isEmpty()) {
            isValid = false
            binding.heightEditText.error = "Height is required"
        }

        if (binding.addressEditText.text.toString().isEmpty()) {
            isValid = false
            binding.addressEditText.error = "Address is required"
        }

        return isValid
    }

    private fun saveFormData() {
        val editor = sharedPreferences.edit()
        editor.putString("name", binding.nameEditText.text.toString())
        editor.putString("bloodGroup", binding.bloodGroupSpinner.selectedItem.toString())
        editor.putString("gender", binding.genderSpinner.selectedItem.toString())
        editor.putString("weight", binding.weightEditText.text.toString())
        editor.putString("height", binding.heightEditText.text.toString())
        editor.putString("address", binding.addressEditText.text.toString())
        editor.apply()
    }


    private fun generatePDF() {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size: 595 x 842
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        val paint = Paint()
        paint.textSize = 12f
        val name = "Name: ${sharedPreferences.getString("name", "")}"
        val bloodGroup = "Blood Group: ${sharedPreferences.getString("bloodGroup", "")}"
        val gender = "Gender: ${sharedPreferences.getString("gender", "")}"
        val weight = "Weight: ${sharedPreferences.getString("weight", "")}"
        val height = "Height: ${sharedPreferences.getString("height", "")}"
        val address = "Address: ${sharedPreferences.getString("address", "")}"

        val textArray = arrayOf(name, bloodGroup, gender, weight, height, address)
        var yPosition = 50 // Starting y-position for text
        for (text in textArray) {
            canvas.drawText(text, 50f, yPosition.toFloat(), paint)
            yPosition += 20 // Increase y-position for the next line
        }

        // Finish the page
        pdfDocument.finishPage(page)

        // Define the directory and file name for the PDF
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(directory, "UserData.pdf")

        // Write the PDF to a file
        try {
            val outputStream = FileOutputStream(file)
            pdfDocument.writeTo(outputStream)
            Toast.makeText(this, "PDF generated successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to generate PDF", Toast.LENGTH_SHORT).show()
        }

        // Close the document
        pdfDocument.close()
    }

    private fun sharePDF(pdfUri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "application/pdf"
        shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri)
        startActivity(Intent.createChooser(shareIntent, "Share PDF"))
    }


}