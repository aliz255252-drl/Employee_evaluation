package com.drl.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.drl.entities.EvaluatorEvaluationDocument;
import com.drl.utils.EvaluatorEvaluationDocumentDTO;
import com.drl.entities.EvaluationSetupEvaluator;

import java.util.List;

@Repository
public interface EvaluatorEvaluationDocumentRepository extends JpaRepository<EvaluatorEvaluationDocument, Integer> {

    // ✅ Find all documents by evaluatorEvaluation
    @Query(value ="SELECT e.* FROM hr_tbl_evaluator_evaluation_documents e WHERE e.ser_evaluation_setup_evaluators_id = :evaluatorEvaluationId",nativeQuery = true)
    List<EvaluatorEvaluationDocumentDTO> findByEvaluatorEvaluation(@Param("evaluatorEvaluationId") Integer evaluatorEvaluationId);

    // ✅ Optional: Find by file name (if needed)
    List<EvaluatorEvaluationDocument> findByFileName(String fileName);

    // ✅ Optional: Delete all documents linked to a specific evaluator
    int deleteByEvaluatorEvaluation(EvaluationSetupEvaluator evaluatorEvaluation);
}
