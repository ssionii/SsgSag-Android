package com.sopt.appjam_sggsag.Fragment.Info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.icoo.ssgsag_android.ui.walkthrough.WalkthroughActivity
import com.icoo.ssgsag_android.R

class FirstInfoFragment: androidx.fragment.app.Fragment(){
    private var firstInfoFragment: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        firstInfoFragment = inflater!!.inflate(R.layout.fragment_first_info, container, false)

        return firstInfoFragment
    }
}