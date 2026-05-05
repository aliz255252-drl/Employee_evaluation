package com.drl.utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluatorEvaluationDTO {

    private Integer ser_evaluator_evaluation_id;
    private Integer ser_evaluation_factor_id;
    private Integer ser_user_id;
    private Integer obtain_marks;
    private String 	obtain_level;
    private String 	recommendation;
    private String 	user_comments;
    private Integer ser_evaluation_setup_evaluators_id;

}
