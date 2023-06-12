package com.shahruie.www.gohome;

/**
 * Created by MR on 02/21/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  implements View.OnClickListener{
    List<Addresses> locs;
    //ArrayList<String> SubjectNames;
    private final Context context;
    View view1;
    DBHandler db;
    String imguri,audiouri;

    public RecyclerViewAdapter(Context context,List<Addresses> locs) {

        this.locs = locs;
        this.context=context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        view1  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);

        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int i) {

        Viewholder.txtaddress.setText(locs.get(i).getAddress());
        Viewholder.txtphone.setText(locs.get(i).getName()+" : "+locs.get(i).getPhone());
        //imguri=locs.get(i).getLocimg();


    }

    @Override
    public int getItemCount() {

        return locs.size();
    }

    @Override
    public void onClick(View v) {


    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context context;
        TextView txtaddress,txtphone;
        Button btnvoice;
        ImageView imgloc;
        public ViewHolder(View view) {

            super(view);
            context = view.getContext();
            view.setOnClickListener(this);
            imgloc=(ImageView)view.findViewById(R.id.imgloc);
            txtaddress = (TextView)view.findViewById(R.id.txtaddress);
            txtphone = (TextView)view.findViewById(R.id.txtphone);
            btnvoice=(Button) view.findViewById(R.id.btnvoice);
            btnvoice.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    MediaPlayer mpintro;
                    audiouri=locs.get(getAdapterPosition()).getVoice();
                    if(audiouri!=null)
                    { mpintro = MediaPlayer.create(context, Uri.parse(audiouri));
                    mpintro.start();}

                }
            });
            imgloc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap bi= ((BitmapDrawable)imgloc.getDrawable()).getBitmap();
                    Fullscreen full=new Fullscreen(context, bi);
                    full.show();

                }
            });


        }

        @Override
        public void onClick(View v) {

             /*switch (getAdapterPosition()) {
                case 0:
                    Intent intent0 = new Intent(context,Amoozesh.class);
                    intent0.putExtra("key_name",getAdapterPosition());
                    context.startActivity(intent0);
                    break;
                case 1:
                    Intent intent = new Intent(context,main2.class);
                    intent.putExtra("key_name",getAdapterPosition());
                    context.startActivity(intent);
                    break;
                case 2:
                    Intent intent2 = new Intent(context,main2.class);
                    intent2.putExtra("key_name",getAdapterPosition());
                    context.startActivity(intent2);
                    break;
                case 3:
                    Intent intent3 = new Intent(context,Amoozesh.class);
                    intent3.putExtra("key_name",getAdapterPosition());
                    context.startActivity(intent3);
                    break;
                case 4:
                    Intent intent4 = new Intent(context,main2.class);
                    intent4.putExtra("key_name",getAdapterPosition());
                    context.startActivity(intent4);
                    break;
                case 5:
                    Intent intent5 = new Intent(context,main2.class);
                    intent5.putExtra("key_name",getAdapterPosition());
                    context.startActivity(intent5);
                    break;
                case 6:
                    Intent intent6 = new Intent(context,main2.class);
                    intent6.putExtra("key_name",getAdapterPosition());
                    context.startActivity(intent6);
                    break;
                case 7:
                    Intent intent7 = new Intent(context,main2.class);
                    intent7.putExtra("key_name",getAdapterPosition());
                    context.startActivity(intent7);
                    break;
                case 8:
                    Intent intent8 = new Intent(context,main2.class);
                    intent8.putExtra("key_name",getAdapterPosition());
                    context.startActivity(intent8);
                    break;
                case 9:
                    Intent intent9 = new Intent(context,main2.class);
                    intent9.putExtra("key_name",getAdapterPosition());
                    context.startActivity(intent9);
                    break;
                default:
                    Intent intent10 = new Intent(context,main2.class);
                    intent10.putExtra("key_name",getAdapterPosition());
                    context.startActivity(intent10);
                    break;

            }*/
        }
    }

}