package com.social.jctask.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.social.jctask.adapter.PostAdapter
import com.social.jctask.room.Post
import com.social.jctask.utils.Communicator
import com.social.jctask.viewmodel.CommentsViewModel
import com.social.jctask.adapter.ItemClick
import com.social.jctask.utils.FAVORITE
import com.social.jctask.utils.FAVORITE_RESULT
import com.social.jctask.utils.POST_ID
import com.social.jctask.viewmodel.FavoriteWorker
import com.social.jctask.viewmodel.PostViewModel
import org.koin.android.ext.android.inject
import com.social.jctask.R

class PostsFragment : Fragment() {

    val postViewModel : PostViewModel by inject()
    val commentsViewModel: CommentsViewModel by inject()
    private lateinit var recyclerView: RecyclerView
    private lateinit var communicator: Communicator
    private var postAdapter: PostAdapter? = null
    private val itemClick by lazy {
        object : ItemClick {
            override fun onItemClick(position: Int, post: Post) {
                findNavController().navigate(R.id.action_postsFragment_to_detailFragment)
                commentsViewModel.id = post.id.toString()
            }

            override fun favoriteClick(imageView: ImageView, position: Int, post: Post) {
                postViewModel.addOrRemoveFavourite(post)
                val workManager = WorkManager.getInstance(requireContext())
                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
                val data = Data.Builder()
                    .putInt(POST_ID, post.id)
                    .putBoolean(FAVORITE,post.isFavorite == true)
                    .build()

                val favoriteWorker = OneTimeWorkRequestBuilder<FavoriteWorker>()
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build()
                workManager.enqueue(favoriteWorker)
                workManager.getWorkInfoByIdLiveData(favoriteWorker.id)
                    .observe(viewLifecycleOwner,  { workInfo ->

                        if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                            val userEmotionResult = workInfo.outputData.getString(FAVORITE_RESULT)
                            Toast.makeText(requireContext(), userEmotionResult, Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_posts, container, false)

        recyclerView = view.findViewById(R.id.recyclerview)


        communicator = activity as Communicator

        postViewModel.getAllPostAsLiveData().observe(
            viewLifecycleOwner,
            { posts ->
                if (!posts.isNullOrEmpty()) {
                    Handler(Looper.getMainLooper()).post {
                        postAdapter = PostAdapter(requireContext(),posts,itemClick)
                        recyclerView.adapter =postAdapter
                    }
                } else {
                    postViewModel.callPostsApi()
                }
            }
        )
        return view
    }

}