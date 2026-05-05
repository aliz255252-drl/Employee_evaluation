package com.drl.entities;

import jakarta.persistence.GeneratedValue;
import lombok.Data;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "sysmgr_tbl_user_rights_on_form", schema = "public")
public class FramesRights {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ser_user_rights_on_form_id", nullable = false)
	    private Integer id;

	    @Column(name = "bln_can_create")
	    private Boolean canCreate;

	    @Column(name = "bln_can_update")
	    private Boolean canUpdate;

	    @Column(name = "bln_can_delete")
	    private Boolean canDelete;

	    @Column(name = "bln_can_print")
	    private Boolean canPrint;

	    @Column(name = "bln_can_approve")
	    private Boolean canApprove;

	    @Column(name = "ser_form_id")
	    private Integer formId;

	    @Column(name = "ser_user_id")
	    private Integer userId;

	    @Column(name = "dte_createddate")
	    private LocalDateTime createdDate;

	    @Column(name = "dte_modifieddate")
	    private LocalDateTime modifiedDate;

	    @Column(name = "bln_status")
	    private Boolean status;

	    @Column(name = "txt_machine_ip", length = 20)
	    private String machineIp;

	    @Column(name = "ser_created_user_id")
	    private Integer createdUserId;

	    @Column(name = "ser_modified_user_id")
	    private Integer modifiedUserId;

	    @Column(name = "ser_status_id")
	    private Integer statusId;

	    @Column(name = "bln_can_read")
	    private Boolean canRead;

	    @Column(name = "ser_groups_users_id")
	    private Integer groupUserId;

	    @Column(name = "bln_can_reject")
	    private Boolean canReject;

	    @Column(name = "bln_can_view")
	    private Boolean canView;

	    @Column(name = "bln_can_post")
	    private Boolean canPost;

	    @Column(name = "bln_can_check")
	    private Boolean canCheck = false;

	    @Column(name = "bln_can_recommend")
	    private Boolean canRecommend = false;

	    @Column(name = "bln_can_review")
	    private Boolean canReview = false;

	    @Column(name = "txt_approver")
	    private String approver;

	    @Column(name = "bln_can_view_all")
	    private Boolean canViewAll = true;

}