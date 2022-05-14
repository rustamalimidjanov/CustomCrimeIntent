package com.example.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.databinding.FragmentCrimeListBinding
import com.example.criminalintent.databinding.ListItemCrimeBinding
import com.example.criminalintent.databinding.ListItemCrimePoliceBinding


class CrimeListFragment : Fragment() {

    private lateinit var crimeRecyclerView: RecyclerView
    private lateinit var binding: FragmentCrimeListBinding
    private var adapter: CrimeAdapter? = null

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CrimeListFragment", "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        crimeRecyclerView =
            binding.crimeRecyclerView.findViewById(R.id.crime_recycler_view) as RecyclerView
//        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
//        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()
//        return view
        return binding.root
    }

    private fun updateUI() {
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter


    }

    companion object {
        fun newInstance(): CrimeListFragment = CrimeListFragment()
    }



    /** ADAPTER */

    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeAdapter.CrimeHolder>() {

        private val CHECK_TRUE = 1
        private val CHECK_FALSE = 0

        /** ABSTRACT HOLDER */
        private abstract inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        /** HOLDER */
        private inner class NormalCrimeHolder(view: View) : CrimeHolder(view),
            View.OnClickListener {

            lateinit var crime: Crime
            val bindingNotPolice = ListItemCrimeBinding.bind(view)
//            val bindingRequiresPolice = ListItemCrimePoliceBinding.bind(view)

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(crime: Crime) {
                this.crime = crime
                bindingNotPolice.crimeTitle.text = crime.title
                bindingNotPolice.crimeDate.text = crime.date.toString()
            }
//            fun bindP(crime: Crime) {
//                this.crime = crime
//                bindingRequiresPolice.crimeTitlePolice.text = crime.title
//                bindingRequiresPolice.crimeDatePolice.text = crime.date.toString()
//            }

            override fun onClick(p0: View?) {
                Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()
            }

        }

        /** HOLDER 2 */
        private inner class SeriousCrimeHolder(view: View) : CrimeHolder(view),
            View.OnClickListener {
//            val bindingNotPolice = ListItemCrimeBinding.bind(view)
            val bindingRequiresPolice = ListItemCrimePoliceBinding.bind(view)
            lateinit var crime: Crime

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(crime: Crime) {
                this.crime = crime
                bindingRequiresPolice.crimeTitle.text = crime.title
                bindingRequiresPolice.crimeDate.text = crime.date.toString()
                bindingRequiresPolice.crimeButton.setOnClickListener {
                    Toast.makeText(context, "This crime serious!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onClick(p0: View?) {
                Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()
            }

        }



        override fun getItemViewType(position: Int): Int {
            val crime = crimes[position]
            return when (crime.requiresPolice) {
                true -> CHECK_TRUE
                false -> CHECK_FALSE
            }
//           return super.getItemViewType(position)
        }

        /** Return inflate layout */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val viewNotPolice = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            val viewForPolice = layoutInflater.inflate(R.layout.list_item_crime_police, parent, false)

            return when (viewType) {
                CHECK_TRUE -> SeriousCrimeHolder(view = viewForPolice)
                else -> NormalCrimeHolder(view = viewNotPolice)
            }

        }

        /** Fill u views using Holder */
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
//            val type: Int = getItemViewType(position)
            when (holder) {
                is SeriousCrimeHolder ->  holder.bind(crime = crime)
                is NormalCrimeHolder -> holder.bind(crime = crime)
            }
        }

        /** Return List size */
        override fun getItemCount(): Int {
            return crimes.size
        }
    }
}