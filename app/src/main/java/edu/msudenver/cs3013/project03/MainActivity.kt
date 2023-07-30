package edu.msudenver.cs3013.project03

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgument
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
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

        var passedUser = intent.getSerializableExtra("myUser") as User?
        Log.d("Main Username", passedUser?.username.toString())
        //Debug logs
//        Log.d("username", myUser.username)
//        Log.d("password", myUser.password)


        // Initialize the FavoritesViewModel using ViewModelProvider
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)


//        setSupportActionBar(findViewById(R.id.toolbar))
        drawerLayout = findViewById(R.id.drawer_layout)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController // Obtain the navController instance

        val graph = navController.navInflater.inflate(R.navigation.mobile_navigation)

// Retrieve the user data from the intent
        passedUser = intent.getSerializableExtra("myUser") as User?

// Set default arguments for each fragment destination in the navigation graph
        graph.addDefaultArguments(passedUser)

// Set the graph to the NavController
        navController.graph = graph

        //display a toast message with the passedUser.username.toString() displayed
        Toast.makeText(this, "Username: ${passedUser?.username}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "User: ${passedUser}", Toast.LENGTH_SHORT).show()

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
    private fun createBundle(user: User?): Bundle {
        val bundle = Bundle()
        bundle.putSerializable("myUser", user)


        return bundle
    }
    private fun NavGraph.addDefaultArguments(user: User?) {
        val bundle = Bundle()
        bundle.putSerializable("myUser", user)

        // Loop through each fragment destination in the graph and add default arguments
        for (destination in this) {
            if (destination is FragmentNavigator.Destination) {
                destination.addArgument("myUser", NavArgument.Builder().setDefaultValue(user).build())
            }
        }
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

