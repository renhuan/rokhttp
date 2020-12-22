package com.renhuan.administrator.myokhttp

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.XPopupImageLoader
import com.lzy.ninegrid.ImageInfo
import com.lzy.ninegrid.NineGridView
import com.lzy.ninegrid.NineGridViewAdapter
import com.renhuan.okhttplib.base.RBaseFragment
import com.renhuan.okhttplib.utils.Renhuan.glide
import kotlinx.android.synthetic.main.fragment_blank.*
import me.jingbin.library.adapter.BaseByViewHolder
import me.jingbin.library.adapter.BaseRecyclerAdapter
import java.io.File
import kotlin.math.roundToInt
import kotlin.random.Random


/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : RBaseFragment() {


    companion object {
        @JvmStatic
        fun newInstance() = BlankFragment()
    }

    override fun inflaterLayout(): Int {
        return R.layout.fragment_blank
    }

    override fun lazyLoad() {
        byRecyclerView.adapter = object : BaseRecyclerAdapter<Int>(R.layout.item_list, arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)) {
            override fun bindView(holder: BaseByViewHolder<Int>?, bean: Int, position: Int) {
                val nineGridView = holder?.getView<NineGridView>(R.id.nineGrid)
                val list = arrayListOf<ImageInfo>()
                for (i in 0 until bean) {
                    list.add(
                            ImageInfo().apply {
                                setThumbnailUrl(getRandomImage())
                            })
                }
                nineGridView?.setAdapter(object : NineGridViewAdapter(requireActivity(), list) {
                    override fun onImageItemClick(context: Context?, nineGridView: NineGridView?, index: Int, imageInfo: MutableList<ImageInfo>?) {
                        super.onImageItemClick(context, nineGridView, index, imageInfo)
                        val imageView = nineGridView?.getChildAt(index) as ImageView
                        XPopup.Builder(requireActivity())
                                .asImageViewer(imageView, index, imageInfo?.map { it.getThumbnailUrl() }, false, false, -1, -1, -1, false,
                                        { popupView, position_ -> popupView.updateSrcView(nineGridView.getChildAt(position_) as ImageView) },
                                        object : XPopupImageLoader {
                                            override fun loadImage(position: Int, uri: Any, imageView: ImageView) {
                                                glide(imageView, uri.toString(), true)
                                            }

                                            override fun getImageFile(context: Context, uri: Any): File? {
                                                return null
                                            }
                                        })
                                .show()
                    }
                })
            }
        }
    }

    fun getRandomImage(): String {
        val list = arrayListOf(
                "http://browser9.qhimg.com/bdm/960_593_0/t01d2624f1672570e70.jpg",
                "http://browser9.qhimg.com/bdm/480_296_0/t010769fafba59eb5c4.jpg",
                "http://browser9.qhimg.com/bdm/960_593_0/t01a7117bbc9683a7eb.jpg",
                "http://browser9.qhimg.com/bdm/960_593_0/t0128e904ff207c846b.jpg",
                "http://browser9.qhimg.com/bdm/480_296_0/t01287bd180d28865b9.jpg",
                "http://browser9.qhimg.com/bdm/480_296_0/t01039b44f7c7ca5ca3.jpg",
                "http://browser9.qhimg.com/bdm/480_296_0/t01b1abb14c0861ddcf.jpg",
                "http://browser9.qhimg.com/bdm/480_296_0/t01886d1dfc1c53f6a2.jpg",
                "http://browser9.qhimg.com/bdm/960_593_0/t018491e279b96d0083.jpg",
                "http://browser9.qhimg.com/bdm/480_296_0/t017183d9c23dddbcb6.jpg",
                "http://browser9.qhimg.com/bdm/960_593_0/t0178ae93b5057eaf4c.jpg",
                "http://browser9.qhimg.com/bdm/960_593_0/t0125408ad40a560d4b.jpg"
        )
        return list[(Math.random() * list.size).toInt()]
    }

    override fun initView(view: View) {
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun initRequest() {
    }
}