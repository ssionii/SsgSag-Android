package com.icoo.ssgsag_android.ui.main.allPosters

import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.icoo.ssgsag_android.BR
import com.icoo.ssgsag_android.R
import com.icoo.ssgsag_android.base.BaseFragment
import com.icoo.ssgsag_android.base.BaseRecyclerViewAdapter
import com.icoo.ssgsag_android.data.model.poster.PosterCategory
import com.icoo.ssgsag_android.databinding.FragmentAllPosterBinding
import com.icoo.ssgsag_android.databinding.ItemAllPosterCategoryBinding
import com.icoo.ssgsag_android.ui.main.allPosters.category.AllCategoryActivity
import com.icoo.ssgsag_android.ui.main.allPosters.collection.AllPosterCollectionRecyclerViewAdapter
import com.icoo.ssgsag_android.util.extensionFunction.setSafeOnClickListener
import com.icoo.ssgsag_android.util.view.NonScrollGridLayoutManager
import com.icoo.ssgsag_android.util.view.WrapContentLinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllPostersFragment : BaseFragment<FragmentAllPosterBinding, AllPostersViewModel>(),
    CardViewPagerAdapter.OnItemClickListener, BaseRecyclerViewAdapter.OnItemClickListener{

    override val layoutResID: Int
        get() = R.layout.fragment_all_poster
    override val viewModel: AllPostersViewModel by viewModel()

    lateinit private var allPosterCollectionRvAdapter : AllPosterCollectionRecyclerViewAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.vm = viewModel
        
        setTopButtonRv()
        setCollectionRv()
//        setViewPager()
        navigator()
    }

    private fun setTopButtonRv(){

        val categoryList = listOf(
            PosterCategory(1, "대외활동", resources.getColor(R.color.categoryActBg), resources.getColor(R.color.categoryActText), resources.getDrawable(R.drawable.ic_category_activity)),
            PosterCategory(0, "공모전", resources.getColor(R.color.categoryContestBg), resources.getColor(R.color.categoryContestText), resources.getDrawable(R.drawable.ic_category_contest)),
            PosterCategory(4, "인턴", resources.getColor(R.color.categoryInternBg), resources.getColor(R.color.categoryInternText), resources.getDrawable(R.drawable.ic_category_intern)),
            PosterCategory(2, "동아리", resources.getColor(R.color.categoryClubBg), resources.getColor(R.color.categoryClubText), resources.getDrawable(R.drawable.ic_category_club)),
            PosterCategory(7, "교육/강연", resources.getColor(R.color.categoryEduBg), resources.getColor(R.color.categoryEduText), resources.getDrawable(R.drawable.ic_category_edu)),
            PosterCategory(5, "기타", resources.getColor(R.color.categoryEtcBg), resources.getColor(R.color.categoryEtcText), resources.getDrawable(R.drawable.ic_category_etc))
        )

        viewDataBinding.fragAllPosterTopRv.run{
            adapter = object : BaseRecyclerViewAdapter<PosterCategory, ItemAllPosterCategoryBinding>(){
                override val layoutResID: Int
                    get() = R.layout.item_all_poster_category
                override val bindingVariableId: Int
                    get() = BR.posterCategory
                override val listener: OnItemClickListener?
                    get() = this@AllPostersFragment
            }

            layoutManager = NonScrollGridLayoutManager(activity!!, 3)


            (adapter as BaseRecyclerViewAdapter<PosterCategory, *>).run{
                replaceAll(categoryList)
                notifyDataSetChanged()
            }
        }
    }

    override fun onItemClicked(item: Any?, position: Int?) {
        val intent = Intent(activity!!, AllCategoryActivity::class.java)
        intent.putExtra("category", (item as PosterCategory).categoryIdx)

        startActivity(intent)
    }



    private fun setCollectionRv(){

        allPosterCollectionRvAdapter = AllPosterCollectionRecyclerViewAdapter()
        viewDataBinding.fragAllPosterPostersCollectionRv.run {
            adapter = allPosterCollectionRvAdapter

            layoutManager = WrapContentLinearLayoutManager()
        }

        viewModel.posterList.observe(this, Observer {
            Log.e("posterList", it[0].categoryName)
            allPosterCollectionRvAdapter.run{
                replaceAll(it)
                notifyDataSetChanged()
            }
        })



    }

