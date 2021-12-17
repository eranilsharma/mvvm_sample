package com.mvvm.mvvmandroid.utils.customs

import android.Manifest.permission
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.mvvm.mvvmandroid.R
import com.mvvm.mvvmandroid.utils.RealPathUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_pic_bottom_sheet.*
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * @author Satnam Singh
 * Single class for handling Image select from Gallery and Camera. Don't use any other code for local image select
 * TODO: IMPLEMENT BASIC LEVEL IMAGE COMPRESSION
 * TODO: IMPLEMENT SNACKBAR TO DISPLAY APP SETTING TO ENABLE DENIED PERMISSIONS
 */
class PicBottomDialogFragment(listener: PicBottomDialogListener, isCamerEnable: Boolean) :
    BottomSheetDialogFragment(),
    View.OnClickListener {

    private lateinit var capturedImageUri: Uri
    private lateinit var file: File
    private val isCamerEnable = isCamerEnable
    private val listener = listener

    private val PERMISSION_REQUEST_CODE_CAMERA = 100
    private val PERMISSION_REQUEST_CODE_GALLERY = 200
    private val REQUEST_IMAGE_GALLERY = 1000
    private val REQUEST_IMAGE_CAMERA = 2000

    interface PicBottomDialogListener {
        fun onGalleryImage(fileRealPath: String, uri: Uri?)
        fun onCameraImage(bitmap: Bitmap?, fileRealPath: String, uri: Uri)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvCamera -> {
                if (haveCameraPermission()) {
                    dispatchTakePictureIntent()
                } else {
                    requestCameraPermission()
                }
            }
            R.id.tvGallery -> {
                if (haveStoragePermission()) {
                    openGallery()
                } else {
                    requestStoragePermission()
                }
            }
        }
    }


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.layout_pic_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isCamerEnable) {
            tvCamera.visibility = View.VISIBLE
        }
        tvCamera.setOnClickListener(this)
        tvGallery.setOnClickListener(this)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE_CAMERA ->
                if (grantResults.isNotEmpty() && permissions[0] == permission.CAMERA) {
                    var deniedCount = 0

                    for (i in 1..grantResults.size) {
                        // check whether storage permission granted or not.
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            deniedCount++
                        }
                    }

                    if (deniedCount == 0) {
                        dispatchTakePictureIntent()
                    } else {
                        requestCameraPermission()
                    }
                }
            PERMISSION_REQUEST_CODE_GALLERY -> {
                if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        this.dismiss()
        when (requestCode) {
            REQUEST_IMAGE_CAMERA -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = data?.getExtras()?.get("data") as Bitmap
                    Timber.d(capturedImageUri.toString() + "")
                    Timber.d(file.absolutePath + "")
                    val tempfile = saveBitmapToFile(bitmap)
                    listener.onCameraImage(bitmap, tempfile?.absolutePath ?: "", capturedImageUri)
                }
            }
            REQUEST_IMAGE_GALLERY -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri = data?.data
                    val realPath = RealPathUtil.getRealPath(activity, uri)
                    Timber.d(realPath + "")
                    listener.onGalleryImage(realPath, uri)
                }
            }
        }
    }


    companion object {
        val TAG = PicBottomDialogFragment.javaClass.simpleName
        fun newInstance(
            listener: PicBottomDialogListener,
            isCamerEnable: Boolean
        ): PicBottomDialogFragment {
            return PicBottomDialogFragment(listener, isCamerEnable)
        }
    }

    private fun haveCameraPermission() =
        ContextCompat.checkSelfPermission(
            requireActivity(),
            permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED && haveStoragePermission()


    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(permission.CAMERA, permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE_CAMERA
        )
    }


    private fun haveStoragePermission() =
        ContextCompat.checkSelfPermission(
            requireActivity(),
            permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    /**
     * asking only WRITE_EXTERNAL_STORAGE as this is higher level permission include READ_EXTERNAL_STORAGE as well
     */
    private fun requestStoragePermission() {
        requestPermissions(
            arrayOf(permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE_GALLERY
        )
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            REQUEST_IMAGE_GALLERY
        )
    }


//    @Throws(IOException::class)
//    private fun createImageFile(): File {
//        // Create an image file name
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val storageDir: File = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
//        return File.createTempFile(
//            "JPEG_${timeStamp}_", /* prefix */
//            ".jpg", /* suffix */
//            storageDir /* directory */
//        ).apply {
//            // Save a file: path for use with ACTION_VIEW intents
//            currentPhotoPath = absolutePath
//        }
//    }

    //USING LEGACY STORAGE, SO don't worry about FilePROVIDER as they are useless
    private fun dispatchTakePictureIntent() {
        file = File(
            Environment.getExternalStorageDirectory(),
            "img_" + System.currentTimeMillis() + ".jpeg"
        )
        try {
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        capturedImageUri = Uri.fromFile(file)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri)
        }
        startActivityForResult(
            intent, REQUEST_IMAGE_CAMERA
        )
    }

    private fun saveBitmapToFile(bmp: Bitmap): File? {
        val extStorageDirectory = Environment.getExternalStorageDirectory().toString()
        var outStream: OutputStream? = null
        var file = File(extStorageDirectory, "temp.png")
        if (file.exists()) {
            file.delete()
            file = File(extStorageDirectory, "temp.png")
        }
        try {
            outStream = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 80, outStream)
            outStream.flush()
            outStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return file
    }
}