package com.tolganacar.kekodego.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.tolganacar.kekodego.HomeViewModel
import com.tolganacar.kekodego.MainActivity
import com.tolganacar.kekodego.R
import com.tolganacar.kekodego.data.SwitchItem
import com.tolganacar.kekodego.databinding.FragmentHomeBinding
import com.tolganacar.kekodego.utils.navigateSafe

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private var rootView: View? = null
    private lateinit var animationDrawable: AnimationDrawable

    private val homeViewModel: HomeViewModel by viewModels()

    private val switchItemMappings by lazy {
        listOf(
            SwitchItem(binding.switchHappiness, R.id.happinessFragment, "Happiness", R.drawable.happiness),
            SwitchItem(binding.switchOptimism, R.id.optimismFragment, "Optimism", R.drawable.optimism),
            SwitchItem(binding.switchKindness, R.id.kindnessFragment, "Kindness", R.drawable.kindness),
            SwitchItem(binding.switchGiving, R.id.givingFragment, "Giving", R.drawable.giving),
            SwitchItem(binding.switchRespect, R.id.respectFragment, "Respect", R.drawable.respect)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        if(rootView == null) {
            rootView = binding.root
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackgroundAnimation()
        initializeBottomNavBar()
        setupSwitchListeners()
        setupBottomNavBarListener()

        binding.switchEgo.isChecked = homeViewModel.isEgoChecked

    }

    private fun setBackgroundAnimation() {
        animationDrawable = binding.mainLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2500)
        animationDrawable.setEnterFadeDuration(5000)
        animationDrawable.start()
    }

    private fun setupSwitchListeners() {
        binding.switchEgo.setOnCheckedChangeListener { _, isChecked ->
            homeViewModel.isEgoChecked = isChecked  // ViewModel'e durumu kaydet
            if (isChecked) {
                disableOtherViews()
            } else {
                enableOtherViews()
            }
        }

        switchItemMappings.forEachIndexed { index, item ->
            item.switch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (binding.switchEgo.isChecked) {
                        disableOtherViews()
                    } else {
                        if (bottomNavigationView.menu.size() >= 5) {
                            showToastForExtraItem(item)
                            item.switch.isChecked = false
                        } else if (bottomNavigationView.menu.size() < 5) {
                            addBottomNavItem(item.fragmentId, item.title, item.iconRes)
                        } else {
                            showToastForExtraItem(item)
                        }
                    }
                } else {
                    removeBottomNavItem(item.fragmentId)
                }
            }
        }
    }

    private fun disableOtherViews() {
        switchItemMappings.forEach { item ->
            item.switch.isChecked = false
        }
        bottomNavigationView.visibility = View.GONE
    }

    private fun enableOtherViews() {
        switchItemMappings.forEach { item ->
            item.switch.isEnabled = true
        }
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun initializeBottomNavBar() {
        bottomNavigationView = (activity as MainActivity).bottomNavView()
        navController = (activity as MainActivity).navController()
        addBottomNavItem(R.id.homeFragment, "Home", R.drawable.ic_home)
    }

    private fun addBottomNavItem(fragmentId: Int, title: String, iconRes: Int) {
        if (bottomNavigationView.menu.findItem(fragmentId) == null) {
            bottomNavigationView.menu.add(Menu.NONE, fragmentId, Menu.NONE, title).setIcon(iconRes)
        }
    }

    private fun removeBottomNavItem(fragmentId: Int) {
        bottomNavigationView.menu.removeItem(fragmentId)
    }

    private fun setupBottomNavBarListener() {
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            navController.navigateSafe(menuItem.itemId)
            true
        }
    }

    private fun showToastForExtraItem(item: SwitchItem) {
        rootView?.let {
            Snackbar.make(it, "${item.title} cannot be added to the BottomNavigationView.", Snackbar.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(context, "${item.title} cannot be added to the BottomNavigationView.", Toast.LENGTH_SHORT).show()
        }
    }
}