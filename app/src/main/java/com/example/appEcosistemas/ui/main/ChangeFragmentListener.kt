package com.example.appEcosistemas.ui.main



interface ChangeFragmentListener {
    fun goToProfileFragment()
    fun goToEcosistemaFragment()
    fun goToEcosistemaFragment(user: String)
    fun goToEcosistemaCreateFragment()
}