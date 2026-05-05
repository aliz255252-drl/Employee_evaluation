package com.drl.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "hr_tbl_evaluator_evaluation_documents")
public class EvaluatorEvaluationDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hr_tbl_evaluator_evaluation_docs_seq")
    @SequenceGenerator(name = "hr_tbl_evaluator_evaluation_docs_seq", sequenceName = "hr_tbl_evaluator_evaluation_docs_seq", allocationSize = 1)
    @Column(name = "ser_evaluator_evaluation_document_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ser_evaluation_setup_evaluators_id", nullable = false)
    private EvaluationSetupEvaluator evaluatorEvaluation;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    //@Lob
    //@Column(name = "file_data", columnDefinition = "BYTEA") // For PostgreSQL. Use BLOB for MySQL
    private byte[] fileData;

    
    
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

}
