package com.atguigu.java.ai.langchain4j.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Appointment {
    private Long id;
    private String username;
    private String idCard;
    private String department;
    private String date;
    private String time;
    private String doctorName;
    // 兼容大模型可能使用的字段名
    private String name;
    private String idNumber;
    private String hospital;
    private String doctor;
}
