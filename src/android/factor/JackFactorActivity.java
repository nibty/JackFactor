package android.factor;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class JackFactorActivity extends Activity {

	private JackFactor factor;
    private Button okButton, randomButton, clearButton;
	private EditText numberfield;
	private TextView factorsText, multiplesText, numberText;
	private TextView algorithmText, differenceText, timeText;

	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //  Buttons
        okButton        = (Button) findViewById(R.id.okButton);
        randomButton    = (Button) findViewById(R.id.randomButton);
        clearButton     = (Button) findViewById(R.id.clearButton);
        
        //  Text field
        numberfield     = (EditText) findViewById(R.id.numberField);

    	//  Text views
        numberText      = (TextView) findViewById(R.id.numberText);
    	factorsText     = (TextView) findViewById(R.id.factorsText);
    	multiplesText   = (TextView) findViewById(R.id.multiplesText);
    	differenceText  = (TextView) findViewById(R.id.differenceText);
    	algorithmText   = (TextView) findViewById(R.id.algorithmText);
    	timeText        = (TextView) findViewById(R.id.timeText);
         
    	//  OK button listener
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	disableButtons();
            	if (!numberfield.getText().toString().equals("")) {
               	    factor = new JackFactor(numberfield.getText().toString());
        		    factor.calculate();
        		    fillFields();
        		    enableButtons();
            	}
            }
        });
        
    	//  Random button listener
        randomButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	disableButtons();
               	factor = new JackFactor();
        		factor.calculate();
        		numberfield.setText(factor.getNumber().toString());
        		fillFields();
        		enableButtons();
            }
        });
        
    	//  Clear button listener
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	disableButtons();
            	clearFields();
            	enableButtons();
            }
        });
        
        //  Number field listener
        numberfield.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                	okButton.performClick();
                }    
                return false;
            }
        });
    }
    
    private void fillFields()
    {	
    	//  Set number
    	numberText.setText(factor.getNumber().toString());
    		
    	//  Set factors
    	factorsText.setText(factor.getFactors().toString());
     
    	//  Set multiples
    	String multipliers = String.format("%s * %s", factor.getMultiplier(), factor.getOtherMultiplier());       		
    	multiplesText.setText(multipliers);
    		
    	//  Set difference
    	differenceText.setText(factor.getLowestDiff().toString());

    	//  Set algorithm
    	algorithmText.setText(factor.getFactorAlgorithm());
    	
    	//  Set run time
    	timeText.setText(Long.toString(factor.getTime()) + "ms");
     }
       
     private void clearFields()
     {
    	 numberfield.setText("");
    	 numberText.setText("");
    	 factorsText.setText("");
    	 multiplesText.setText("");
    	 differenceText.setText("");
    	 timeText.setText("");
    	 algorithmText.setText("");
     }
     
     private void disableButtons()
     {
    	 okButton.setClickable(false);
    	 randomButton.setClickable(false);
    	 clearButton.setClickable(false);
     }
     
     private void enableButtons()
     {
    	 okButton.setClickable(true);
    	 randomButton.setClickable(true);
    	 clearButton.setClickable(true);
     }
}