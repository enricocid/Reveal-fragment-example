package com.iven.awesometest.fragments

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.iven.awesometest.R
import com.iven.awesometest.ui.ThemeHelper
import kotlinx.android.synthetic.main.fragment_details.*
import java.util.*
import kotlin.math.max
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val REVEAL_DURATION: Long = 1000

class DetailsFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var mItemsDetailsView: View
    private lateinit var mItemsDetailsAnimator: Animator

    private lateinit var mDetailsToolbar: Toolbar


    private var mSelectedItem: String by Delegates.notNull()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.getString(TAG_SELECTED_ITEM)?.let {
            mSelectedItem = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    private fun onHandleBackPressed(): Animator {
        context?.let {
            revealFragment(it, false)
        }
        return mItemsDetailsAnimator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mItemsDetailsView = view
        mDetailsToolbar = details_toolbar

        context?.let {

            mDetailsToolbar.apply {

                title = mSelectedItem.toUpperCase(Locale.getDefault())
                subtitle = mSelectedItem.toLowerCase(Locale.getDefault())
                setNavigationOnClickListener {
                    onHandleBackPressed().doOnEnd {
                        activity?.onBackPressed()
                    }
                }
            }

            selected_artist.text = mSelectedItem

            view.afterMeasured {
                revealFragment(it, true)
            }
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    private fun revealFragment(context: Context, show: Boolean) {

        val radius = max(mItemsDetailsView.width, mItemsDetailsView.height).toFloat()

        val startRadius = if (show) 0f else radius
        val finalRadius = if (show) radius else 0f

        mItemsDetailsAnimator =
            ViewAnimationUtils.createCircularReveal(
                mItemsDetailsView,
                0,
                0,
                startRadius,
                finalRadius
            ).apply {
                interpolator = FastOutSlowInInterpolator()
                duration = REVEAL_DURATION
                start()
            }

        val accent = ContextCompat.getColor(context, R.color.indigo)
        val backgroundColor =
            ThemeHelper.resolveColorAttr(context, android.R.attr.windowBackground)
        val startColor = if (show) accent else backgroundColor
        val endColor = if (show) backgroundColor else accent

        startColorAnimation(
            mItemsDetailsView,
            startColor,
            endColor
        )
    }

    private fun startColorAnimation(
        view: View,
        startColor: Int,
        endColor: Int
    ) {
        ValueAnimator().apply {
            setIntValues(startColor, endColor)
            setEvaluator(ArgbEvaluator())
            addUpdateListener { valueAnimator -> view.setBackgroundColor((valueAnimator.animatedValue as Int)) }
            duration = REVEAL_DURATION
            start()
        }
    }

    companion object {

        const val TAG_SELECTED_ITEM = "SELECTED_ITEM"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MusicFragment.
         */
        @JvmStatic
        fun newInstance(
            selectedItem: String
        ) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(TAG_SELECTED_ITEM, selectedItem)
                }
            }
    }

    //viewTreeObserver extension to measure layout params
    //https://antonioleiva.com/kotlin-ongloballayoutlistener/
    private inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    f()
                }
            }
        })
    }
}
