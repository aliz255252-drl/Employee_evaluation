package com.drl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EvaluationGradesDTO {

    private Integer evaluationSetupGradeId;
    private String grade;
    private Double initialMarks;
    private Double finalMarks;
    private String description;
    private Integer evaluationSetupId;  // Instead of whole object, just ID
}