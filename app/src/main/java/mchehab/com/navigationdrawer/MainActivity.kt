package mchehab.com.navigationdrawer

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val cameraFragment = CameraFragment()
    val galleryFragment = GalleryFragment()
    val sendFragment = SendFragment()
    val shareFragment = ShareFragment()
    val slideShowFragment = SlideShowFragment()
    val toolsFragment = ToolsFragment()

    var currentMenuItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        currentMenuItem = R.id.nav_camera//default value set to first item

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        if(savedInstanceState == null){
            addFragment(cameraFragment)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var selectedFragment = Fragment()
        if(item.itemId == currentMenuItem){
            drawerLayout.closeDrawer(GravityCompat.START)
            return false
        }
        when (item.itemId) {
            R.id.nav_camera -> selectedFragment = cameraFragment
            R.id.nav_gallery -> selectedFragment = galleryFragment
            R.id.nav_slideshow -> selectedFragment = slideShowFragment
            R.id.nav_manage -> selectedFragment = toolsFragment
            R.id.nav_share -> selectedFragment = shareFragment
            R.id.nav_send -> selectedFragment = sendFragment
        }
        currentMenuItem = item.itemId
        replaceFragment(selectedFragment)

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun addFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragment).commit()
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment)
                .addToBackStack(null).commit()
    }
}