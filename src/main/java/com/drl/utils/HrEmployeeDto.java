package com.drl.utils;


import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HrEmployeeDto {
    private Integer ser_employee_id;
    private String txt_employee_id;
    private String applicantId;
    private String txt_name;
    private String txt_employee_group;
    private String designation;
    private String txt_department_name;
    private String nicNo;
    private String fatherName;
    private String surName;
    private Date dob;
    private String materialStatus;
    private String phoneNo1;
    private String phoneNo2;
    private String mobileNo;
    private String emailAddress;
    private String presentAddress;
    private String permanentAddress;
    private Date applicationDate;
    private String gender;
    private String religion;
    private BigDecimal salary;
    private String status;
    private String statusReason;
    private Integer designationId;
    private Boolean activeStatus;
    private Integer departmentId;
    private Integer salaryDurationTypeId;
    private String nationality;
    private String domicile;
    
    public HrEmployeeDto(Integer ser_employee_id, String txt_employee_id, String txt_name, 
            String txt_employee_group, String designation, String txt_department_name) {
this.ser_employee_id = ser_employee_id;
this.txt_employee_id = txt_employee_id;
this.txt_name = txt_name;
this.txt_employee_group = txt_employee_group;
this.designation = designation;
this.txt_department_name = txt_department_name;
}
    
}

