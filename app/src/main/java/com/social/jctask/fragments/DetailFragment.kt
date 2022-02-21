package com.social.jctask.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.social.jctask.R
import com.social.jctask.adapter.CommentAdapter
import com.social.jctask.utils.doAsync
import com.social.jctask.viewmodel.CommentsViewModel
import org.koin.android.ext.android.inject


class DetailFragment : Fragment() {

    private val commentsViewModel : CommentsViewModel by inject()
    private lateinit var recyclerView        : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        recyclerView = view.findViewById(R.id.recyclerview)
        commentsViewModel.getAllCommentsAsLiveData().observe(
            viewLifecycleOwner,
            {
                if(!it.isNullOrEmpty()) {
                    activity?.runOnUiThread {
                        recyclerView.adapter = CommentAdapter(requireContext(),it)
                    }
                } else {
                    commentsViewModel.callCommentsApi(commentsViewModel.id)
                }
            }
        )
        return view
    }

    override fun onDestroy() {
        doAsync {
            commentsViewModel.deleteAll()
        }.execute()
        super.onDestroy()
    }



}