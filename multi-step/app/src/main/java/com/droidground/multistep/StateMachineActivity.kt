package com.droidground.multistep

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

val flag = "DROIDGROUND_FLAG_PLACEHOLDER"

class StateMachineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_state_machine)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        stateMachine(intent);
    }


    private fun getCurrentState(): Int {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return 0
        val currentState = sharedPref.getInt("currentState", 0)
        return currentState
    }

    private fun setCurrentState(state: Number) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("currentState", state.toInt())
            apply()
        }
    }

    private fun setText(txt: String) {
        val myTextView = findViewById<TextView>(R.id.state_machine_txt)
        myTextView.text = txt
    }

    fun stateMachine(intent: Intent) {
        val action = intent.action
        val ordinal = getCurrentState()
        if (ordinal != 0) {
            if (ordinal != 1) {
                if (ordinal == 2) {
                    setCurrentState(0)
                    setText(flag)
                    return
                }
            } else if ("GET_FLAG" == action) {
                setCurrentState(2)
                setText("Transitioned from PREPARE to GET_FLAG.")
                return
            }
        }  else if ("PREPARE_FLAG" == action) {
            setCurrentState(1)
            setText("Transitioned from INIT to PREPARE.")
            return
        }

        setCurrentState(0)
    }
}