package com.example.okhttplib.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.Nullable;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.example.okhttplib.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * 图片选择工具类
 */
public class ImagePickerHelper {
    private static final int REQUEST_CODE_CHOOSE = 123;
    private static int maxNum = 1;

    /**
     * 图片选择
     *
     * @param authority BuildConfig.APPLICATION_ID + ".fileprovider"
     */
    public static void setPickImage(final String authority) {
        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.CAMERA).callback(new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(List<String> permissionsGranted) {
                //选择图片
                Matisse.from(ActivityUtils.getTopActivity())
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .capture(true)
                        .maxSelectable(maxNum)
                        .captureStrategy(new CaptureStrategy(true, authority))
                        .gridExpectedSize(SizeUtils.dp2px(120))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .theme(R.style.Matisse_Dracula)
                        .imageEngine(new Glide4Engine())
                        .forResult(REQUEST_CODE_CHOOSE);
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {

            }
        }).request();


    }

    /**
     * 单张图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    public static String onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE) {
                List<Uri> uris = Matisse.obtainResult(data);
                startUCrop(uris.get(0));
            } else if (requestCode == UCrop.REQUEST_CROP) {
                Uri resultUri = UCrop.getOutput(data);
                return compress(resultUri.getPath(), 50);
            }
        }
        return "";
    }

    /**
     * 质量压缩
     *
     * @param quality 图片的质量,0-100,数值越小质量越差
     */
    private static String compress(String path, int quality) {
        File originFile = new File(path);
        Bitmap originBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        originBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    private static void startUCrop(Uri uri) {
        Activity context = ActivityUtils.getTopActivity();
        Uri destinationUri = Uri.fromFile(new File(context.getExternalCacheDir().getPath(), System.currentTimeMillis() + ".jpg"));
        UCrop uCrop = UCrop.of(uri, destinationUri);
        UCrop.Options cropOptions = new UCrop.Options();
        cropOptions.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        cropOptions.setToolbarColor(Color.parseColor("#FF7B39"));
        cropOptions.setStatusBarColor(Color.parseColor("#FF7B39"));
        cropOptions.setHideBottomControls(true);
        cropOptions.setCircleDimmedLayer(true);
        cropOptions.setShowCropGrid(false);
        cropOptions.setShowCropFrame(false);
        uCrop.withOptions(cropOptions);
        uCrop.withAspectRatio(1, 1); //比例
        uCrop.start(context);
    }
}
