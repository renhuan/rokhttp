package com.example.okhttplib.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Environment

import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.SizeUtils
import com.example.okhttplib.R
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropActivity
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList

import android.app.Activity.RESULT_OK


/**
 * 图片选择工具类
 */
object ImagePickerHelper {
    private val REQUEST_CODE_CHOOSE = 123
    private val maxNum = 1

    /**
     * 图片选择
     *
     * @param authority BuildConfig.APPLICATION_ID + ".fileprovider"
     */
    fun setPickImage(authority: String) {
        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.CAMERA).callback(object : PermissionUtils.FullCallback {
            override fun onGranted(permissionsGranted: List<String>) {
                //选择图片
                Matisse.from(ActivityManager.getInstance().currentActivity())
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .capture(true)
                        .maxSelectable(maxNum)
                        .captureStrategy(CaptureStrategy(true, authority))
                        .gridExpectedSize(SizeUtils.dp2px(120f))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .theme(R.style.Matisse_Dracula)
                        .imageEngine(Glide4Engine())
                        .forResult(REQUEST_CODE_CHOOSE)
            }

            override fun onDenied(permissionsDeniedForever: List<String>, permissionsDenied: List<String>) {

            }
        }).request()


    }

    /**
     * 单张图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): String {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE) {
                val uris = Matisse.obtainResult(data!!)
                startUCrop(uris[0])
            } else if (requestCode == UCrop.REQUEST_CROP) {
                val resultUri = UCrop.getOutput(data!!)
                return compress(resultUri!!.path, 50)
            }
        }
        return ""
    }

    /**
     * 质量压缩
     *
     * @param quality 图片的质量,0-100,数值越小质量越差
     */
    private fun compress(path: String?, quality: Int): String {
        val originFile = File(path!!)
        val originBitmap = BitmapFactory.decodeFile(originFile.absolutePath)
        val bos = ByteArrayOutputStream()
        originBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos)
        try {
            val fos = FileOutputStream(File(path))
            fos.write(bos.toByteArray())
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return path
    }

    private fun startUCrop(uri: Uri) {
        val context = ActivityManager.getInstance().currentActivity()
        val destinationUri = Uri.fromFile(File(context?.externalCacheDir!!.path, System.currentTimeMillis().toString() + ".jpg"))
        val uCrop = UCrop.of(uri, destinationUri)
        val cropOptions = UCrop.Options()
        cropOptions.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL)
        cropOptions.setToolbarColor(Color.parseColor("#FF7B39"))
        cropOptions.setStatusBarColor(Color.parseColor("#FF7B39"))
        cropOptions.setHideBottomControls(true)
        cropOptions.setCircleDimmedLayer(true)
        cropOptions.setShowCropGrid(false)
        cropOptions.setShowCropFrame(false)
        uCrop.withOptions(cropOptions)
        uCrop.withAspectRatio(1f, 1f) //比例
        uCrop.start(context)
    }
}
