package com.bootcampteamc.odev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.bootcampteamc.FireService
import com.bootcampteamc.odev.data.ProfileData
import com.bootcampteamc.odev.ui.login.LoginActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        setSupportActionBar(toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val header = navView.getHeaderView(0)
        val imageView =header.findViewById<ImageView>(R.id.profile_image)

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val firestore = FirebaseFirestore.getInstance()

        uid?.let {
            firestore.collection("profileInfo").document(it).addSnapshotListener { value, error ->

                val profil = value?.toObject(ProfileData::class.java)
                val ref = FirebaseStorage.getInstance().reference.child(profil?.imageAddress.toString())

                Handler(Looper.getMainLooper()).postDelayed({
                    ref.downloadUrl.addOnSuccessListener {
                        Toast.makeText(this, "Photo Updated", Toast.LENGTH_SHORT).show()
                        Glide.with(this).load(it).centerCrop().into(imageView)
                    }
                        .addOnFailureListener {
                            Toast.makeText(this, it.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                        }
                }, 1000)
            }
        }

        val navHeaderTextView = header.findViewById<TextView>(R.id.textview_email)
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(navView,navController)
        toolbar.setupWithNavController(navController,appBarConfiguration)
        navHeaderTextView.text = FireService.getUserEmail()
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logOut -> {
                    FireService.logOut()
                    startActivity(Intent(this,LoginActivity::class.java))
                    this.finish()
                    true
                }
                R.id.profilePageFragment ->{
                    drawerLayout.closeDrawers()
                    navController.navigate(R.id.profilePageFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        if(item.itemId == R.id.logOut){
            return true
        }
        else{
            return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }
}