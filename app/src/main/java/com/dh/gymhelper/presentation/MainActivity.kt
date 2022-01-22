package com.dh.gymhelper.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dh.gymhelper.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        val navController = Navigation.findNavController(this@MainActivity, R.id.fragment)
        initBottomBar(navController = navController)

        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    return if (viewModel.loggedIn != null) {
                        if (viewModel.loggedIn == true) {
                            navController.popBackStack(R.id.welcomeFragment, true)
                            navController.navigate(R.id.dashboardFragment)
                        } else {
                            navController.popBackStack(R.id.welcomeFragment, false)
                        }
                        // The content is ready; start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready; suspend.
                        false
                    }
                }
            }
        )
    }

    private fun initBottomBar(navController: NavController) {
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottom_app_bar)

        val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setAllCorners(RoundedCornerTreatment()).setAllCornerSizes(RelativeCornerSize(0.50f))
            .build()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        visibilityNavElements(navController, bottomAppBar)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_graph_home -> {
                    Toast.makeText(applicationContext, "Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_graph_trainings -> {
                    Toast.makeText(applicationContext, "Trainings", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_graph_stats -> {
                    Toast.makeText(applicationContext, "Stats", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun visibilityNavElements(navController: NavController, bottomAppBar: BottomAppBar) {
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.dashboardFragment -> showAppBar(bottomAppBar, fab)
                R.id.welcomeFragment -> hideAppBar(bottomAppBar, fab)
                R.id.signUpFragment -> hideAppBar(bottomAppBar, fab)
                else -> showAppBar(bottomAppBar, fab)
            }
        }
    }

    private fun hideAppBar(bottomAppBar: BottomAppBar, fab: FloatingActionButton) {
        bottomAppBar.visibility = View.GONE
        fab.visibility = View.GONE
    }

    private fun showAppBar(bottomAppBar: BottomAppBar, fab: FloatingActionButton) {
        bottomAppBar.visibility = View.VISIBLE
        fab.visibility = View.VISIBLE
    }

}