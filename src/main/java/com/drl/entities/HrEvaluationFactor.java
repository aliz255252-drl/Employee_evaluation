package com.drl.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import java.time.LocalDateTime;
import org.hibernate.annotations.Type;
import lombok.*;

@Entity
@Table(name = "hr_tbl_evaluation_factor", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@TypeDef(name = "string-array", typeClass = org.hibernate.type.ArrayType.class)
public class HrEvaluationFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ser_evaluation_factor_id")
    private Integer factorId;

    @Column(name = "txt_evaluation_factor_name", length = 50)
    private String name;

    @Column(name = "txt_evaluation_factor_description", length = 3000)
    private String description;

    @Column(name = "bln_status")
    private Boolean status;

    @Column(name = "txt_machine_ip", length = 20)
    private String machineIp;

    @Column(name = "ser_modified_user_id")
    private Integer modifiedUserId;

    @Column(name = "ser_created_user_id")
    private Integer createdUserId;

    @Column(name = "dte_createddate")
    private LocalDateTime createdDate;

    @Column(name = "dte_modifieddate")
    private LocalDateTime modifiedDate;

    @Column(name = "txt_evaluation_group", length = 50)
    private String evaluationGroup;

    @Column(name = "factor_type")
    private String factorType;

    @Column(name = "total_marks")
    private Integer totalMarks;

    @Column(name = "passing_marks")
    private Integer passingMarks;

    @Type(StringArrayType.class)
    @Column(name = "levels", columnDefinition = "text[]")
    private String[] levels;
}