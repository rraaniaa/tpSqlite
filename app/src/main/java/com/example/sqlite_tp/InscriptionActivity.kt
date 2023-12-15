package com.example.sqlite_tp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class InscriptionActivity : AppCompatActivity() {

    private lateinit var etNom: EditText
    private lateinit var etPrenom: EditText
    private lateinit var etTel: EditText
    private lateinit var etEmail: EditText
    private lateinit var etMdp: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)

        etNom = findViewById(R.id.etNom)
        etPrenom = findViewById(R.id.etPrenom)
        etTel = findViewById(R.id.etTel)
        etEmail = findViewById(R.id.etEmail)
        etMdp = findViewById(R.id.etMdp)

        val btnValider: Button = findViewById(R.id.btnValider)
        val btnAnnuler: Button = findViewById(R.id.btnAnnuler)

        btnValider.setOnClickListener {
            if (champsNonVides()) {
                val dbHelper = EtudiantDBHelper(applicationContext)
                val db = dbHelper.writableDatabase

                val values = ContentValues().apply {
                    put(EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM, etNom.text.toString())
                    put(EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM, etPrenom.text.toString())
                    put(EtudiantBC.EtudiantEntry.COLUMN_NAME_PHONE, etTel.text.toString())
                    put(EtudiantBC.EtudiantEntry.COLUMN_NAME_EMAIL, etEmail.text.toString())
                    put(EtudiantBC.EtudiantEntry.COLUMN_NAME_MDP, etMdp.text.toString())
                }

                val newRowId = db.insert(EtudiantBC.EtudiantEntry.TABLE_NAME, null, values)

                db.close()
                dbHelper.close()

                if (newRowId != -1L) {
                    afficherMessage("Validation", "Inscription réussie!")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    afficherMessage("Erreur", "Une erreur s'est produite lors de l'inscription.")
                }
            } else {
                afficherMessage("Erreur", "Veuillez remplir tous les champs.")
            }
        }

        btnAnnuler.setOnClickListener {
            showResetConfirmationDialog()
        }
    }

    private fun champsNonVides(): Boolean {
        return etNom.text.isNotBlank() &&
                etPrenom.text.isNotBlank() &&
                etTel.text.isNotBlank() &&
                etEmail.text.isNotBlank() &&
                etMdp.text.isNotBlank()
    }

    private fun afficherMessage(titre: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(titre)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which -> dialog.dismiss() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showResetConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Attention")
        builder.setMessage("Voulez-vous vraiment remettre à zéro les champs ?")
        builder.setPositiveButton("Oui") { _, _ ->
            resetFields()
        }
        builder.setNegativeButton("Non") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun resetFields() {
        etNom.text.clear()
        etPrenom.text.clear()
        etTel.text.clear()
        etEmail.text.clear()
        etMdp.text.clear()
    }
}
