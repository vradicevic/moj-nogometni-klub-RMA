package vradicevic.etfos.mojnogometniklub

import android.content.Context

object MyAppContext {
    private lateinit var context: Context
    fun setContext(ctx:Context){
        context=ctx
    }
    fun getContext():Context{
        return context
    }
}