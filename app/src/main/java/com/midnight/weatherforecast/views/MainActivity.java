package com.midnight.weatherforecast.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MenuItem;

import com.midnight.weatherforecast.R;
import com.midnight.weatherforecast.controller.ControllerPermission;
import com.midnight.weatherforecast.databinding.ActivityMainBinding;
import com.midnight.weatherforecast.views.fragments.FragmentDetail;
import com.midnight.weatherforecast.views.fragments.FragmentMain;
import com.midnight.weatherforecast.views.fragments.FragmentSearch;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();

    }

    /**
     *
     */
    private void initView(){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,FragmentMain.getInstance(),FragmentMain.TAG)
                .addToBackStack(null)
                .commit();
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void openAddCityFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,FragmentSearch.getInstance(), FragmentSearch.TAG)
                .addToBackStack(FragmentSearch.TAG)
                .commit();

    }

    public void openDetailFragment(Bundle bundle){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, FragmentDetail.getInstance(bundle), FragmentSearch.TAG)
                .addToBackStack(FragmentSearch.TAG)
                .commit();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0]==-1){
            ((FragmentMain)getSupportFragmentManager().getFragments().get(0)).handlerLoading(false);
            return;
        }
        if (ControllerPermission.Companion.getInstance().getRequestCodeLocation()==requestCode){
            if (getSupportFragmentManager().getFragments().size()>0){
                if (getSupportFragmentManager().getFragments().get(0) instanceof FragmentMain){
                    ((FragmentMain)getSupportFragmentManager().getFragments().get(0)).getLocation();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getFragments().size()>0){
            if (getSupportFragmentManager().getFragments().get(0) instanceof FragmentDetail){
                getSupportFragmentManager().popBackStack();
            }else if (getSupportFragmentManager().getFragments().get(0) instanceof FragmentSearch){
                getSupportFragmentManager().popBackStack();
            }else{
                finishAffinity();
            }
        }
    }


}
