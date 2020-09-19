package vradicevic.etfos.mojnogometniklub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_player.view.*
import kotlinx.android.synthetic.main.item_player_highscore.view.*
import vradicevic.etfos.mojnogometniklub.R
import vradicevic.etfos.mojnogometniklub.models.HighscoreItem
import vradicevic.etfos.mojnogometniklub.models.Player

class HighscorePlayersAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<HighscoreItem> = ArrayList();
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HighscorePlayerViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.item_player_highscore,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HighscorePlayerViewHolder->{
                holder.bind(items.get(position),position+1)
            }
        }
    }

    fun submitList(players:List<HighscoreItem>){
        items = players

    }

    class HighscorePlayerViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val tvPlayer= itemView.tvPlayer
        val tvPosition= itemView.tvPosition
        val ivPlayer =itemView.ivPlayer
        val tvScore =itemView.tvScore
        fun bind(player: HighscoreItem, pos: Int){
            tvPlayer.text = player.name + " " +player.surname
            tvScore.text = player.value.toString()
            tvPosition.text = pos.toString()
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(player.image)
                .into(ivPlayer)

        }


    }
}