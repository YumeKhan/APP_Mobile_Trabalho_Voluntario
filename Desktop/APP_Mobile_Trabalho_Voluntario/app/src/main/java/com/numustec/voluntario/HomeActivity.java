package com.numustec.voluntario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.numustec.voluntario.fragment.Profile;
import androidx.fragment.app.Fragment;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView menu;
    Toolbar appBar;
    FragmentContainerView fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        menu   = (BottomNavigationView) findViewById(R.id.bt_menu);
        appBar = (Toolbar)findViewById(R.id.appBar);
        fragmentContainer = (FragmentContainerView)findViewById(R.id.fcvScreen);
        menu.setOnNavigationItemSelectedListener(
                menuItem -> {
                    if(menuItem.getItemId() == R.id.nav_home){

                    }
                    else if(menuItem.getItemId() == R.id.nav_canditate){

                    }
                    else if(menuItem.getItemId() == R.id.nav_post){

                    }
                    else if(menuItem.getItemId() == R.id.nav_profile){
                        replaceFragment(Profile.newInstance());
                    }
                    return true;
                }
        );
    }
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainer.getId(), fragment)
                .commit();
    }
}