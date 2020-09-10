package com.example.a200327connectfour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.a200327connectfour.Model.Logging;
import com.example.a200327connectfour.View.MainActivityView;

public class MainActivity extends AppCompatActivity {

    Logging log = new Logging("MainActivity.java");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View v = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(v);
       // context = getApplicationContext();

        final MainActivityView mainActivityView = new MainActivityView(
                MainActivity.this,
                v,
                MainActivity.this);
       // mainActivityView.handleInputTest();

        //every change or restart:
        if(savedInstanceState == null){
            //this is a fresh app that was not restarted due to memory loss or orientation change
            mainActivityView.setEventAppFirstLoad();
            log.add("onCreate:savedInstanceState=null/first load");
        } else {
            //load the old SaveGame of the "inprogress" file
            log.add("onCreate:savedInstanceState exists");
            mainActivityView.setEventAppOnCreateButInstanceStateExists();
        }

        //personalised Menu instead of somewhat complicated Android Standard
        final Button buttonPopupMenu = (Button) findViewById(R.id.ButtonMenu);
        buttonPopupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, buttonPopupMenu);
                //Inflating the Popup using xml file

                popup.getMenuInflater()
                        .inflate(R.menu.mypopupmenu, popup.getMenu());

                //registering popup with OnMenuItemClickListener

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        /*Toast.makeText(
                                MainActivity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();*/
                        mainActivityView.popupMenuAction(item.getTitle().toString());
                        return true;
                    }
                });
                if(mainActivityView.getShowDevModeActivationButton().equals("DevMode"))
                    popup.getMenu().add("Developer Mode");
                popup.show(); //showing popup menu
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.mytoolbarmenu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Toast.makeText(this, "By Karl Klotz, September 2020", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
