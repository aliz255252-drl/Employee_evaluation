package com.drl.service;

import com.drl.entities.HrEvaluationTarget;
import java.util.List;

public interface HrEvaluationTargetService {
    HrEvaluationTarget createTarget(HrEvaluationTarget target);
    HrEvaluationTarget updateTarget(HrEvaluationTarget target);
    List<HrEvaluationTarget> getAllTargets();
	String deleteTarget(String name);
}
