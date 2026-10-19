
package com.example.pab_2301

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Aktifkan Bottom Navigation
        setupBottomNavigation(this, R.id.nav_form)

        // Komponen form pendaftaran
        val edtNama = findViewById<EditText>(R.id.edtNama)
        val rgjk = findViewById<RadioGroup>(R.id.rgjk)
        val spKota = findViewById<Spinner>(R.id.spKota)
        val swMenyetujui = findViewById<Switch>(
            R.id.swMenyetujui
        )
        val skProgress = findViewById<SeekBar>(
            R.id.skProgress
        )
        val tvProgress = findViewById<TextView>(
            R.id.tvProgress
        )
        val btnSimpan = findViewById<Button>(
            R.id.btnSimpan
        )

        // Daftar kota
        val kota = arrayOf(
            "Pilih Kota",
            "Jakarta",
            "Bandung",
            "Surabaya",
            "Padang"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            kota
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spKota.adapter = adapter

        // Perubahan SeekBar
        tvProgress.text = "Progress: ${skProgress.progress}%"

        skProgress.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    tvProgress.text = "Progress: $progress%"
                }

                override fun onStartTrackingTouch(
                    seekBar: SeekBar?
                ) {}

                override fun onStopTrackingTouch(
                    seekBar: SeekBar?
                ) {}
            }
        )

        // Tombol menyimpan data
        btnSimpan.setOnClickListener {

            val nama = edtNama.text.toString().trim()

            if (nama.isEmpty()) {
                edtNama.error = "Nama wajib diisi"
                edtNama.requestFocus()
                return@setOnClickListener
            }

            val jk = when (rgjk.checkedRadioButtonId) {
                R.id.rbLaki -> "Laki-Laki"
                R.id.rbPerempuan -> "Perempuan"
                else -> ""
            }

            if (jk.isEmpty()) {
                Toast.makeText(
                    this,
                    "Pilih jenis kelamin",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (spKota.selectedItemPosition == 0) {
                Toast.makeText(
                    this,
                    "Pilih kota asal",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (!swMenyetujui.isChecked) {
                Toast.makeText(
                    this,
                    "Setujui S&K terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val dataHasil = """
                Nama: $nama
                Jenis Kelamin: $jk
                Kota Asal: ${spKota.selectedItem}
                Persetujuan S&K: Setuju
                Progress: ${skProgress.progress}%
            """.trimIndent()

            // Simpan hasil agar bisa dibuka dari menu Hasil
            getSharedPreferences(
                "DataPendaftaran",
                MODE_PRIVATE
            ).edit()
                .putString("hasil", dataHasil)
                .apply()

            // Buka halaman hasil pendaftaran
            val intent = Intent(
                this,
                DuaActivity::class.java
            )

            intent.addFlags(
                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            )

            startActivity(intent)
        }
    }
}
