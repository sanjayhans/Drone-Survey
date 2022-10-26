package com.mobile.dronesurvey

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobile.dronesurvey.databinding.ActivityLocationFilterBinding
import com.mobile.dronesurvey.utils.KeyboardUtil
import com.mobile.dronesurvey.utils.MaterialSpinner
import com.mobile.dronesurvey.utils.MaterialSpinner.OnItemSelectedListener


class LocationFilterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocationFilterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        KeyboardUtil(this,binding.llRoot)


        binding.btnInitialize.setOnClickListener {

            if(!binding.spnDistrict.selectedItem.toString().contains("--Select--")&&
                !binding.spnTaluk.selectedItem.toString().contains("--Select--") &&
                !binding.spnVillage.selectedItem.toString().contains("--Select--")&&
                !binding.spnGrampanchayat.selectedItem.toString().contains("--Select--")&&
                    binding.edSurveyno.text.toString().isNotEmpty()){
                val i = Intent(this, MainActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i)
            }else{
                Toast.makeText(this, "Please fill the required data.", Toast.LENGTH_SHORT).show()
            }

        }

        setUpSpinner()

    }

    private fun setUpSpinner() {

        // access the spinner
            ArrayAdapter.createFromResource(this, R.array.district_array, android.R.layout.simple_spinner_item).let {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spnDistrict.apply {
                    adapter = it
                    onItemSelectedListener = districtListener

                 }

            }


            binding.spnDistrict.selection = 0


    }

    private fun talukSpinner(talukArray: Int) {

        ArrayAdapter.createFromResource(this, talukArray, android.R.layout.simple_spinner_item).let {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spnTaluk.apply {
                adapter = it
                onItemSelectedListener = talukListener

            }
        }
        binding.spnTaluk.selection = 0
    }

    private val districtListener by lazy {
        object : OnItemSelectedListener {
            override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
                Log.v("MaterialSpinner", "onItemSelected =${parent.selection}, position=$position")
               // parent.focusSearch(View.FOCUS_UP)?.requestFocus()
                var talukArray: Int? = null
                if(parent.selectedItem?.equals("Anantapur") == true){
                     talukArray = R.array.anantapurTaluka_array

                }else if (parent.selectedItem?.equals("Chittoor") == true){
                     talukArray = R.array.chittoorTaluka_array

                }
                binding.spnTaluk.selection = 0
                binding.spnVillage.selection = 0
                binding.spnGrampanchayat.selection = 0

                if (talukArray != null) {
                    talukSpinner(talukArray)
                }

            }

            override fun onNothingSelected(parent: MaterialSpinner) {
                Log.v("MaterialSpinner", "onNothingSelected parent=${parent.id}")
            }
        }
    }
    private val talukListener by lazy {
        object : OnItemSelectedListener {
            override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
                Log.v("MaterialSpinner", "onItemSelected =${parent.selection}, position=$position")
                //parent.focusSearch(View.FOCUS_UP)?.requestFocus()

                Log.e("TAG", "item : "+parent.selectedItem)
                var villageArray: Int? = null

                if(parent.selectedItem?.equals("Anantapur") == true){
                     villageArray = R.array.anantapur_Village_GP_array
                    villageSpinner(villageArray)
                    gpSpinner(villageArray)
                    binding.spnGrampanchayat.selection = 0
                    binding.spnVillage.selection = 0

                }else if(parent.selectedItem?.equals("Rayadurgam") == true){
                     villageArray = R.array.rayadurgam_Village_GP_array
                        villageSpinner(villageArray)
                        gpSpinner(villageArray)
                    binding.spnGrampanchayat.selection = 0
                    binding.spnVillage.selection = 0

                }else if(parent.selectedItem?.equals("Penukonda") == true){
                     villageArray = R.array.penukonda_Village_GP_array
                    villageSpinner(villageArray)
                    gpSpinner(villageArray)
                    binding.spnGrampanchayat.selection = 0
                    binding.spnVillage.selection = 0
                }
                else if(parent.selectedItem?.equals("Bangarupalem") == true){
                    villageArray = R.array.bangarupalem_Village_GP_array
                    villageSpinner(villageArray)
                    gpSpinner(villageArray)
                    binding.spnGrampanchayat.selection = 0
                    binding.spnVillage.selection = 0

                }else if(parent.selectedItem?.equals("Kuppam") == true){
                     villageArray = R.array.kuppam_Village_GP_array
                    villageSpinner(villageArray)
                    gpSpinner(villageArray)
                    binding.spnGrampanchayat.selection = 0
                    binding.spnVillage.selection = 0
                }




            }

            override fun onNothingSelected(parent: MaterialSpinner) {
                Log.v("MaterialSpinner", "onNothingSelected parent=${parent.id}")
            }
        }
    }

    private fun villageSpinner(villageArray: Int) {

        ArrayAdapter.createFromResource(this, villageArray, android.R.layout.simple_spinner_item).let {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spnVillage.apply {
                adapter = it
                onItemSelectedListener = villageListener



            }
        }
        binding.spnVillage.selection = 0
    }

    private val villageListener by lazy {
        object : OnItemSelectedListener {
            override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
                Log.v("MaterialSpinner", "onItemSelected =${parent.selection}, position=$position")
                //parent.focusSearch(View.FOCUS_UP)?.requestFocus()

                binding.spnGrampanchayat.selection = 0

            }

            override fun onNothingSelected(parent: MaterialSpinner) {
                Log.v("MaterialSpinner", "onNothingSelected parent=${parent.id}")
            }
        }
    }

    private fun gpSpinner(gpArray: Int) {

        ArrayAdapter.createFromResource(this, gpArray, android.R.layout.simple_spinner_item).let {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spnGrampanchayat.apply {
                adapter = it
                onItemSelectedListener = gpListener

            }
        }
        binding.spnGrampanchayat.selection = 0
    }

    private val gpListener by lazy {
        object : OnItemSelectedListener {
            override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
                Log.v("MaterialSpinner", "onItemSelected =${parent.selection}, position=$position")
                //parent.focusSearch(View.FOCUS_UP)?.requestFocus()

            }

            override fun onNothingSelected(parent: MaterialSpinner) {
                Log.v("MaterialSpinner", "onNothingSelected parent=${parent.id}")
            }
        }
    }
}