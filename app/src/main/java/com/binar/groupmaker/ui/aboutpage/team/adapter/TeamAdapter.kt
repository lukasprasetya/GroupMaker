package com.binar.groupmaker.ui.aboutpage.team.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.binar.groupmaker.constant.CommonConstant
import com.binar.groupmaker.databinding.ItemProfileMemberBinding
import com.binar.groupmaker.ui.aboutpage.profile.ProfileActivity
import com.binar.groupmaker.ui.aboutpage.team.TeamActivity

class TeamAdapter(private val list: List<TeamActivity.teams>, val context : Context)
    : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = ItemProfileMemberBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)

    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.tvName.text = name.toString()
                binding.tvJob.text = job.toString()
                binding.ivImg.load(image.toString()) {
                    crossfade(true)
                }

                binding.llItem.setOnClickListener {
                    val i = Intent(context, ProfileActivity::class.java)
                    i.putExtra(CommonConstant.EXTRAS_NAME, name.toString())
                    i.putExtra(CommonConstant.EXTRAS_JOB, job.toString())
                    i.putExtra(CommonConstant.EXTRAS_DESC, desc.toString())
                    i.putExtra(CommonConstant.EXTRAS_IMG, image.toString())
                    context.startActivity(i)
                }
            }
        }
    }

    inner class TeamViewHolder(val binding: ItemProfileMemberBinding) :
        RecyclerView.ViewHolder(binding.root)

}