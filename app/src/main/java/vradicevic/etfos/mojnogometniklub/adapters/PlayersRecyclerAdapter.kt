package vradicevic.etfos.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_player.view.*
import vradicevic.etfos.mojnogometniklub.R
import vradicevic.etfos.mojnogometniklub.models.Player

class PlayersRecyclerAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Player> = ArrayList();
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return PlayerViewHolder (
           LayoutInflater.from(parent.context).inflate(R.layout.item_player,parent,false)
       )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PlayerViewHolder->{
                holder.bind(items.get(position))
            }
        }
    }

    fun submitList(players:List<Player>){
        items = players

    }

    class PlayerViewHolder constructor(
        itemView: View
    ):RecyclerView.ViewHolder(itemView){
        val tvPlayerNameAndSurname:TextView = itemView.tvPlayerNameAndSurname
        val tvPlayerFifaID:TextView = itemView.tvPlayerFifaID
        val ivPlayerPicture: ImageView = itemView.ivPlayerPicture
        val tvRedCards:TextView = itemView.tvRedCards
        val tvYellowCards:TextView = itemView.tvYellowCards
        val tvPlayerScore:TextView = itemView.tvPlayerScore
        val tvPlayerPosition:TextView = itemView.tvPlayerPosition
        fun bind(player:Player){
            tvPlayerNameAndSurname.text = player.name +" " +player.surname
            tvPlayerFifaID.text = player.fifaID
            tvPlayerScore.text = player.scores.toString()
            tvRedCards.text = player.redCards.toString()
            tvYellowCards.text = player.yellowCards.toString()
            tvPlayerPosition.text = player.position

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(player.image)
                .into(ivPlayerPicture)

        }


    }

}