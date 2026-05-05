package com.drl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EvaluatorEvaluationDocumentDTO {

    private Integer id;
    private Integer ser_evaluation_setup_evaluators_id;
    private String fileName;
    private String fileType;
    private byte[] fileData;
    private java.sql.Timestamp createdAt; // <- Change from LocalDateTime

}
