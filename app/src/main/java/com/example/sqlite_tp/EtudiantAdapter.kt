package com.example.sqlite_tp

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

// ... (import statements)

class EtudiantAdapter(private val etudiants: List<EtudiantBC>) : RecyclerView.Adapter<EtudiantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_etudiant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val etudiant = etudiants[position]
        holder.bind(etudiant)
    }

    override fun getItemCount(): Int {
        return etudiants.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNom: TextView = itemView.findViewById(R.id.textViewNom)
        private val textViewPrenom: TextView = itemView.findViewById(R.id.textViewPrenom)
        private val textViewPhone: TextView = itemView.findViewById(R.id.textViewPhone)
        private val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)

        fun bind(etudiant: EtudiantBC) {
            textViewNom.text = "Nom : ${etudiant.nom} "
            textViewPrenom.text = "Prenom : ${etudiant.prenom} "
            textViewPhone.text = "Phone : ${etudiant.phone}"
            textViewEmail.text = "Email : ${etudiant.email}"
        }
    }
}
