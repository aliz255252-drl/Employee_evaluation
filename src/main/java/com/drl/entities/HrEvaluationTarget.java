package com.drl.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hr_tbl_evaluation_target", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HrEvaluationTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ser_evaluation_target_id")
    private Integer id;

    @Column(name = "ser_employee_id")
    private BigDecimal employeeId;

    @Column(name = "ser_evaluation_campaign_id")
    private BigDecimal evaluationCampaignId;

    @Column(name = "txt_target_name", length = 200)
    private String targetName;

    @Column(name = "txt_description", length = 2000)
    private String description;

    @Column(name = "txt_achievement", length = 1000)
    private String achievement;

    @Column(name = "num_total_marks")
    private BigDecimal totalMarks;

    @Column(name = "num_marks_obtained")
    private BigDecimal marksObtained;

    @Column(name = "dte_createddate")
    private LocalDateTime createdDate;

    @Column(name = "dte_modifieddate")
    private LocalDateTime modifiedDate;
}
