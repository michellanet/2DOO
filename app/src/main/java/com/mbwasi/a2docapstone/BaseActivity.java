package com.mbwasi.a2docapstone;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import pk.codebase.requests.HttpError;
import pk.codebase.requests.HttpHeaders;
import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;


public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";
    private static final String TAG = "BaseActivity";
    private final Handler mDrawerActionHandler = new Handler();
    private DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected int mNavItemId;

    Context context;

    protected String shareFilePath;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    protected void onStart() {

        context = this;
        Toolbar toolbar = initToolbar();
        initDrawerNav(toolbar);
        super.onStart();
    }

    protected void initDrawerNav( Toolbar toolbar) {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // load saved navigation state if present
        //TODO: Add rest of menu items
//        if (null == savedInstanceState) {
//            mNavItemId = R.id.drawer_item_random;
//        } else {
//            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
//        }

        // listen for navigation events
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);


        View hView =  navigationView.getHeaderView(0);
      //  TextView nav_user = (TextView)hView.findViewById(R.id.nav_name);
        //nav_user.setText(user);
        CircleImageView profielImage = (CircleImageView) hView.findViewById(R.id.profileImage);
        File imgFile = new  File(   getFilesDir()+"/avatar.jpg");
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            profielImage.setImageBitmap(myBitmap);
        }

        // select the correct nav menu item
     //   navigationView.getMenu().findItem(mNavItemId).setChecked(true);

        // set up the hamburger icon to open and close the drawer
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open,
                R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return toolbar;
    }



    private void navigate(final int itemId) {
        Log.i(TAG, "Navigate");

        switch (itemId) {
            case R.id.drawer_item_home://placeholder
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(homeIntent);
                break;


            case R.id.drawer_item_categories://placeholder
                Intent catIntent = new Intent(getApplicationContext(), CategoryActivity.class);

              startActivity(catIntent);
                break;

            case R.id.drawer_item_reserve:

                Log.i(TAG, "Reserve Nav");
                Intent reserveIntent = new Intent(getApplicationContext(), ReservationActivity.class);
                startActivity(reserveIntent);
                break;

            case R.id.drawer_item_about:

                Log.i(TAG, "About Nav");
                Intent aboutIntent = new Intent(getApplicationContext(), About.class);
                startActivity(aboutIntent);
                break;

            case R.id.drawer_item_history:

                Log.i(TAG, "Reserve History Nav");
                Intent historyIntent = new Intent(getApplicationContext(), ReservationHistoryListActivity.class);
                startActivity(historyIntent);
                break;

            case R.id.drawer_item_settings:

                Log.i(TAG, "Settings Nav");
                Intent settingsIntent = new Intent(getApplicationContext(), PreferencesActivity.class);
                startActivity(settingsIntent);
                break;

            case R.id.drawer_item_profile:

                Log.i(TAG, "Settings Nav");
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profileIntent);
                break;

            //      case R.id.drawer_item_all_quotes:


//                Intent AllQuotesIntent = new Intent(getApplicationContext(), AllQuotesActivity.class);
//                AllQuotesIntent.putExtra(Constants.QUOTE_FILTER_ID,Constants.ALL_FILTER);
//                startActivity(AllQuotesIntent);
     //       break;

      //      case R.id.drawer_item_all_authors:


//                Intent AllAuthorsIntent = new Intent(getApplicationContext(), AllAuthorsActivity.class);
//                AllAuthorsIntent.putExtra(Constants.QUOTE_FILTER_ID,Constants.AUTHOR_FILTER);
//                startActivity(AllAuthorsIntent);
      //          break;
      //      case R.id.drawer_item_favs:


//                if(Utils.areThereAnyFavs(getApplicationContext())){
//                    Intent FavsIntent = new Intent(getApplicationContext(), AllQuotesActivity.class);
//                    FavsIntent.putExtra(Constants.QUOTE_FILTER_ID,Constants.FAV_FILTER);
//                    startActivity(FavsIntent);
//                }else{
//                    Toast.makeText(getApplicationContext(), "There are no Favourites saved", Toast.LENGTH_LONG).show();
//                }

         //      break;
//
//            case R.id.drawer_item_search:
//
//                Log.i(TAG, "Search Nav");
//                onSearchRequested();
//                break;

//            case R.id.drawer_item_random:

//                Log.i(TAG, "Random Nav");
//                Intent randomQuoteIntent = new Intent(getApplicationContext(), ViewQuoteActivity.class);
//                startActivity(randomQuoteIntent);;
//                break;

//            case R.id.drawer_item_settings:

//                Log.i(TAG, "Settings Nav");
//                Intent settingIntent = new Intent(getApplicationContext(), SettingsActivity.class);
//                startActivity(settingIntent);
//                break;
            default:
                break;
        }

    }



    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        Log.d(TAG,"onNavigationItemSelected");

        // update highlighted item in the navigation menu
        menuItem.setChecked(true);
        mNavItemId = menuItem.getItemId();

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);
        return true;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }
}
