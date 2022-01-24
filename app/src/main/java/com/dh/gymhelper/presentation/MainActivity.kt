package com.dh.gymhelper.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private  val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.fab_add_rotate_to_close) }
    private  val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.fab_close_rotate_to_add) }
    private  val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.fab_from_bottom) }
    private  val toBottom : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.fab_to_bottom) }
    private var closed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        val navController = Navigation.findNavController(this@MainActivity, R.id.fragment)
        initBottomBar(navController = navController)

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            onAddFabClicked()
        }

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

    private fun onAddFabClicked() {
        setVisibility(closed)
        setAnimation(closed)
        closed = !closed
    }

    private fun setAnimation(closed:Boolean) {
        if(!closed){
            findViewById<FloatingActionButton>(R.id.fab_training).startAnimation(fromBottom)
            findViewById<MaterialTextView>(R.id.add_training).startAnimation(fromBottom)
            findViewById<FloatingActionButton>(R.id.fab_exercise_set).startAnimation(fromBottom)
            findViewById<MaterialTextView>(R.id.add_exercise_set).startAnimation(fromBottom)
            findViewById<FloatingActionButton>(R.id.fab_personal_best).startAnimation(fromBottom)
            findViewById<MaterialTextView>(R.id.add_personal_best).startAnimation(fromBottom)
            findViewById<FloatingActionButton>(R.id.fab).animate().rotationBy(315f)
        }else{
            findViewById<FloatingActionButton>(R.id.fab_training).startAnimation(toBottom)
            findViewById<MaterialTextView>(R.id.add_training).startAnimation(toBottom)
            findViewById<FloatingActionButton>(R.id.fab_exercise_set).startAnimation(toBottom)
            findViewById<MaterialTextView>(R.id.add_exercise_set).startAnimation(toBottom)
            findViewById<FloatingActionButton>(R.id.fab_personal_best).startAnimation(toBottom)
            findViewById<MaterialTextView>(R.id.add_personal_best).startAnimation(toBottom)
            findViewById<FloatingActionButton>(R.id.fab).animate().rotationBy(-315f)
        }
    }

    private fun setVisibility(closed:Boolean) {
        if(!closed) {
            fabMenuVisible()
         } else{
            fabMenuGone()
        }
    }

    private fun fabMenuGone() {
        findViewById<FloatingActionButton>(R.id.fab_training).visibility = View.GONE
        findViewById<MaterialTextView>(R.id.add_training).visibility = View.GONE
        findViewById<FloatingActionButton>(R.id.fab_exercise_set).visibility = View.GONE
        findViewById<MaterialTextView>(R.id.add_exercise_set).visibility = View.GONE
        findViewById<FloatingActionButton>(R.id.fab_personal_best).visibility = View.GONE
        findViewById<MaterialTextView>(R.id.add_personal_best).visibility = View.GONE
    }

    private fun fabMenuVisible() {
        findViewById<FloatingActionButton>(R.id.fab_training).visibility = View.VISIBLE
        findViewById<MaterialTextView>(R.id.add_training).visibility = View.VISIBLE
        findViewById<FloatingActionButton>(R.id.fab_exercise_set).visibility = View.VISIBLE
        findViewById<MaterialTextView>(R.id.add_exercise_set).visibility = View.VISIBLE
        findViewById<FloatingActionButton>(R.id.fab_personal_best).visibility = View.VISIBLE
        findViewById<MaterialTextView>(R.id.add_personal_best).visibility = View.VISIBLE
    }

    private fun initBottomBar(navController: NavController) {
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottom_app_bar)

        val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setAllCorners(RoundedCornerTreatment()).setAllCornerSizes(RelativeCornerSize(0.5f))
            .build()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        visibilityNavElements(navController, bottomAppBar)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
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
                R.id.profileFragment -> hideAppBar(bottomAppBar, fab)
                else -> showAppBar(bottomAppBar, fab)
            }
        }
    }

    private fun hideAppBar(bottomAppBar: BottomAppBar, fab: FloatingActionButton) {
        bottomAppBar.visibility = View.GONE
        fab.visibility = View.GONE
        fabMenuGone()
    }

    private fun showAppBar(bottomAppBar: BottomAppBar, fab: FloatingActionButton) {
        bottomAppBar.visibility = View.VISIBLE
        fab.visibility = View.VISIBLE
    }

}