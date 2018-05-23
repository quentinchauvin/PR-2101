package pr_2101.josselin.extruder.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import pr_2101.josselin.extruder.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int REQUEST_CHANGE_ACTIVITY = 1;
    private static final int RC_SIGN_IN = 3;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    EditText mEmailText;
    TextInputEditText mPasswordText;
    CircularProgressButton mLoginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        mEmailText = findViewById(R.id.input_email);
        mPasswordText = findViewById(R.id.input_password);

        mLoginButton = findViewById(R.id.btn_sign_in);
        findViewById(R.id.link_signup).setOnClickListener(this);
        findViewById(R.id.google_login_button).setOnClickListener(this);

        mLoginButton.setCompleteText("Done ;)");
        mLoginButton.setErrorText("Error");
        mLoginButton.setIdleText("Sign in");
        mLoginButton.setIndeterminateProgressMode(true);
        mLoginButton.setOnClickListener(this);

        mEmailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mLoginButton.setProgress(0);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();

    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Log.i(TAG, "Auth signed yet");
            //mAuth.signOut();
            startActivityForResult(new Intent(getApplicationContext(), MainActivity.class), REQUEST_CHANGE_ACTIVITY);
        } else {
            Log.i(TAG, "Auth not signed");
        }
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mLoginButton.setProgress(100);
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Authentication succeeded.",
                                    Toast.LENGTH_SHORT).show();
                            onLoginSuccess();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            mLoginButton.setProgress(-1);
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        //progressDialog.dismiss();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_SIGNUP:
                if (resultCode == RESULT_OK) {
                    this.finish();
                }
                break;

            case REQUEST_CHANGE_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    this.finish();
                }
                break;

            case RC_SIGN_IN:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    fireBaseAuthWithGoogle(account);
                    onLoginSuccess();
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                    onLoginFailed();
                }
                break;
        }
    }

    private void fireBaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.i(TAG, "fireBaseAuthWithGoogle:" + acct.getEmail());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    /**
     * Login was a success, we can try to change activity.
     */
    public void onLoginSuccess() {
        mLoginButton.setProgress(CircularProgressButton.SUCCESS_STATE_PROGRESS);
        Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_LONG).show();
        startActivityForResult(new Intent(LoginActivity.this, MainActivity.class),
                REQUEST_CHANGE_ACTIVITY);
        finish();
    }

    /**
     * Display that it failed with toast and login button turning red.
     */
    public void onLoginFailed() {
        mLoginButton.setProgress(-1);
        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
        mLoginButton.setEnabled(true);
    }

    /**
     * Check if email and password fields are not empty and if email match with android email pattern.
     *
     * @return true if fields are ok, else false
     */
    public boolean validate() {
        boolean valid = true;

        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError("enter a valid email address");
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        if (password.isEmpty()) {
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        switch (i) {
            case R.id.btn_sign_in:
                mLoginButton.setProgress(50);
                login();
                break;
            case R.id.google_login_button:
                googleSignIn();
                break;
            case R.id.link_signup:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                break;
        }
    }
}

