package com.mj.lims;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends AppCompatActivity implements
        MyPlotsFragment.OnFragmentInteractionListener, Drawer.OnDrawerItemClickListener {

    private DopeTextView tvTitle;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar  = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);

        tvTitle = (DopeTextView) toolbar.findViewById(R.id.toolbar_title);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "khula.ttf");

        fragmentManager = getSupportFragmentManager();


        String username = Remember.getString(Constants.USER_FULL_NAME, "Full Name");
        String phone_number = Remember.getString(Constants.USER_PHONE_NUMBER, "Phone Number");

        IProfile profileItem = new ProfileDrawerItem()
                .withName(username)
                .withEmail(phone_number)
                .withTypeface(typeface)
                .withIcon(getResources().getDrawable(R.mipmap.ic_launcher));

        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profileItem)
                .build();

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("My Plots").withTypeface(typeface);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("My land applications").withTypeface(typeface);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Plots on Sale").withTypeface(typeface);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Announcements").withTypeface(typeface);

        SecondaryDrawerItem item5 = new SecondaryDrawerItem().withIdentifier(5).withName("Log Out").withTypeface(typeface);

        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .addDrawerItems(item1, item2, item3, item4)
                .addDrawerItems(new DividerDrawerItem())
                .addDrawerItems(item5)
                .withFooterDivider(false)
                .withActionBarDrawerToggle(true)
                .withOnDrawerItemClickListener(this)
                .withStickyFooter(R.layout.footer)
                .build();


    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        MyApp.log("on fragment interaction listener");
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        if (drawerItem != null) {
            MyApp.log("Item clicked: "+drawerItem.toString());

            switch ((int)drawerItem.getIdentifier()) {
                case 1:
            }


            String[] urls = new String[] {
                    "https://news.ycombinator.com/newest",
                    "https://twitter.com/",
                    "https://instagram.com/"
            };

            tvTitle.setText(urls[position%urls.length]);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MasterFragment fragment = MasterFragment.newInstance(urls[position%urls.length]);
            fragmentTransaction.add(R.id.fragment_container, fragment);
            fragmentTransaction.commit();


        }
        return false;
    }
}
