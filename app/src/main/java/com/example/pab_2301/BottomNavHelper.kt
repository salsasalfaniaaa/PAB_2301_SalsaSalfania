
package com.example.pab_2301

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun setupBottomNavigation(
    activity: AppCompatActivity,
    selectedItemId: Int
) {
    val bottomNav = activity.findViewById<BottomNavigationView>(
        R.id.bottomNavigation
    )

    // Menandai menu yang sedang aktif
    bottomNav.selectedItemId = selectedItemId

    // Mengatur perpindahan antarhalaman
    bottomNav.setOnItemSelectedListener { item ->

        if (item.itemId == selectedItemId) {
            return@setOnItemSelectedListener true
        }

        val tujuan = when (item.itemId) {

            R.id.nav_form -> MainActivity::class.java

            R.id.nav_hasil -> DuaActivity::class.java

            R.id.nav_informasi -> TigaActivity::class.java

            R.id.nav_profil -> EmpatActivity::class.java

            else -> null
        }

        if (tujuan != null) {

            val intent = Intent(activity, tujuan)

            intent.addFlags(
                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            )

            activity.startActivity(intent)
            true

        } else {
            false
        }
    }
}
