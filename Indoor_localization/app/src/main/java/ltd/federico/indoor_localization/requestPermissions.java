package ltd.federico.indoor_localization;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class requestPermissions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_permissions);
    }
    public void onCLickRequestPermissionsButton (View v)
    {
        Intent requestPermissionsIntent = new Intent(this, permissionsRequested.class);
        startActivity(requestPermissionsIntent);
    }
}
