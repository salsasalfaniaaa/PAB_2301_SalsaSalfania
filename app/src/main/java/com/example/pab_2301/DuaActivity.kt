
package com.example.pab_2301

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DuaActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var pbLoading: ProgressBar
    private lateinit var tvHasil: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dua)

        setupBottomNavigation(this, R.id.nav_hasil)

        pbLoading = findViewById(R.id.pbLoading)
        tvHasil = findViewById(R.id.tvHasil)
    }

    override fun onResume() {
        super.onResume()

        handler.removeCallbacksAndMessages(null)

        // Membaca data yang sudah disimpan
        val dataHasil = getSharedPreferences(
            "DataPendaftaran",
            MODE_PRIVATE
        ).getString(
            "hasil",
            "Belum ada data pendaftaran.\nSilakan isi form terlebih dahulu."
        )

        pbLoading.visibility = View.VISIBLE
        tvHasil.visibility = View.GONE

        // Loading selama 2 detik
        handler.postDelayed({

            pbLoading.visibility = View.GONE
            tvHasil.text = dataHasil
            tvHasil.visibility = View.VISIBLE

        }, 2000)
    }

    override fun onPause() {
        handler.removeCallbacksAndMessages(null)
        super.onPause()
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
