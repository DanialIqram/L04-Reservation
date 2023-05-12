package sg.edu.rp.c346.id22022260.l04_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameEdit, mobileEdit, grpSizeEdit;
    DatePicker datePicker;
    TimePicker timePicker;
    RadioGroup smokingRadioGroup;
    CheckBox confirmCheckbox;
    Button reserveButton;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit = findViewById(R.id.nameEdit);
        mobileEdit = findViewById(R.id.mobileNumberEdit);
        grpSizeEdit = findViewById(R.id.groupSizeEdit);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);

        smokingRadioGroup = findViewById(R.id.smokingGroup);
        confirmCheckbox = findViewById(R.id.confirmCheckbox);

        reserveButton = findViewById(R.id.reserveButton);
        resetButton = findViewById(R.id.resetButton);

        // default time and date:
        datePicker.setMinDate(System.currentTimeMillis()); // Enhancement 2
        timePicker.setCurrentHour(19);
        timePicker.setCurrentMinute(30);
        timePicker.setIs24HourView(true);

        reserveButton.setEnabled(false);

        // Enhancement #2
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < 8 || hourOfDay > 20) {
                    view.setCurrentHour(8);
                    Toast.makeText(MainActivity.this, "Restaurant is close at that time", Toast.LENGTH_LONG).show();
                }
            }
        });

        // changes reserveButton enabled attribute
        confirmCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reserveButton.setEnabled(confirmCheckbox.isChecked());
            }
        });

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                // Enhancement #1
                if (isEmpty(nameEdit) | isEmpty(mobileEdit) | isEmpty(grpSizeEdit)) {
                    Toast.makeText(MainActivity.this, "Some field(s) are empty", Toast.LENGTH_LONG).show();
                    return;
                }

                String message = "RESERVATION DETAILS:";

                message += "\nName: " + nameEdit.getText().toString();
                message += "\nMobile No: " + mobileEdit.getText().toString();
                message += "\nGroup Size: " + grpSizeEdit.getText().toString();
                message += String.format("\nDate: %d/%d/%d", datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear());
                message += String.format("\nTime: %d:%d", timePicker.getCurrentHour(), timePicker.getCurrentMinute());

                if (smokingRadioGroup.getCheckedRadioButtonId() == R.id.smokingRadio) {
                    message += "\nArea Type: Smoking";
                } else {
                    message += "\nArea Type: Non-Smoking";
                }

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameEdit.setText("");
                mobileEdit.setText("");
                grpSizeEdit.setText("");

                datePicker.updateDate(2023, 0, 1);
                timePicker.setCurrentHour(19);
                timePicker.setCurrentMinute(30);

                smokingRadioGroup.check(R.id.smokingRadio);
                confirmCheckbox.setChecked(false);
                reserveButton.setEnabled(false);
            }
        });
    }

    // Enhancement #1
    public boolean isEmpty(EditText field) {
        String result = field.getText().toString();
        return result.isEmpty();
    }
}