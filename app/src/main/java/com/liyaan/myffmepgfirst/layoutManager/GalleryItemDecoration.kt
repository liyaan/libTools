package com.liyaan.myffmepgfirst.layoutManager

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.Display
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.WindowManager
import com.liyaan.myffmepgfirst.utils.DensityUtil


class GalleryItemDecoration: RecyclerView.ItemDecoration(){

    /**
     * 自定义默认的Item的边距
     */
    private var mPageMargin = 10;
    /**
     * 第一张图片的左边距
     */
    private var mLeftPageVisibleWidth:Int = 0
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        //计算一下第一中图片距离屏幕左边的距离：(屏幕的宽度-item的宽度)/2。其中item的宽度=实际ImagView的宽度+margin。
        //我这里设置的ImageView的宽度为100+margin=110
        if (mLeftPageVisibleWidth == 0) {
            //计算一次就好了
            mLeftPageVisibleWidth = DensityUtil.px2dip(
                view.getContext(),
                (getScreenWidth(view.getContext()) - DensityUtil.dip2px( view.getContext(),110f)).toFloat()
            ) / 2
        }

        //获取当前Item的position
        val position = parent.getChildAdapterPosition(view)
        //获得Item的数量
        val itemCount = parent.adapter!!.itemCount
        val leftMagin: Float
        leftMagin = if (position == 0) {
            dpToPx(mLeftPageVisibleWidth)
        } else {
            dpToPx(mPageMargin)
        }
        val rightMagin: Float
        rightMagin = if (position == itemCount - 1) {
            dpToPx(mLeftPageVisibleWidth)
        } else {
            dpToPx(mPageMargin)
        }
        val layoutParams = view.getLayoutParams() as RecyclerView.LayoutParams

        //10,10分别是item到上下的margin
        layoutParams.setMargins(leftMagin.toInt(), 10, rightMagin.toInt(), 10)
        view.setLayoutParams(layoutParams)
        super.getItemOffsets(outRect, view, parent, state!!)
    }

    /**
     * d p转换成px
     * @param dp：
     */
    private fun dpToPx(dp: Int): Float {
        return (dp * Resources.getSystem().displayMetrics.density + 0.5f)
    }

    /**
     * 获取屏幕的宽度
     * @param context:
     * @return :
     */
    fun getScreenWidth(context: Context): Int {
        val manager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = manager.defaultDisplay
        return display.getWidth()
    }
}