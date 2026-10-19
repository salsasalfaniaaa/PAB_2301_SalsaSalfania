
package com.example.pab_2301

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TigaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiga)

        setupBottomNavigation(this, R.id.nav_informasi)
    }
}