//
//    private fun setViewPager(){
//
//        val d = resources.displayMetrics.density
//
//        // 화면 전체 사이즈
//        val display = activity!!.windowManager.defaultDisplay
//        val size = Point()
//        display.getSize(size)
//        val width = (size.x / d).toInt()
//
//        val contentDpValue = 180
//        val leftDpValue = 26
//        val middleDpValue = 15
//        val rightDpValue = width - (leftDpValue + contentDpValue + middleDpValue)
//
//        val leftMargin = (leftDpValue * d).toInt()
//        val rightMargin = (rightDpValue * d).toInt()
//        val middleMargin = (middleDpValue * d).toInt()
//
//        viewModel.clubPosterList.observe(this, Observer {value ->
//            Log.e("clubPosterList observer", "들어옴")
//
//            var cardViewPagerAdapter = CardViewPagerAdapter(activity!!, value)
//            cardViewPagerAdapter.setOnItemClickListener(this)
//
//            if(value.size > 0) {
//                viewDataBinding.fragAllPosterVpClub.run {
//
//                    clipToPadding = false
//                    setPadding(leftMargin, 20, rightMargin, 30)
//                    pageMargin = middleMargin
//
//
//                    adapter = cardViewPagerAdapter
//
//                }
//            }else {
//                viewDataBinding.fragAllPosterLlClubContainer.visibility = GONE
//                viewDataBinding.fragAllPosterVpClub.visibility = GONE
//            }
//        })
//
//
//        viewModel.actPosterList.observe(this, Observer {value ->
//
//            var cardViewPagerAdapter = CardViewPagerAdapter(activity!!, value)
//            cardViewPagerAdapter.setOnItemClickListener(this)
//
//            if(value.size > 0) {
//                viewDataBinding.fragAllPosterVpAct.run {
//
//                    clipToPadding = false
//                    setPadding(leftMargin, 20, rightMargin, 30)
//                    pageMargin = middleMargin
//
//
//                    adapter = cardViewPagerAdapter
//
//                }
//            }else {
//                viewDataBinding.fragAllPosterLlActContainer.visibility = GONE
//                viewDataBinding.fragAllPosterVpAct.visibility = GONE
//            }
//        })
//
//        viewModel.contestPosterList.observe(this, Observer {value ->
//
//            var cardViewPagerAdapter = CardViewPagerAdapter(activity!!, value)
//            cardViewPagerAdapter.setOnItemClickListener(this)
//
//            if(value.size > 0) {
//                viewDataBinding.fragAllPosterVpContest.run {
//                    clipToPadding = false
//                    setPadding(leftMargin, 20, rightMargin, 30)
//                    pageMargin = middleMargin
//
//                    adapter = cardViewPagerAdapter
//
//                }
//            }else {
//                viewDataBinding.fragAllPosterLlContestContainer.visibility = GONE
//                viewDataBinding.fragAllPosterVpContest.visibility = GONE
//            }
//        })
//
//        viewModel.internPosterList.observe(this, Observer {value ->
//            var cardViewPagerAdapter = CardViewPagerAdapter(activity!!, value)
//            cardViewPagerAdapter.setOnItemClickListener(this)
//
//            if(value.size > 0) {
//                viewDataBinding.fragAllPosterVpIntern.run {
//                    clipToPadding = false
//                    setPadding(leftMargin, 20, rightMargin, 30)
//                    pageMargin = middleMargin
//
//                    adapter = cardViewPagerAdapter
//
//                }
//            }else {
//                viewDataBinding.fragAllPosterLlInternContainer.visibility = GONE
//                viewDataBinding.fragAllPosterVpIntern.visibility = GONE
//            }
//        })
//        viewModel.educationPosterList.observe(this, Observer {value ->
//            var cardViewPagerAdapter = CardViewPagerAdapter(activity!!, value)
//            cardViewPagerAdapter.setOnItemClickListener(this)
//
//            if(value.size > 0) {
//                viewDataBinding.fragAllPosterVpEducation.run {
//                    clipToPadding = false
//                    setPadding(leftMargin, 20, rightMargin, 30)
//                    pageMargin = middleMargin
//
//                    adapter = cardViewPagerAdapter
//
//                }
//            }else {
//                viewDataBinding.fragAllPosterLlEducationContainer.visibility = GONE
//                viewDataBinding.fragAllPosterVpEducation.visibility = GONE
//            }
//        })
//
//        viewModel.etcPosterList.observe(this, Observer {value ->
//            var cardViewPagerAdapter = CardViewPagerAdapter(activity!!, value)
//            cardViewPagerAdapter.setOnItemClickListener(this)
//            if(value.size > 0) {
//                viewDataBinding.fragAllPosterLlEtcContainer.visibility = VISIBLE
//                viewDataBinding.fragAllPosterVpEtc.run {
//                    clipToPadding = false
//                    setPadding(leftMargin, 20, rightMargin, 30)
//                    pageMargin = middleMargin
//
//                    adapter = cardViewPagerAdapter
//
//                }
//            }else {
//                viewDataBinding.fragAllPosterLlEtcContainer.visibility = GONE
//                viewDataBinding.fragAllPosterVpEtc.visibility = GONE
//            }
//        })
//
//    }

    override fun onItemClick(posterIdx: Int) {
        viewModel.navigate(posterIdx)
    }

    private fun navigator() {
        viewModel.activityToStart.observe(this, Observer { value ->
            val intent = Intent(activity, value.first.java)
            value.second?.let {
                intent.putExtras(it)
            }
            view!!.context.startActivity(intent)

        })
    }

    companion object {
        fun newInstance(): AllPostersFragment {
            val fragment = AllPostersFragment()
            return fragment
        }
    }
}