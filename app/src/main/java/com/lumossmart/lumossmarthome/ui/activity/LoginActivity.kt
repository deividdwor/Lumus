package com.lumossmart.lumossmarthome.ui.activity


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_google_signin.*
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.firebase.auth.FirebaseUser
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.lumossmart.lumossmarthome.R
import com.lumossmart.lumossmarthome.model.*


class LoginActivity : AppCompatActivity(), View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    private val TAG = "JSAGoogleSignIn"
    private val REQUEST_CODE_SIGN_IN = 1234
    private lateinit var dispositivos: MutableList<Dispositivo>
    private  var usuarios = mutableListOf<Usuario>()

    private var mAuth: FirebaseAuth? = null

    private var mGoogleApiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_signin)

        btn_sign_in.setOnClickListener(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.request_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        mAuth = FirebaseAuth.getInstance()


        val mDatabaseUser = FirebaseDatabase.getInstance().getReference("casa/usuarios")

        mDatabaseUser.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {  }

            override fun onDataChange(p0: DataSnapshot?) {
                usuarios.clear()
                if(p0!!.exists()){
                    for(a in p0.children){
                        var usuario = a.getValue(Usuario::class.java)
                        usuarios.add(usuario!!)

                    }
                }
            }

        })


        val mDatabaseDisp = FirebaseDatabase.getInstance().getReference("casa/dispositivos")

        mDatabaseUser.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {  }

            override fun onDataChange(p0: DataSnapshot?) {
                dispositivos = mutableListOf()
                if(p0!!.exists()){
                    for(a in p0.children){
                        var dispositivo = a.getValue(Dispositivo::class.java)
                        dispositivo!!.id = a.key
                        dispositivos.add(dispositivo!!)

                    }
                }
            }

        })


       /* val mDatabaseDevices = FirebaseDatabase.getInstance().getReference("casa/dispositivos")

        val childEventListener = object : ChildEventListener {
            override fun onChildRemoved(p0: DataSnapshot?) {
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                    var dispositivo = dataSnapshot.getValue(Dispositivo::class.java)
                    dispositivo!!.id = dataSnapshot.key

                    for(u in usuarios){
                        var permitido = false
                        if(u.uid == dispositivo.usuarioInc){
                           permitido = true
                        }
                        val mDatabase = FirebaseDatabase.getInstance().getReference("casa/usuarios")
                        mDatabase.child(u.uid).child("permissoes").child(dispositivo.id).setValue(permitido)
                    }

            }

            override fun onCancelled(p0: DatabaseError?) {  }

        }
        mDatabaseDevices!!.addChildEventListener(childEventListener)*/

    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        if(currentUser != null){
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("nome", currentUser?.displayName)
            intent.putExtra("email", currentUser?.email)
            intent.putExtra("foto", currentUser?.photoUrl.toString())

            startActivity(intent)

        }

    }

    override fun onClick(v: View?) {
        val i = v!!.id

        when (i) {
            R.id.btn_sign_in -> signIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent();
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // successful -> authenticate with Firebase
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
            } else {
                // failed -> update UI
                updateUI(null)
                Toast.makeText(applicationContext, "SignIn: failed!",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.e(TAG, "firebaseAuthWithGoogle():" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success
                        Log.e(TAG, "signInWithCredential: Success!")
                        val user = mAuth!!.currentUser
                        val intent = Intent(this, MenuActivity::class.java)
                        intent.putExtra("nome", acct.displayName)
                        intent.putExtra("email", acct.email)
                        intent.putExtra("foto", acct.photoUrl.toString())

                        var uid = user!!.uid
                        val mDatabase = FirebaseDatabase.getInstance().getReference("casa/usuarios")

                        if(usuarios.filter { i -> i.uid == uid }.count() == 0){
                            var usuario = Usuario(uid)
                            mDatabase.child(uid).setValue(usuario)


                            var permissoes = mutableListOf<Permissao>()
                            for(d in dispositivos){
                                mDatabase.child(uid).child("permissoes").child(d.id).setValue(false)
                            }

                        }

                        startActivity(intent)
                        updateUI(user)
                    } else {
                        // Sign in fails
                        Log.w(TAG, "signInWithCredential: Failed!", task.exception)
                        Toast.makeText(applicationContext, "Authentication failed!",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.e(TAG, "onConnectionFailed():" + connectionResult);
        Toast.makeText(applicationContext, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private fun signIn() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
    }

    public fun signOut() {
        // sign out Firebase
        mAuth!!.signOut()

        // sign out Google
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback { updateUI(null) }
    }

    private fun revokeAccess() {
        // sign out Firebase
        mAuth!!.signOut()

        // revoke access Google
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback { updateUI(null) }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
           // tvStatus.text = "Google User email: " + user.email!!
           // tvDetail.text = "Firebase User ID: " + user.uid

            btn_sign_in.visibility = View.GONE
        } else {
          //  tvStatus.text = "Signed Out"
          //  tvDetail.text = null

            btn_sign_in.visibility = View.VISIBLE
           // layout_sign_out_and_disconnect.visibility = View.GONE
        }
    }
}
