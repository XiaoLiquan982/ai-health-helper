package com.atguigu.java.ai.langchain4j.service.impl;

import com.atguigu.java.ai.langchain4j.entity.Appointment;
import com.atguigu.java.ai.langchain4j.mapper.AppointmentMapper;
import com.atguigu.java.ai.langchain4j.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public Appointment queryAppointment(String name, String idNumber) {
        return appointmentMapper.selectBy_name_and_idNumber(name, idNumber);
    }

    @Override
    public Appointment getOne(Appointment appointment) {
        return appointmentMapper.selectByAppointment(appointment);
    }

    @Override
    public boolean save(Appointment appointment) {
        return appointmentMapper.insert(appointment) > 0;
    }

    @Override
    public boolean removeById(Long id) {
        return appointmentMapper.deleteById(id) > 0;
    }
}
