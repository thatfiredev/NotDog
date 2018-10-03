/*
    Copyright 2018 Rosário Pereira Fernandes

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software
    and associated documentation files (the "Software"), to deal in the Software without
    restriction, including without limitation the rights to use, copy, modify, merge, publish,
    distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
    Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or
    substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
    BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
    DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.rosariopfernandes.notdog

import ability.co.mz.nahu.permissinaManager
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionLabel
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetectorOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_REQUEST = 2018
        const val CAMERA_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideSystemUI()

        fab.setOnClickListener {

            permissinaManager {
                activity = this@MainActivity
                permission = Manifest.permission.CAMERA
                permissionExplaination = getString(R.string.permission_explanation)
                requestCode = CAMERA_PERMISSION
                then {
                    val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA_REQUEST)
                }
            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (grantResults[0]) {
            PackageManager.PERMISSION_GRANTED -> {
                val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.let {
                val photo = it.extras.get("data") as Bitmap
                Glide.with(this).load(photo).into(imageView)
                labelImage(photo)
            }
        }
    }

    private fun isDog(label: FirebaseVisionLabel) = label.label.equals("Dog", true)

    private fun showResult(isDog: Boolean) {
        textView.text = if (isDog) {
            getString(R.string.dog)
        } else {
            getString(R.string.app_name)
        }
        val color = if (isDog) {
            R.color.colorGreen
        } else {
            R.color.colorRed
        }
        textView.setBackgroundColor(ContextCompat.getColor(this, color))
    }

    private fun labelImage(bitmap:Bitmap) {
        val options = FirebaseVisionLabelDetectorOptions.Builder()
                .setConfidenceThreshold(0.7f)
                .build()

        val image = FirebaseVisionImage.fromBitmap(bitmap)

        FirebaseVision.getInstance()
                .getVisionLabelDetector(options)
                .detectInImage(image)
                .addOnSuccessListener { list ->
                    for (label in list) {
                        showResult(isDog(label))
                    }
                }
                .addOnFailureListener { _ ->
                    showResult(isDog = false)
                }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }
}
