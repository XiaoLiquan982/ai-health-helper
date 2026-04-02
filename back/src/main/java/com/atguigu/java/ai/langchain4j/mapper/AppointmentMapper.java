package com.atguigu.java.ai.langchain4j.mapper;

import com.atguigu.java.ai.langchain4j.entity.Appointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppointmentMapper {
    Appointment selectBy_name_and_idNumber(@Param("name") String name, @Param("idNumber") String idNumber);
    Appointment selectByAppointment(Appointment appointment);
    int insert(Appointment appointment);
    int deleteById(@Param("id") Long id);
}
