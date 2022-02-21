package com.social.jctask.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.social.jctask.adapter.ItemClick
import com.social.jctask.adapter.PostAdapter
import com.social.jctask.room.Post
import com.social.jctask.utils.FAVORITE
import com.social.jctask.utils.FAVORITE_RESULT
import com.social.jctask.utils.POST_ID
import com.social.jctask.viewmodel.CommentsViewModel
import com.social.jctask.viewmodel.FavoriteWorker
import com.social.jctask.viewmodel.PostViewModel
import org.koin.android.ext.android.inject
import com.social.jctask.R

class FavouritesFragment : Fragment() {

    val postViewModel: PostViewModel by inject()
    val commentsViewModel : CommentsViewModel by inject()
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvFavEmpty: TextView
    private val itemClick by lazy {
        object : ItemClick {
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

            override fun onItemClick(position: Int, post: Post) {
                findNavController().navigate(R.id.action_favouritesFragment_to_detailFragment)
                commentsViewModel.id = post.id.toString()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)



        recyclerView = view.findViewById(R.id.recyclerview)
        tvFavEmpty = view.findViewById(R.id.tv_fav_is_empty)

        postViewModel.getFavouriteAsLiveData().observe(
            viewLifecycleOwner,
            { posts ->
                if (!posts.isNullOrEmpty()) {
                    tvFavEmpty.visibility = View.GONE
                    activity?.runOnUiThread {
                        recyclerView.adapter = PostAdapter(requireContext(),posts ,itemClick)
                    }
                } else {
                    tvFavEmpty.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE

                }
            }
        )
        return view
    }

}