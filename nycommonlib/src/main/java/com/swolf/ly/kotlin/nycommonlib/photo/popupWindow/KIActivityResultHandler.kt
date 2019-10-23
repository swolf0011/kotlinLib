package com.swolf.ly.kotlin.nycommonlib.photo.popupWindow

interface KIActivityResultHandler {
    /**
     * 相册..结果
     */
    abstract fun pickPhotoFromGallerySuccess()

    /**
     * 拍照..结果
     */
    abstract fun pickPhotoFromTakePhotoSuccess()

    /**
     * 裁剪图片
     */
    abstract fun photoZoomClipSuccess()

    /**
     * 取消
     */
    abstract fun cancel()

    /**
     * 失败
     */
    abstract fun fail()
}