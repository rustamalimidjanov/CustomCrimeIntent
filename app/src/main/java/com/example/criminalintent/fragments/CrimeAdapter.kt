//package com.example.criminalintent.fragments//package com.example.criminalintent
//
//import android.text.format.DateFormat
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//import com.example.criminalintent.app.CriminalIntentApplication
//import com.example.criminalintent.databinding.ListItemCrimeBinding
//import com.example.criminalintent.models.Crime
//import kotlin.coroutines.coroutineContext
//
//
///** ADAPTER */
//class CrimeAdapter(var crimes: List<Crime>) :
//    RecyclerView.Adapter<CrimeAdapter.CrimeHolder>() {
//
//    /** HOLDER */
//    class CrimeHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
//        lateinit var crime: Crime
//        private val binding = ListItemCrimeBinding.bind(view)
////        private var callbacks: Callbacks? = null
//
//
//        init {
//            itemView.setOnClickListener(this)
//        }
//
//        fun bind(crime: Crime) {
//            this.crime = crime
//            binding.crimeTitle.text = crime.title
//            binding.crimeDate.text =
//                DateFormat.format("EEE, d MMM, yyyy", this.crime.date).toString()
//            binding.crimeSolved.visibility = if (crime.isSolved) {
//                View.VISIBLE
//            } else View.GONE
//        }
//
//        override fun onClick(p0: View?) {
////            CrimeListFragment().callbacks?.onCrimeSelected(crimeId = crime.id)
//            Toast.makeText(CrimeListFragment().context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()
//        }
//
//
//    }
//
//    /** Return inflate layout */
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
//        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
//        val newView = inflater.inflate(R.layout.list_item_crime, parent, false)
//        return CrimeHolder(view = newView)
//    }
//
//    /** Fill u views using Holder */
//    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
//        val crime = crimes[position]
//
//        when (holder) {
//            is CrimeHolder -> holder.bind(crime = crime)
//        }
//    }
//
//    /** Return List size */
//    override fun getItemCount(): Int {
//        return crimes.size
//    }
//}