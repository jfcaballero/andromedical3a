package com.app.andromedical3a

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.andromedical3a.administrationModulo.AdministrationFragment
import com.app.andromedical3a.administrationModulo.AdministrationMedicineFragment
import com.app.andromedical3a.administrationModulo.LoginAdministrationFragment

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), MainPageFragment.Callbacks, LoginAdministrationFragment.Callbacks, AdministrationFragment.Callbacks, AdministrationMedicineFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentoActual = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragmentoActual == null) {
            val fragmento = MainPageFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragmento)
                    .commit()
        }

    }


    override fun administrationModulo() {
        Log.i(TAG, "Montando modulo Administracion")
        val fragmentoAdministracion =
                AdministrationFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentoAdministracion)
                .addToBackStack(null)
                .commit()
    }

    override fun loginadministrationModulo() {
        Log.i(TAG, "Montando modulo loginAdministracion")
        val fragmentologinAdministracion =
                LoginAdministrationFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentologinAdministracion)
                .addToBackStack(null)
                .commit()
    }

    override fun calendarModulo() {
        Log.i(TAG, "Montando modulo calendar")
        /* val fragmentocalendar =
                 //CalendarFragment()
         supportFragmentManager
                 .beginTransaction()
                 .replace(R.id.fragment_container, fragmentocalendar)
                 .addToBackStack(null)
                 .commit()
                 */
    }

    override fun dateMedicModulo() {
        Log.i(TAG, "Montando modulo citeMedic")
        /* val fragmentocalendar =
                 //CalendarFragment()
         supportFragmentManager
                 .beginTransaction()
                 .replace(R.id.fragment_container, fragmentocalendar)
                 .addToBackStack(null)
                 .commit()
                 */
    }

    override fun administrationMedicineModulo() {
        Log.i(TAG, "Montando modulo AdministracionMedicine")
        val fragmentoAdministracionMedicine =
            AdministrationMedicineFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoAdministracionMedicine)
            .addToBackStack(null)
            .commit()
    }

    override fun addMedicacionModulo() {
        TODO("Not yet implemented")
    }


}


