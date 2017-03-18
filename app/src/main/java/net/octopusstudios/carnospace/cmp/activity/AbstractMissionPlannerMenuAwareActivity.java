package net.octopusstudios.carnospace.cmp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.octopusstudios.carnospace.cmp.R;

/**
 * Created by Davide on 18/03/2017.
 */

public abstract class AbstractMissionPlannerMenuAwareActivity  extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_missions_lister, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Resources res = getResources();
        if (id == R.id.action_help) {
            openPopupDialog(R.layout.mission_input_dialog, res.getString(R.string.close_help));
            return true;
        }

        if (id == R.id.action_about) {
            openPopupDialog(R.layout.about_popup, res.getString(R.string.close_about));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPopupDialog(int dialogId, String closeButtonText) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(dialogId, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(closeButtonText,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
