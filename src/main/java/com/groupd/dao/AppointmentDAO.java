package com.groupd.dao;

import com.groupd.beans.Appointment;
import com.groupd.utils.DataSourceUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // Modified addAppointment to return a boolean indicating success
    public boolean addAppointment(Appointment appointment) throws SQLException {
        String query = "INSERT INTO Appointments (patient_id, doctor_id, appointment_date, appointment_time, feedback) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, appointment.getPatientId());
            statement.setString(2, appointment.getDoctorId());
            statement.setDate(3, appointment.getAppointmentDate());
            statement.setTime(4, appointment.getAppointmentTime());
            statement.setString(5, appointment.getFeedback());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was affected
        }
    }

    public Appointment getAppointment(int appointmentId) throws SQLException {
        String query = "SELECT * FROM Appointments WHERE appointment_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, appointmentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(resultSet.getInt("appointment_id"));
                    appointment.setPatientId(resultSet.getString("patient_id"));
                    appointment.setDoctorId(resultSet.getString("doctor_id"));
                    appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
                    appointment.setAppointmentTime(resultSet.getTime("appointment_time"));
                    appointment.setFeedback(resultSet.getString("feedback"));
                    return appointment;
                }
            }
        }
        return null;
    }


    public Appointment getPatientAppointment(String patientId) throws SQLException {
        String query = "SELECT * FROM Appointments WHERE patient_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(resultSet.getInt("appointment_id"));
                    appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
                    appointment.setAppointmentTime(resultSet.getTime("appointment_time"));
                    appointment.setFeedback(resultSet.getString("feedback"));
                    return appointment;
                }
            }
        }
        return null;
    }

    public List<Appointment> getAllAppointments() throws SQLException {
        String query = "SELECT * FROM Appointments";
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt("appointment_id"));
                appointment.setPatientId(resultSet.getString("patient_id"));
                appointment.setDoctorId(resultSet.getString("doctor_id"));
                appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
                appointment.setAppointmentTime(resultSet.getTime("appointment_time"));
                appointment.setFeedback(resultSet.getString("feedback"));
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    public String getDoctorNameById(String doctorId) throws SQLException {
        String query = "SELECT first_name, last_name FROM Doctors WHERE doctor_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, doctorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    return firstName + " " + lastName; // Combine first and last name
                }
            }
        }
        return null; // Return null if the doctor is not found
    }


    public List<Appointment> getAllAppointmentsByPatientId(String patientId) throws SQLException {
        String query = "SELECT * FROM Appointments WHERE patient_id = ?";
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(resultSet.getInt("appointment_id"));
                    appointment.setPatientId(resultSet.getString("patient_id"));
                    appointment.setDoctorId(resultSet.getString("doctor_id"));
                    appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
                    appointment.setAppointmentTime(resultSet.getTime("appointment_time"));
                    appointment.setFeedback(resultSet.getString("feedback"));
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }
    public void updateAppointment(Appointment appointment) throws SQLException {
        String query = "UPDATE Appointments SET patient_id = ?, doctor_id = ?, appointment_date = ?, appointment_time = ?, feedback = ? WHERE appointment_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, appointment.getPatientId());
            statement.setString(2, appointment.getDoctorId());
            statement.setDate(3, appointment.getAppointmentDate());
            statement.setTime(4, appointment.getAppointmentTime());
            statement.setString(5, appointment.getFeedback());
            statement.setInt(6, appointment.getAppointmentId());
            statement.executeUpdate();
        }
    }

    public void deleteAppointment(int appointmentId) throws SQLException {
        String query = "DELETE FROM Appointments WHERE appointment_id = ?";
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, appointmentId);
            statement.executeUpdate();
        }
    }

    public List<Appointment> getAppointmentsByDoctor(String doctorId) throws SQLException {
        String query = "SELECT * FROM Appointments WHERE doctor_id = ? AND (feedback IS NULL OR feedback = '')";
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, doctorId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(resultSet.getInt("appointment_id"));
                    appointment.setPatientId(resultSet.getString("patient_id"));
                    appointment.setDoctorId(resultSet.getString("doctor_id"));
                    appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
                    appointment.setAppointmentTime(resultSet.getTime("appointment_time"));
                    appointment.setFeedback(resultSet.getString("feedback"));
                    appointments.add(appointment);
                }
            }
        }
        return appointments;
    }


    public List<Appointment> getPendingAppointmentsByDoctor(String doctorId) {
        List<Appointment> pendingAppointments = new ArrayList<>();
        String query = "SELECT * FROM Appointments WHERE doctor_id = ? AND (feedback IS NULL OR feedback = '')";

        try (Connection connection = DataSourceUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt("appointment_id"));
                appointment.setDoctorId(resultSet.getString("doctor_id"));
                appointment.setPatientId(resultSet.getString("patient_id"));
                appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
                appointment.setAppointmentTime(resultSet.getTime("appointment_time"));
                appointment.setFeedback(resultSet.getString("feedback"));
                // Add any other fields as necessary

                pendingAppointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pendingAppointments;
    }


}
