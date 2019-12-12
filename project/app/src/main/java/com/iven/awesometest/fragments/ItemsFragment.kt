package com.iven.awesometest.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.recyclical.datasource.DataSource
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.iven.awesometest.R
import com.iven.awesometest.ui.GenericViewHolder
import com.iven.awesometest.ui.UIControlInterface
import kotlinx.android.synthetic.main.fragment_items.*


/**
 * A simple [Fragment] subclass.
 * Use the [ItemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemsFragment : Fragment(), SearchView.OnQueryTextListener {

    //views
    private lateinit var mItemsRecyclerView: RecyclerView

    private var mItems =
        listOf(
            "Hello!",
            "Bye bye",
            "How are You?",
            "I'm a pasta enthusiast",
            "Goodbye :(",
            "Imma sue You",
            "A bello",
            "I'm a pasta enthusiast",
            "I'm an amateur",
            "Circular reveal me!"
        )

    private lateinit var mDataSource: DataSource<Any>

    private lateinit var mUIControlInterface: UIControlInterface


    override fun onAttach(context: Context) {
        super.onAttach(context)

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mUIControlInterface = activity as UIControlInterface
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mItemsRecyclerView = items_rv

        mDataSource = dataSourceOf(mItems)

        context?.let {

            mItemsRecyclerView.apply {

                // setup{} is an extension method on RecyclerView
                setup {

                    // item is a `val` in `this` here
                    withDataSource(mDataSource)
                    withItem<String, GenericViewHolder>(R.layout.generic_item) {

                        onBind(::GenericViewHolder) { _, item ->
                            // GenericViewHolder is `this` here
                            title.text = item
                            subtitle.text = item
                        }

                        onClick {
                            if (::mUIControlInterface.isInitialized)
                                mUIControlInterface.onItemSelected(item)
                        }
                    }
                }
            }
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MusicFragment.
         */
        @JvmStatic
        fun newInstance() = ItemsFragment()
    }
}
