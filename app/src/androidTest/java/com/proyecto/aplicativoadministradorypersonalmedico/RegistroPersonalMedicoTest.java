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
public class RegistroPersonalMedicoTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.proyecto.aplicativoadministradorypersonalmedico", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<RegistroPersonalMedico> activityScenarioRule =
            new ActivityScenarioRule<>(RegistroPersonalMedico.class);


    @Before
    public void setUp() {
        activityScenarioRule.getScenario().onActivity(activity -> {
        });
    }

    @Test
    public void testFindComponents() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.findComponents();
            assertNotNull(activity.txtViewDniPersonalMedico);
            assertNotNull(activity.txtViewNombresPersonalMedico);
            assertNotNull(activity.txtViewApellidoPaternoPersonalMedico);
        });
    }

    @Test
    public void testFindNullComponents() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.findComponents();
            assertNull(activity.txtNullTest);
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
            activity.inputDniPersonalMedico.setText("72683109");
            activity.txtViewNombresPersonalMedico.setText("GERARDO DANIEL");

            activity.clearComponents();

            assertTrue(activity.inputDniPersonalMedico.getText().toString().isEmpty());
            assertEquals("", activity.txtViewNombresPersonalMedico.getText().toString());
        });
    }

    @Test
    public void testRegistrarPersonalMedico() {
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.txtViewNombresPersonalMedico.setText("GERARDO DANIEL");
            activity.txtViewApellidoPaternoPersonalMedico.setText("REYNOSO");
            activity.txtViewApellidoMaternoPersonalMedico.setText("SILVERA");
            activity.inputFechaNacimiento.setText("01-09-2024");
            activity.inputCorreoElectronico.setText("gerardodaniel@gmail.com");
            activity.inputTelefono.setText("987654321");
            activity.txtViewDniPersonalMedico.setText("72683109");
            activity.spinnerEspecialidad.setSelection(0);

            FirebaseFirestore mockDb = mock(FirebaseFirestore.class);
            CollectionReference mockCollection = mock(CollectionReference.class);
            DocumentReference mockDocument = mock(DocumentReference.class);
            when(mockDb.collection("PersonalMedico")).thenReturn(mockCollection);
            when(mockCollection.document("72683109")).thenReturn(mockDocument);
            activity.db = mockDb;

            activity.registrarPersonalMedico();

            Map<String, Object> personalMedico = new HashMap<>();
            personalMedico.put("nombres", "GERARDO DANIEL");
            personalMedico.put("apellidoPaterno", "REYNOSO");
            personalMedico.put("apellidoMaterno", "SILVERA");
            personalMedico.put("fechaNacimiento", "01-09-2024");
            personalMedico.put("correoElectronico", "gerardodaniel@gmail.com");
            personalMedico.put("Telefono", "987654321");
            personalMedico.put("Especialidad", "Cardiologia");
            personalMedico.put("DNI", "72683109");

            verify(mockDocument).set(personalMedico);
        });
    }

    @Test
    public void testRegistrarPacientesFailed(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            activity.txtViewNombresPersonalMedico.setText("GERARDO DANIEL");
            activity.txtViewApellidoPaternoPersonalMedico.setText("REYNOSO");
            activity.txtViewApellidoMaternoPersonalMedico.setText("SILVERA");
            activity.inputFechaNacimiento.setText("01-09-2024");
            activity.inputCorreoElectronico.setText("gerardodaniel@gmail.com");
            activity.inputTelefono.setText("987654321");
            activity.txtViewDniPersonalMedico.setText("72683109");
            activity.spinnerEspecialidad.setSelection(2);

            FirebaseFirestore mockDb = mock(FirebaseFirestore.class);
            CollectionReference mockCollection = mock(CollectionReference.class);
            DocumentReference mockDocument = mock(DocumentReference.class);
            when(mockDb.collection("Pacientes")).thenReturn(mockCollection);
            when(mockCollection.document("72683109")).thenReturn(mockDocument);
            activity.db = mockDb;

            activity.registrarPersonalMedico();

            Map<String, Object> personalMedico = new HashMap<>();
            personalMedico.put("nombres", "GERARDO");
            personalMedico.put("apellidoPaterno", "REYNOSO");
            personalMedico.put("apellidoMaterno", "SILVERA");
            personalMedico.put("fechaNacimiento", "01-09-2024");
            personalMedico.put("correoElectronico", "gerardodaniel@gmail.com");
            personalMedico.put("Telefono", "987654321");
            personalMedico.put("Especialidad", "Psicología");
            personalMedico.put("DNI", "72683109");

            verify(mockDocument).set(personalMedico);
        });
    }
}
