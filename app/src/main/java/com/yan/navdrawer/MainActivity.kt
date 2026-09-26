package com.yan.navdrawer

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.yan.navdrawer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Oculta a barra de status
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.statusBars())

        setSupportActionBar(binding.toolbar)

        // Configura o botão hamburguer da Toolbar para abrir a gaveta lateral
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        // Configuração do Menu Inferior (Bottom Navigation) ZARA
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottom_home -> openFragment(HomeFragment())
                R.id.bottom_cart -> openFragment(CartFragment())
                R.id.bottom_profile -> openFragment(ProfileFragment())
                R.id.bottom_menu -> openFragment(MenuFragment())
            }
            true
        }

        fragmentManager = supportFragmentManager
        // Abre a tela inicial da ZARA ao abrir o aplicativo
        openFragment(HomeFragment())

        // Ação do Botão Flutuante (FAB)
        binding.fab.setOnClickListener {
            Toast.makeText(this, "Coleção ZARA", Toast.LENGTH_SHORT).show()
        }

        // Tratamento do botão Voltar do celular para fechar a gaveta se estiver aberta
        onBackPressedDispatcher.addCallback(this) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                finish()
            }
        }
    }

    // Clique nas opções do Menu Lateral (Drawer ZARA)
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_prime -> openFragment(CartFragment())
            R.id.nav_shirt -> openFragment(ShirtFragment())
            R.id.nav_pants -> openFragment(PantsFragment())
            R.id.nav_shoes -> openFragment(ShoesFragment())
            R.id.nav_jacket -> openFragment(JacketFragment())
            R.id.nav_dress -> openFragment(DressFragment())
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    // Função utilitária para trocar de tela/fragment
    private fun openFragment(fragment: Fragment){
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}