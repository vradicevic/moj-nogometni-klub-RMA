package vradicevic.etfos.colouredbirds

import android.content.Context

class PreferenceManager {
    companion object {
        val PREFS_FILE = "MyPreferences"
        val PREFS_COUNTER = "MyCounter"
        val PREFS_KEY_BIRD = "Colour"
    }

    fun saveBundle(bird: Int, counter:Int) {
        val sharedPreferences = MyBirdCounterApp.ApplicationContext.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(PREFS_KEY_BIRD, bird)
        editor.putInt(PREFS_COUNTER, counter);
        editor.apply()
    }

    fun retrieveBird(): Int {
        val sharedPreferences = MyBirdCounterApp.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        return sharedPreferences.getInt(PREFS_KEY_BIRD,0)
    }


    fun retrieveCounter():Int{
        val sharedPreferences = MyBirdCounterApp.ApplicationContext.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PREFS_COUNTER,0);
    }
}