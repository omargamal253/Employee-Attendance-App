package com.example.attendanceapp.voice;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.attendanceapp.R;

public class MediaVoice {

    MediaPlayer player;
    Context context;

    public MediaVoice(Context context)
    {
        this.context = context ;
    }
    public void PlayVoice(int mode){
        if(mode==1)
         player =  MediaPlayer.create(context, R.raw.thank_you_voice);
        else player =  MediaPlayer.create(context, R.raw.try_again_voice);

        player.start();

    }
    public  void StopPlayer(){
        if (player!=null){

            player.release();
            player=null;
        }
    }
}
