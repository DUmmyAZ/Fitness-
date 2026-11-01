package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceWhite
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.ZoZoOrange
import com.example.ui.theme.ZoZoPurple

@Composable
fun LogWeightDialog(
    currentWeightKg: Float,
    useLbs: Boolean,
    onDismiss: () -> Unit,
    onSaveWeight: (Float) -> Unit
) {
    val initialVal = if (useLbs) {
        String.format("%.1f", currentWeightKg * 2.20462f)
    } else {
        String.format("%.1f", currentWeightKg)
    }
    var weightText by remember { mutableStateOf(initialVal) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Log Today's Body Weight",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
        },
        text = {
            Column {
                Text(
                    text = "Track weight progression on your trend chart. Enter your morning measurement.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(14.dp))
                OutlinedTextField(
                    value = weightText,
                    onValueChange = { weightText = it },
                    label = { Text("Weight in ${if (useLbs) "lbs" else "kg"}") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ZoZoPurple,
                        unfocusedBorderColor = SurfaceBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_weight_field")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val parsed = weightText.toFloatOrNull()
                    if (parsed != null && parsed > 0) {
                        val kgValue = if (useLbs) parsed / 2.20462f else parsed
                        onSaveWeight(kgValue)
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ZoZoPurple),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("confirm_weight_button")
            ) {
                Text("Save", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("cancel_weight_button")
            ) {
                Text("Cancel", color = TextSecondary)
            }
        },
        containerColor = SurfaceWhite,
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
fun AddPrDialog(
    onDismiss: () -> Unit,
    onSavePr: (String, String) -> Unit
) {
    var exerciseName by remember { mutableStateOf("") }
    var recordValue by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Log New Personal Best (PR)",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = TextPrimary
            )
        },
        text = {
            Column {
                Text(
                    text = "Celebrate your strength milestones and track your peak power output.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(14.dp))
                OutlinedTextField(
                    value = exerciseName,
                    onValueChange = { exerciseName = it },
                    label = { Text("Exercise (e.g. Incline Bench, Clean)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ZoZoPurple,
                        unfocusedBorderColor = SurfaceBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_pr_exercise")
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = recordValue,
                    onValueChange = { recordValue = it },
                    label = { Text("Weight / Reps (e.g. 100 kg × 5 reps)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ZoZoPurple,
                        unfocusedBorderColor = SurfaceBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_pr_value")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (exerciseName.isNotBlank() && recordValue.isNotBlank()) {
                        onSavePr(exerciseName.trim(), recordValue.trim())
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ZoZoOrange),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("confirm_pr_button")
            ) {
                Text("Save PR", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("cancel_pr_button")
            ) {
                Text("Cancel", color = TextSecondary)
            }
        },
        containerColor = SurfaceWhite,
        shape = RoundedCornerShape(20.dp)
    )
}
