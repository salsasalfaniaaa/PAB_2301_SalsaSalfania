
package com.example.pab_2301

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EmpatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empat)

        setupBottomNavigation(this, R.id.nav_profil)

        val btnFormProfil = findViewById<Button>(
            R.id.btnFormProfil
        )

        val btnHasilProfil = findViewById<Button>(
            R.id.btnHasilProfil
        )

        btnFormProfil.setOnClickListener {
            bukaHalaman(MainActivity::class.java)
        }

        btnHasilProfil.setOnClickListener {
            bukaHalaman(DuaActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()

        val prefs = getSharedPreferences(
            "DataPendaftaran",
            MODE_PRIVATE
        )

        val data = prefs.getString("hasil", "") ?: ""

        fun ambilNilai(label: String): String {
            return data.lineSequence()
                .firstOrNull { it.startsWith("$label:") }
                ?.substringAfter(":")
                ?.trim()
                .orEmpty()
        }

        val nama = ambilNilai("Nama")
        val jk = ambilNilai("Jenis Kelamin")
        val kota = ambilNilai("Kota Asal")
        val progress = ambilNilai("Progress")
            .removeSuffix("%")
            .toIntOrNull()
            ?.coerceIn(0, 100) ?: 0

        findViewById<TextView>(R.id.tvNamaHeader).text =
            if (nama.isBlank()) "Pengguna" else nama

        findViewById<TextView>(R.id.tvNamaProfil).text =
            "Nama Lengkap: ${nama.ifBlank { "-" }}"

        findViewById<TextView>(R.id.tvJenisKelaminProfil).text =
            "Jenis Kelamin: ${jk.ifBlank { "-" }}"

        findViewById<TextView>(R.id.tvKotaProfil).text =
            "Kota Asal: ${kota.ifBlank { "-" }}"

        findViewById<TextView>(R.id.tvStatusProfil).text =
            if (data.isBlank())
                "Status: Belum mendaftar"
            else
                "Status: Data tersimpan"

        findViewById<TextView>(R.id.tvProgressProfil).text =
            "Progress: $progress%"

        findViewById<ProgressBar>(R.id.pbProfil).progress =
            progress
    }

    private fun bukaHalaman(tujuan: Class<*>) {
        val intent = Intent(this, tujuan)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(intent)
    }
}
