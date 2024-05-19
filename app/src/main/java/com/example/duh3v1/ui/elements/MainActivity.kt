package com.example.duh3v1.ui.elements

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.duh3v1.R
import com.example.duh3v1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var binding: ActivityMainBinding
    private lateinit var navMenuToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//      ActionBar codes // Fragment management
        val actionBar = supportActionBar
        if (actionBar!=null){
            actionBar.displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
            actionBar.setCustomView(R.layout.action_bar_layout)
        }
        if (savedInstanceState==null){
            replaceFragment(DashboardFragment())
        }
        navMenuToggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(navMenuToggle)
        navMenuToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navMenuId1 -> {
                    replaceFragment(DashboardFragment())
                    Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show()
                }
                R.id.navMenuId2 -> {
                    replaceFragment(EventFragment())
                    Toast.makeText(this, "Events", Toast.LENGTH_SHORT).show()
                }
                R.id.navMenuId3 -> {
                    replaceFragment(PantryFragment())
                    Toast.makeText(this, "Pantry", Toast.LENGTH_SHORT).show()
                }
                R.id.navMenuId4 -> {
                    replaceFragment(RecipeFragment())
                    Toast.makeText(this, "Recipes", Toast.LENGTH_SHORT).show()
                }
                R.id.navMenuId5 -> {
                    replaceFragment(SettingsFragment())
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }


////      Firebase codes
//        auth = Firebase.auth
//        if (auth.currentUser!=null){
//            user = auth.currentUser!!
//            binding.tvDummyMain.text = user.email
//        }
//        else{
//            jumpToLogin()
//        }
//
//        binding.btnLogoutMain.setOnClickListener {
//            Firebase.auth.signOut()
//            jumpToLogin()
//        }
    }
    private fun jumpToLogin(){
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (navMenuToggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment){
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragmentHolderMain,fragment)
        ft.commit()
    }
}