package com.drl.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hr_tbl_evaluation_campaign", schema = "public")
public class HrEvaluationCampaign {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ser_evaluation_campaign_id")
    private Integer campaignId;

    @Column(name = "txt_name", length = 50)
    private String name;

    @Column(name = "txt_description", length = 3000)
    private String description;

    @Column(name = "txt_year")
    private String year;

    @Column(name = "dte_start")
    private LocalDate startDate;

    @Column(name = "dte_end")
    private LocalDate endDate;

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
}

