package com.binar.groupmaker.ui.member

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.binar.groupmaker.R
import com.binar.groupmaker.base.BaseActivity
import com.binar.groupmaker.base.GenericViewModelFactory
import com.binar.groupmaker.constant.CommonConstant
import com.binar.groupmaker.data.room.entity.Member
import com.binar.groupmaker.databinding.ActivityMemberFormBinding
import com.binar.groupmaker.di.ServiceLocator
import com.binar.groupmaker.dialogs.AddMemberDialog
import com.binar.groupmaker.ui.member.adapter.MemberAdapter
import com.binar.groupmaker.ui.random.RandomizeActivity
import com.binar.groupmaker.wrapper.Resource


class MemberFormActivity : BaseActivity<ActivityMemberFormBinding>(ActivityMemberFormBinding::inflate) , AddMemberDialog.DialogListener, MemberAdapter.OnCLickListenerMember {

    private val viewModel: MemberViewModel by lazy {
        GenericViewModelFactory(MemberViewModel(ServiceLocator.provideLocalRepository(this))).create(
            MemberViewModel::class.java
        )
    }

    var playersArrList: ArrayList<String>? = null

    private val nameGroup: String? by lazy {
        intent.getStringExtra("name_group")
    }

    private val idGroup: String? by lazy {
        intent.getStringExtra("id_group")
    }

    private lateinit var adapter: MemberAdapter



    override fun onStart() {
        super.onStart()
        viewModel.setIntentData(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        observeData()
        adapter = MemberAdapter(this)

        playersArrList = ArrayList()
        initRecyclerView()
        initData()
        binding.btnRandom.visibility = View.GONE
        binding.btnAddMamber.setOnClickListener {
            AddMemberDialog().show(supportFragmentManager, "List member")
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnRandom.setOnClickListener {

            var dataJumlah = binding.edtJumlahTim.text.toString()

            if (playersArrList!!.isEmpty() || playersArrList!!.size == 0){
                Toast.makeText(this@MemberFormActivity, "Member Kosong", Toast.LENGTH_SHORT)
                    .show()
            }else{
                if (dataJumlah.isEmpty()){
                    Toast.makeText(this@MemberFormActivity, "Input Jumlah Tim", Toast.LENGTH_SHORT)
                        .show()
                }else{
                    val intent = Intent(this@MemberFormActivity, RandomizeActivity::class.java)
                    intent.putExtra("id_group", idGroup)
                    intent.putExtra("name_group", nameGroup)
                    intent.putExtra("numberOfTeams", Integer.parseInt(binding.edtJumlahTim.text.toString()))
                    intent.putStringArrayListExtra("data", playersArrList)
                    startActivity(intent)

                }
            }




        }


    }

    private fun initData() {
        binding.tvRandomizeCurrentPreset.text = nameGroup

        Log.d("datagroup",idGroup.toString())
        idGroup?.let { viewModel.getAllGroupByGroup(it) }
    }



    private fun observeData() {
        viewModel.initialDataResult.observe(this) {
            when (it) {
                is Resource.Error -> {
                    showError(it.message)
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    showData(it.data)

                    playersArrList!!.clear()
                    it.data?.forEach {
                        playersArrList!!.add(it.nameMember)
                    }
                    binding.btnRandom.visibility = View.VISIBLE
                    binding.tvRandomizeTotalPlayers.text = playersArrList?.size.toString()
                    Log.d("datakonver", playersArrList.toString())




                }
            }
        }

        viewModel.insertResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    setFormEnabled(false)
                    binding.pbForm.isVisible = true
                }
                is Resource.Success -> {
                    setFormEnabled(true)
                    binding.pbForm.isVisible = false
//                    finish()
                    initData()
                    Toast.makeText(this, "Insert data Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    setFormEnabled(true)
                    binding.pbForm.isVisible = false
//                    finish()
                    Toast.makeText(this, "Error when update data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.updateResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    setFormEnabled(false)
                    binding.pbForm.isVisible = true
                }
                is Resource.Success -> {
                    setFormEnabled(true)
                    binding.pbForm.isVisible = false
                    Toast.makeText(this, "Update data Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    setFormEnabled(true)
                    binding.pbForm.isVisible = false
                    Toast.makeText(this, "Error when update data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.deleteResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    setFormEnabled(false)
                    binding.pbForm.isVisible = true
                }
                is Resource.Success -> {
                    setFormEnabled(true)
                    binding.pbForm.isVisible = false
                    initData()
                    Toast.makeText(this, "Delete data Success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    setFormEnabled(true)
                    binding.pbForm.isVisible = false
                    Toast.makeText(this, "Error when delete data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun setFormEnabled(isFormEnabled: Boolean) {
        with(binding) {
//            tilNoteTitle.isEnabled = isFormEnabled
        }
    }





    private fun showData(data: List<Member>?) {




        data?.let { listData ->
            binding.pbForm.isVisible = false
            binding.tvError.isVisible = false
            binding.rvNotes.isVisible = true
            if (listData.isNotEmpty()) {
                adapter.setItems(listData)
                adapter.notifyDataSetChanged()



            } else {
                showEmptyData()
            }
        }

    }

    private fun showEmptyData() {
        binding.tvError.isVisible = true
        binding.tvError.text = getString(R.string.text_empty_notes)

    }

    private fun showLoading() {
        binding.pbForm.isVisible = true
        binding.tvError.isVisible = false
        binding.rvNotes.isVisible = false
    }

    private fun showError(message: String?) {
        binding.pbForm.isVisible = false
        binding.tvError.isVisible = true
        binding.rvNotes.isVisible = false
        message?.let {
            binding.tvError.text = it
        }
    }

    private fun initRecyclerView() {
        binding.rvNotes.adapter = this@MemberFormActivity.adapter
    }

    companion object {
        fun startActivity(context: Context, id: Int? = null) {
            context.startActivity(Intent(context, MemberFormActivity::class.java).apply {
                id?.let {
                    putExtra(CommonConstant.EXTRAS_ID_NOTE, id)
                }
            })
        }
    }

    override fun processDialog(memberName: String) {
        val data = Member(idGroup = idGroup.toString(), nameMember = memberName)
        viewModel.insertMember(data)

    }

    override fun onDeleteClickListenerMember(member: Member) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.text_delete_dialog))
            .setMessage("Are you sure want to delete ${member.nameMember} ?")
            .setPositiveButton(getString(R.string.text_yes_dialog)) { dialog, _ ->
                viewModel.deleteMember(member)
                Toast.makeText(
                    this,
                    "${member.nameMember} Successfully Deleted",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.text_no_dialog)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}


