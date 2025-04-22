package com.example.absensimahasiswa

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var btnTanggal: Button
    private lateinit var btnWaktu: Button
    private lateinit var btnAbsen: Button
    private lateinit var tvTanggal: TextView
    private lateinit var tvWaktu: TextView
    private lateinit var listAbsen: ListView

    private var selectedTanggal = ""
    private var selectedWaktu = ""
    private val riwayatAbsen = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNama = findViewById(R.id.etNama)
        btnTanggal = findViewById(R.id.btnTanggal)
        btnWaktu = findViewById(R.id.btnWaktu)
        btnAbsen = findViewById(R.id.btnAbsen)
        tvTanggal = findViewById(R.id.tvTanggal)
        tvWaktu = findViewById(R.id.tvWaktu)
        listAbsen = findViewById(R.id.listAbsen)

        // Adapter untuk ListView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, riwayatAbsen)
        listAbsen.adapter = adapter

        // Tanggal Picker
        btnTanggal.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this,
                { _, year, month, dayOfMonth ->
                    selectedTanggal = "$dayOfMonth/${month + 1}/$year"
                    tvTanggal.text = "Tanggal: $selectedTanggal"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        // Waktu Picker
        btnWaktu.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePicker = TimePickerDialog(this,
                { _, hour, minute ->
                    selectedWaktu = String.format("%02d:%02d", hour, minute)
                    tvWaktu.text = "Waktu: $selectedWaktu"
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePicker.show()
        }

        // Tombol Absen
        btnAbsen.setOnClickListener {
            val nama = etNama.text.toString().trim()

            if (nama.isEmpty() || selectedTanggal.isEmpty() || selectedWaktu.isEmpty()) {
                Toast.makeText(this, "Lengkapi semua input!", Toast.LENGTH_SHORT).show()
            } else {
                val catatan = "$nama - $selectedTanggal - $selectedWaktu"
                riwayatAbsen.add(catatan)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Absen berhasil!", Toast.LENGTH_SHORT).show()

                // Reset input
                etNama.text.clear()
                tvTanggal.text = "Tanggal belum dipilih"
                tvWaktu.text = "Waktu belum dipilih"
                selectedTanggal = ""
                selectedWaktu = ""
            }
        }
    }
}
