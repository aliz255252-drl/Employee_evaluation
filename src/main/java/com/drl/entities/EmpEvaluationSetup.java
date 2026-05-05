package com.drl.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "hr_tbl_evaluation_setup",
       uniqueConstraints = @UniqueConstraint(columnNames = {"ser_evaluation_campaign_id", "ser_employee_id"}))
@Data
public class EmpEvaluationSetup {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evaluation_setup_seq")
	    @SequenceGenerator(name = "evaluation_setup_seq", sequenceName = "seq_evaluation_setup", allocationSize = 1)
	    @Column(name = "ser_evaluation_setup_id")
	    private Integer id;

	    @ManyToOne
	    @JoinColumn(name = "ser_evaluation_campaign_id", nullable = false)
	    private HrEvaluationCampaign campaign;

	    @ManyToOne
	    @JoinColumn(name = "ser_employee_id", nullable = false)
	    private HrEmployee employee;

	    @OneToMany(mappedBy = "evaluationSetup", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<EvaluationSetupFactor> factors = new ArrayList();

	    @OneToMany(mappedBy = "evaluationSetup", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<EvaluationSetupEvaluator> evaluators = new ArrayList();
	    
	    @Column(name = "finish_date", nullable = false)
	    private String finsihDate;
	    
	    @Column(name = "created_date")
	    private String created_date;
	    
	    @Column(name = "calculation_method")
	    private String calculationMethod;
	    
}