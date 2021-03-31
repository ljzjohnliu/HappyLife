package com.ilife.happy.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ilife.common.base.BaseFragment
import com.ilife.happy.R
import com.ilife.happy.bean.UserInfo
import com.ilife.happy.contract.IMineContract
import com.ilife.happy.presenter.MinePresenter

class MineFragment : BaseFragment<MinePresenter?, IMineContract.View<*>?>() {
    private var minePresenter: MinePresenter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mine, container, false)
        val textView = view.findViewById<View>(R.id.fragment_text_view) as TextView
        var bundle = getArguments()
        var s = bundle!!.getString("args")
        textView.setOnClickListener(View.OnClickListener {
            getPresenter().contract.personApi("arg1", "arg2")
        })
        textView.text = s
        return view
    }

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
}