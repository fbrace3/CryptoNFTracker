package edu.msudenver.cs3013.project03

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView

    // Declare a property to hold the FavoritesViewModel
    private lateinit var favoritesViewModel: FavoritesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the FavoritesViewModel using ViewModelProvider
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)


//        setSupportActionBar(findViewById(R.id.toolbar))
        drawerLayout = findViewById(R.id.drawer_layout)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val settingsFragment = navController.graph.findNode(R.id.nav_settings)
        // Navigate to the appropriate destination based on the source fragment
        navController.navigate(R.id.nav_menu)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_menu, R.id.nav_coin_fragment, R.id.nav_nft_fragment, R.id.nav_BankFinderFragment),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setupWithNavController(navController)

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()

            // Navigate to the selected destination
            when (menuItem.itemId) {
                R.id.nav_menu -> navController.navigate(R.id.nav_menu)
                R.id.nav_coin_fragment -> navController.navigate(R.id.nav_coin_fragment)
                R.id.nav_nft_fragment -> navController.navigate(R.id.nav_nft_fragment)
                R.id.nav_BankFinderFragment -> navController.navigate(R.id.nav_BankFinderFragment)
            }

            true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)

        // Log the clicked toolbar item ID for debugging purposes
        Log.d(TAG, "Toolbar item clicked: ${item.itemId}")

        // Let the Navigation component handle the selected item as a navigation action
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item)
    }

//    private fun createBundle(userName: String?): Bundle {
//        val bundle = Bundle()
//
//        // Put the user name into the bundle with a predefined key
//        bundle.putString(USER_NAME_KEY, userName)
//
//        // Log the user name value for debugging purposes
//        Log.d(TAG, "SendingMainActivity USER_NAME_KEY value: $USER_NAME_KEY")
//        Log.d(TAG, "SendingMainActivity Received userName: $userName")
//
//        // Store the user name in the global userData object for access throughout the app
//        if (userName != null) {
//            userData.username = userName
//        }
//
//        return bundle
//    }
}

