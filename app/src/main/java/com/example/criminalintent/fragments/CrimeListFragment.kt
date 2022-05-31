package com.example.criminalintent.fragments


import android.content.Context
import android.opengl.Visibility
import android.os.Bundle

import android.text.format.DateFormat
import android.view.*
import android.widget.Button
import android.widget.ImageButton

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.criminalintent.CrimeListViewModel
import com.example.criminalintent.R
import com.example.criminalintent.databinding.FragmentCrimeListBinding
import com.example.criminalintent.databinding.ListItemCrimeBinding
import com.example.criminalintent.models.Crime


class CrimeListFragment : Fragment() {
    private var callbacks: Callbacks? = null
    private lateinit var crimeRecyclerView: RecyclerView
    private lateinit var binding: FragmentCrimeListBinding
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCrimeListBinding.inflate(inflater, container, false)

        crimeRecyclerView =
            binding.crimeRecyclerView.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    updateUI(crimes = crimes)
                }
            }
        )
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_crime -> {
                val crime = Crime()
                crimeListViewModel.addCrime(crime = crime)
                callbacks?.onCrimeSelected(crimeId = crime.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun updateUI(crimes: List<Crime>) {
        val imButton = view?.findViewById<Button>(R.id.button)
        val recyclers = binding.crimeRecyclerView
        val emptyConst = binding.emptyView
        val bottomButton = binding.addCrimeButton
        val crime = Crime()

        if (crimes.isEmpty()) {
            recyclers.visibility = View.INVISIBLE
            emptyConst.visibility = View.VISIBLE
            imButton?.setOnClickListener {
                crimeListViewModel.addCrime(crime = crime)
                callbacks?.onCrimeSelected(crimeId = crime.id)
            }
        } else {
            recyclers.visibility = View.VISIBLE
            emptyConst.visibility = View.INVISIBLE
        }

        if (crimes.size in 1..4) {
            bottomButton.visibility = View.VISIBLE
            bottomButton.setOnClickListener {
                crimeListViewModel.addCrime(crime = crime)
                callbacks?.onCrimeSelected(crimeId = crime.id)
            }
        }

        (crimeRecyclerView.adapter as CrimeAdapter).submitList(crimes)
    }

    companion object {
        fun newInstance(): CrimeListFragment = CrimeListFragment()
    }


    /** ADAPTER */

    private inner class CrimeAdapter(var crimes: List<Crime>) :
        ListAdapter<Crime, CrimeAdapter.CrimeHolder>(DiffCallback()) {

        /** ABSTRACT HOLDER */
        private abstract inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view) {

        }

        /** HOLDER */
        private inner class NormalCrimeHolder(view: View) : CrimeHolder(view),
            View.OnClickListener {
            lateinit var crime: Crime
            val bindingNotPolice = ListItemCrimeBinding.bind(view)

            init {
                itemView.setOnClickListener(this)
            }

            fun bind(crime: Crime) {
                this.crime = crime
                bindingNotPolice.crimeTitle.text = crime.title
                bindingNotPolice.crimeDate.text =
                    DateFormat.format("EEE, d MMM, yyyy, HH:mm", this.crime.date).toString()
                bindingNotPolice.crimeSolved.visibility = if (crime.isSolved) {
                    View.VISIBLE
                } else View.GONE
            }


            override fun onClick(p0: View?) {
                callbacks?.onCrimeSelected(crimeId = crime.id)
            }

        }


        /** Return inflate layout */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val viewNotPolice =
                layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return NormalCrimeHolder(view = viewNotPolice)
        }


        /** Fill u views using Holder */
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
//            val crime = crimes[position]
            when (holder) {
                is NormalCrimeHolder -> holder.bind(crime = getItem(position))
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Crime>() {
        override fun areItemsTheSame(oldItem: Crime, newItem: Crime): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Crime, newItem: Crime): Boolean {
            return oldItem == newItem
        }

    }

}