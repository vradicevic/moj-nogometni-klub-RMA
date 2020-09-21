package vradicevic.etfos.mojnogometniklub.utils

import android.content.Context

class PreferenceManager {
    companion object {
        val PREFS_FILE = "MyPreferences"
        val PREFS_UUID = "MyCounter"
        val PREFS_CLUB= ""
    }

    fun saveUUID(uuid: String) {
        val sharedPreferences = MyAppContext.getContext().getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(PREFS_UUID, uuid)
        editor.apply()
    }
    fun saveClub(club:String?){
        val sharedPreferences = MyAppContext.getContext().getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(PREFS_CLUB, club)
        editor.apply()
    }
    fun retrieveUUID():String?{
        val sharedPreferences = MyAppContext.getContext().getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return sharedPreferences.getString(PREFS_UUID,null);
    }
    fun retrieveClub():String?{
        val sharedPreferences = MyAppContext.getContext().getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
        return sharedPreferences.getString(PREFS_CLUB,null);
    }
}