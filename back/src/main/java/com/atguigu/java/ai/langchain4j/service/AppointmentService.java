package com.atguigu.java.ai.langchain4j.service;

import com.atguigu.java.ai.langchain4j.entity.Appointment;

public interface AppointmentService {
    Appointment queryAppointment(String name, String idNumber);
    Appointment getOne(Appointment appointment);
    boolean save(Appointment appointment);
    boolean removeById(Long id);
}
