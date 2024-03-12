package com.example.sunsmartfamily

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        // 设置ActionBarDrawerToggle
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ).also { drawerLayout.addDrawerListener(it) }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // 初始化时设置 HomeFragment 作为默认内容
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, HomeFragment())
                .commit()
            navigationView.setCheckedItem(R.id.nav_home_fragment) // 如果你有一个对应的菜单项ID，也应该设置为选中状态
        }

        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            // 声明一个变量来存储将要展示的 Fragment
            var fragmentToShow: Fragment? = null

            when (item.itemId) {
                R.id.nav_home_fragment -> {
                    fragmentToShow = HomeFragment()
                }
                R.id.nav_a -> {
                    fragmentToShow = FragmentA()
                }
                R.id.nav_b -> {
                    fragmentToShow = FragmentB()
                }
                R.id.nav_c -> {
                    fragmentToShow = FragmentC()
                }
                R.id.nav_d -> {
                    fragmentToShow = FragmentD()
                }
                // 其他菜单项...
            }

            // 如果有 Fragment 要展示，则进行事务并替换当前 Fragment
            fragmentToShow?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, it)
                    .commit()
            }

            // 关闭抽屉
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 将ActionBar上的图标与抽屉的状态同步
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // 同步切换器的状态
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // 配置更改时更新切换器状态
        toggle.onConfigurationChanged(newConfig)
    }
}
