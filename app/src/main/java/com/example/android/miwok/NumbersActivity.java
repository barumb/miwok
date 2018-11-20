package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if ( focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause PlayBack
                        // Lower the volume, keep playing
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0); //riporto all'inizio del file
                        // Wait 30 seconds before stopping playback
                        //mHandler.postDelayed(mDelayedStopRunnable,
                        //TimeUnit.SECONDS.toMillis(30));
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        releaseMediaPlayer();
                    }
                }
            };



    private MediaPlayer.OnCompletionListener mCompletitionListener = new  MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        /*
        Create and setup audiomanager
         */
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        /*
        ArrayList<String> words = new ArrayList<String>();
        words.add("one");
        words.add("two");
        words.add("three");
        words.add("four");
        words.add("five");
        words.add("six");
        words.add("seven");
        words.add("eight");
        words.add("nine");
        words.add("ten");
         */
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add( new Word("one","1",R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "2",R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "3",R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "4",R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five","5",R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six","6",R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven","7",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","8",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","9",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten","10",R.drawable.number_ten,R.raw.number_ten));


        /*
        for(int c=0; c< words.size(); c++)
        {
            Log.v("Numbers","Word ad index " + c +":" + words.get(c));

        }
        LinearLayout rootView = (LinearLayout)findViewById(R.id.rootView);
        ArrayList<TextView> listTextViewNumbers = new ArrayList<TextView>() ;
        TextView wordView = new TextView(this);
        rootView.addView(wordView);
        String text="";

        for(int c=0; c< words.size(); c++)
        {
            Log.v("Numbers","Word ad index " + c +":" + words.get(c));
            text = text + words.get(c) + "\n";
            TextView numberTextView = new TextView(this);
            numberTextView.setText(words.get(c));
            rootView.addView(numberTextView);
            listTextViewNumbers.add(numberTextView);

        }
        */

        WordAdapter adapter = new WordAdapter(this,  words, R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseMediaPlayer();

                //request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, words.get(position).getAudioResource());
                    mMediaPlayer.start();
                    //Toast.makeText(NumbersActivity.this, "List Item Click", Toast.LENGTH_SHORT).show();

                    mMediaPlayer.setOnCompletionListener(mCompletitionListener);
                }
            }
        });


    }


    /*
     mettendo in stop l'activity rilascio le eventuali risorse allocate nel media player per evitare che
     */
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            //abandon audio focus. Unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}

