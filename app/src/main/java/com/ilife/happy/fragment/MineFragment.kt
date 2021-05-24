package com.ilife.happy.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.ilife.common.basemvp.BaseFragment
import com.ilife.happy.R
import com.ilife.happy.activity.CenterFabActivity
import com.ilife.happy.activity.TestCustomViewActivity
import com.ilife.happy.bean.UserInfo
import com.ilife.happy.contract.IMineContract
import com.ilife.happy.presenter.MinePresenter

class MineFragment : BaseFragment<MinePresenter?, IMineContract.View<*>?>() {
    private var minePresenter: MinePresenter? = null

    override fun getContract(): IMineContract.View<*> {
        return IMineContract.View<UserInfo?> { userInfo ->
            Log.d("TAG", "onResult: userInfo = $userInfo")
        }
    }

    override fun getPresenter(): MinePresenter {
        if (minePresenter == null) {
            minePresenter = MinePresenter()
        }
        return minePresenter as MinePresenter
    }

    companion object {
        fun newInstance(s: String?): MineFragment {
            val homeFragment = MineFragment()
            val bundle = Bundle()
            bundle.putString("args", s)
            homeFragment.arguments = bundle
            return homeFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        val textView = mRootView.findViewById<View>(R.id.test_btn) as TextView
        textView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, TestCustomViewActivity::class.java)
            startActivity(intent)
        })
    }

    override fun initData() {
        //todo
    }
}