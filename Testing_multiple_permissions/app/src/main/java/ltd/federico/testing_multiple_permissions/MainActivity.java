package ltd.federico.testing_multiple_permissions;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
{
    /* Explain this */
    private static final int REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int BLUETOOTH_ENABLE_INTENT = 2;

    /* Explain this */
    private static MainActivity thisActivity;

    /* Explain this */
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        thisActivity = this;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Do this first because it restarts the app {@link onRequestPermissionsResult/startActivity}
        request_permissions();
        enable_bluetooth();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == REQUEST_ACCESS_FINE_LOCATION)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                showText("Access to location granted");

                // Restart this activity with the given permissions
                startActivity(getIntent());
            }
            else
            {
                showText("You must grant this permission now!");
                request_permissions();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BLUETOOTH_ENABLE_INTENT)
        {
            if (requestCode == RESULT_OK)
            {
                showText("Thank you for enabling your bluetooth");
            }
            else
            {
                showText("You need to enable your bluetooth");
                enable_bluetooth();
            }
        }
    }

    /* Explain this */
    private void request_permissions()
    {
        if (!may_access_location())
        {
            showText("Do not have access to your location!");

//           if ( ActivityCompat.shouldShowRequestPermissionRationale(thisActivity, Manifest.permission.ACCESS_FINE_LOCATION) )
//           {
//               ActivityCompat.requestPermissions(thisActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
//           }
//           else
//           {
//               showText("You have to grant this permission in your settings/apps");
//           }

            ActivityCompat.requestPermissions(thisActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /* Explain this */
    private boolean may_access_location()
    {
        if ( ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED )
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /* Explain this */
    private void enable_bluetooth()
    {
        if (mBluetoothAdapter == null)
        {
            showText("This device does not have bluetooth");
        }
        else
        {
            showText("Starting bluetooth....");
            if (! mBluetoothAdapter.isEnabled() )
            {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, BLUETOOTH_ENABLE_INTENT);
            }
        }
    }

    /* Explain this */
    private void showText(String text)
    {
        Toast.makeText(thisActivity, text, Toast.LENGTH_SHORT).show();
    }

    /* Explain this */
    private void exit()
    {
        finish();
    }
}

