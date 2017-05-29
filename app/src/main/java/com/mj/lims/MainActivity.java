package com.mj.lims;

import android.content.Context;
import android.content.Intent;
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
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
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

            int userId = Remember.getInt(Constants.USER_ID, -1);
            if (userId < 0) {
                MyApp.log("User ID not proper");
                return false;
            }

            String url = Constants.BASE_URL, title="";

            switch ((int)drawerItem.getIdentifier()) {
                case 1:
                    url += "/app/user/"+userId+"/plots";
                    title = "My Plots";
                    break;

                case 2:
                    url += "/app/user/"+userId+"/applications";
                    title = "My Applications";
                    break;

                case 3:
                    url += "/app/plots/on-sale";
                    title = "Plots on Sale";
                    break;

                case 4:
                    url += "/app/announcements";
                    title = "Announcements";
                    break;

                case 5:
                    Remember.remove(Constants.SIGNED_IN);
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                    break;

                default: break;
            }

            tvTitle.setText(title);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MasterFragment fragment = MasterFragment.newInstance(url);
            fragmentTransaction.add(R.id.fragment_container, fragment);
            fragmentTransaction.commit();


        }
        return false;
    }

}
