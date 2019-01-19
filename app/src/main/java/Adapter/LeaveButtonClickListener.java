package Adapter;


import android.content.Intent;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;


import com.bilkent.subfly.getout.MainActivity;

import Model.EventManager;

/**
 * The click listener for leave button
 * that sets the view according to changes
 * @author Çağlar Çankaya
 */
public class LeaveButtonClickListener implements View.OnClickListener {

    private EventManager eventManager;
    private TextView participants;
    Button leave;
    Button join;

    public LeaveButtonClickListener(EventManager eventManager, TextView participants, Button leave, Button join) {
        this.eventManager = eventManager;
        this.participants = participants;
        this.leave = leave;
        this.join = join;
    }

    @Override
    public void onClick(View view) {
        if (eventManager.dropEvent()) {
            String text = participants.getText().toString();
            String current = text.substring(0, text.indexOf('/'));
            String all = text.substring(text.indexOf('/') + 1, text.length());
            participants.setText((Integer.parseInt(current) - 1 ) + "/" + all);

            /**
             * Added by Tolga Catalpinar 19.01.2019
             */
            leave.setVisibility( View.INVISIBLE);
            join.setVisibility( View.VISIBLE);
            join.setOnClickListener( new JoinButtonClickListener( eventManager, participants, leave, join));
        }
    }
}
