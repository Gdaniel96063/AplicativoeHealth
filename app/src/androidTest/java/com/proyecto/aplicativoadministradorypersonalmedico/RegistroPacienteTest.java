package com.proyecto.aplicativoadministradorypersonalmedico;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RegistroPacienteTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.proyecto.aplicativoadministradorypersonalmedico", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<RegistroPaciente> activityScenarioRule =
            new ActivityScenarioRule<>(RegistroPaciente.class);


    @Before
    public void setUp() {
        activityScenarioRule.getScenario().onActivity(activity -> {
        });
    }

    @Test
    public void testFindComponents() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.findComponents();
            assertNotNull(activity.txtViewNombresPaciente);
            assertNotNull(activity.inputDniPaciente);
            assertNotNull(activity.buttonBuscarPaciente);
        });
    }

    @Test
    public void testFindNullComponents() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.findComponents();

            assertNull(activity.txtViewNombresPaciente);
            assertNull(activity.inputDniPaciente);
            assertNull(activity.buttonBuscarPaciente);
        });
    }

    @Test
    public void testEmptyComponents(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.inputCorreoElectronico.setText("");
            activity.inputFechaNacimiento.setText("");
            activity.inputTelefono.setText("");

            assertFalse(activity.inputCorreoElectronico.getText().toString().isEmpty());
            assertFalse(activity.inputFechaNacimiento.getText().toString().isEmpty());
            assertFalse(activity.inputTelefono.getText().toString().isEmpty());
        });
    }

    @Test
    public void testMethodValidationEmptyComponents() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.inputCorreoElectronico.setText("");
            activity.inputFechaNacimiento.setText("");
            activity.inputTelefono.setText("");

            assertTrue(activity.valid());

        });
    }

    @Test
    public void testMethodValidationComponents() {
        activityScenarioRule.getScenario().onActivity(activity -> {

            activity.inputCorreoElectronico.setText("gerardodaniel@gmail.com");
            activity.inputFechaNacimiento.setText("01-09-2024");
            activity.inputTelefono.setText("123456789");

            assertTrue(activity.valid());

        });
    }

    @Test
    public void testClearComponents() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.inputDniPaciente.setText("72683109");
            activity.txtViewNombresPaciente.setText("GERARDO DANIEL");

            activity.clearComponents();

            assertEquals("", activity.inputDniPaciente.getText().toString());
            assertEquals("", activity.txtViewNombresPaciente.getText().toString());
        });
    }

    @Test
    public void testRegistrarPaciente() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.txtViewNombresPaciente.setText("GERARDO DANIEL");
            activity.txtViewApellidoPaternoPaciente.setText("REYNOSO");
            activity.txtViewApellidoMaternoPaciente.setText("SILVERA");
            activity.inputFechaNacimiento.setText("01-09-2024");
            activity.inputCorreoElectronico.setText("gerardodaniel@gmail.com");
            activity.inputTelefono.setText("987654321");
            activity.txtViewDniPaciente.setText("72683109");

            FirebaseFirestore mockDb = mock(FirebaseFirestore.class);
            CollectionReference mockCollection = mock(CollectionReference.class);
            DocumentReference mockDocument = mock(DocumentReference.class);
            when(mockDb.collection("Pacientes")).thenReturn(mockCollection);
            when(mockCollection.document("72683109")).thenReturn(mockDocument);
            activity.db = mockDb;

            activity.registrarPaciente();

            Map<String, Object> paciente = new HashMap<>();
            paciente.put("nombres", "GERARDO DANIEL");
            paciente.put("apellidoPaterno", "REYNOSO");
            paciente.put("apellidoMaterno", "SILVERA");
            paciente.put("fechaNacimiento", "01-09-2024");
            paciente.put("correoElectronico", "gerardodaniel@gmail.com");
            paciente.put("Telefono", "987654321");
            paciente.put("DNI", "72683109");

            verify(mockDocument).set(paciente);
        });
    }

    @Test
    public void testRegistrarPacientesFailed(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.txtViewNombresPaciente.setText("GERARDO DANIEL");
            activity.txtViewApellidoPaternoPaciente.setText("REYNOSO");
            activity.txtViewApellidoMaternoPaciente.setText("SILVERA");
            activity.inputFechaNacimiento.setText("01-09-2024");
            activity.inputCorreoElectronico.setText("gerardodaniel@gmail.com");
            activity.inputTelefono.setText("987654321");
            activity.txtViewDniPaciente.setText("72683109");

            FirebaseFirestore mockDb = mock(FirebaseFirestore.class);
            CollectionReference mockCollection = mock(CollectionReference.class);
            DocumentReference mockDocument = mock(DocumentReference.class);
            when(mockDb.collection("Pacientes")).thenReturn(mockCollection);
            when(mockCollection.document("72683109")).thenReturn(mockDocument);
            activity.db = mockDb;

            activity.registrarPaciente();

            Map<String, Object> paciente = new HashMap<>();
            paciente.put("nombres", "GERARDO");
            paciente.put("apellidoPaterno", "REYNOSO");
            paciente.put("apellidoMaterno", "SILVERA");
            paciente.put("fechaNacimiento", "01-09-2024");
            paciente.put("correoElectronico", "gerardodaniel@gmail.com");
            paciente.put("Telefono", "987654321");
            paciente.put("DNI", "72683109");

            verify(mockDocument).set(paciente);
        });
    }
}
