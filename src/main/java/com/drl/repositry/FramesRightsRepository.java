package com.drl.repositry;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.drl.entities.FramesRights;

public interface FramesRightsRepository extends JpaRepository<FramesRights, Integer> {


	@Query(value = "select txt_form_title from sysmgr_Tbl_forms where ser_form_id = :ser_form_id", nativeQuery = true)
	String getFormName(@Param("ser_form_id")Integer id);
	
	@Query(value = "SELECT fr.* " +
            "FROM sysmgr_tbl_user_rights_on_form fr " +
            "JOIN sysmgr_tbl_forms f ON fr.ser_form_id = f.ser_form_id " +
            "JOIN sysmgr_tbl_modules m ON f.ser_modules_id = m.ser_modules_id " +
            "WHERE fr.ser_user_id = :userId " +
            "AND m.txt_module_name = 'Employee_Evaluation_Extension'", 
            nativeQuery = true)
	List<FramesRights> getFrameRight(@Param("userId") Integer id);
	
}
