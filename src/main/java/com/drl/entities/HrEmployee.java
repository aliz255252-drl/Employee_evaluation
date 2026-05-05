package com.drl.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import org.hibernate.annotations.JdbcTypeCode;
import java.sql.Types;
@Entity
@Table(name = "hr_tbl_employee", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HrEmployee {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ser_employee_id")
    private Integer empId;

    @Column(name = "txt_employee_id")
    private String employeeId;

    @Column(name = "txt_applicant_id")
    private String applicantId;

    @Column(name = "txt_name")
    private String name;

    @Column(name = "txt_nic_no")
    private String nicNo;

    @Column(name = "txt_father_name")
    private String fatherName;

    @Column(name = "txt_sur_name")
    private String surName;

    @Column(name = "dte_dob")
    private Date dob;

    @Column(name = "txt_material_status")
    private String materialStatus;

    @Column(name = "txt_phone_no_1")
    private String phoneNo1;

    @Column(name = "txt_phone_no_2")
    private String phoneNo2;

    @Column(name = "txt_mobile_no")
    private String mobileNo;

    @Column(name = "txt_email_address")
    private String emailAddress;

    @Column(name = "txt_present_address")
    private String presentAddress;

    @Column(name = "txt_permanent_address")
    private String permanentAddress;

    @Column(name = "dte_applicationdate")
    private Date applicationDate;

 

    @Column(name = "txt_gender", length = 1)
    @JdbcTypeCode(Types.CHAR)
    private String gender;


    @Column(name = "txt_religion")
    private String religion;

    @Column(name = "dte_letterdate")
    private Date letterDate;

    @Column(name = "dte_appointmentdate")
    private Date appointmentDate;

    @Column(name = "dte_joiningdate")
    private Date joiningDate;

    @Column(name = "num_salary")
    private BigDecimal salary;

    @Column(name = "txt_remarks")
    private String remarks;

    @Column(name = "pic_biometric_barcode")
    private byte[] biometricBarcode;

    @Column(name = "txt_status")
    private String status;

    @Column(name = "txt_status_reason")
    private String statusReason;

    @Column(name = "dte_status_date")
    private Date statusDate;

    @Column(name = "ser_designation_id")
    private Integer designationId;

    @Column(name = "ser_bps_id")
    private Integer bpsId;

    @Column(name = "ser_employee_type_id")
    private Integer employeeTypeId;

    @Column(name = "ser_payment_mode_id")
    private Integer paymentModeId;

    @Column(name = "ser_approverby_id")
    private Integer approverById;

    @Column(name = "ser_country_id")
    private Integer countryId;

    @Column(name = "ser_postadvertised_media_id")
    private Integer postAdvertisedMediaId;

    @Column(name = "bln_status")
    private Boolean activeStatus;

    @Column(name = "txt_machine_ip")
    private String machineIp;

    @Column(name = "ser_created_user_id")
    private Integer createdUserId;

    @Column(name = "ser_modified_user_id")
    private Integer modifiedUserId;

    @Column(name = "ser_currency_definition_id")
    private Integer currencyDefinitionId;

    @Column(name = "ser_department_id")
    private Integer departmentId;

    @Column(name = "dte_contract_final_date")
    private Date contractFinalDate;

    @Column(name = "ser_salary_duration_type_id")
    private Integer salaryDurationTypeId;

    @Column(name = "pic_picture")
    private String picture;

    @Column(name = "dte_createddate")
    private Timestamp createdDate;

    @Column(name = "dte_modifieddate")
    private Timestamp modifiedDate;

    @Column(name = "txt_nationality")
    private String nationality;

    @Column(name = "txt_domicile")
    private String domicile;

    @Column(name = "txt_phone_extension")
    private String phoneExtension;

    @Column(name = "bln_is_per_piece")
    private Boolean perPiece;

    @Column(name = "ser_shift_info_id")
    private Integer shiftInfoId;

    @Column(name = "num_probation_period_inmonths")
    private Short probationPeriodInMonths;

    @Column(name = "num_previous_salary")
    private BigDecimal previousSalary;

    @Column(name = "num_expected_salary")
    private BigDecimal expectedSalary;

    @Column(name = "bln_old_age_benefit")
    private Boolean oldAgeBenefit;

    @Column(name = "bln_company_transport")
    private Boolean companyTransport;

    @Column(name = "txt_licence_no")
    private String licenceNo;

    @Column(name = "bln_ss_fund")
    private Boolean ssFund;

    @Column(name = "txt_blood_group")
    private String bloodGroup;

    @Column(name = "dte_date_of_leaving")
    private Date dateOfLeaving;

    @Column(name = "ser_manager_id")
    private Integer managerId;

    @Column(name = "txt_job_description")
    private String jobDescription;

    @Column(name = "txt_employee_eob_no")
    private String employeeEobNo;

    @Column(name = "txt_employee_ssn_no")
    private String employeeSsnNo;

    @Column(name = "bln_overtime_allowed")
    private Boolean overtimeAllowed;

    @Column(name = "bln_employee_card_issue")
    private Boolean employeeCardIssue;

    @Column(name = "bln_somker")
    private Boolean smoker;

    @Column(name = "bln_offer_prayer")
    private Boolean offerPrayer;

    @Column(name = "ser_granter_id")
    private Integer granterId;

    @Column(name = "ser_employee_group_id")
    private Integer employeeGroupId;

    @Column(name = "dte_confirmation")
    private Date confirmationDate;

    @Column(name = "txt_ntn_no")
    private String ntnNo;

    @Column(name = "bln_is_athorized")
    private Boolean authorized;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "ser_payscale_id")
    private Integer payscaleId;

    @Column(name = "bln_officiating")
    private Boolean officiating;

    @Column(name = "bln_substantive")
    private Boolean substantive;

    @Column(name = "ser_bridge_id")
    private Integer bridgeId;

    @Column(name = "ser_rank_id")
    private Integer rankId;

    @Column(name = "num_total_pre_incom")
    private BigDecimal totalPreIncome;

    @Column(name = "num_total_pre_tax")
    private BigDecimal totalPreTax;

    @Column(name = "dte_entry_date")
    private Date entryDate;

    @Column(name = "bln_tax_exempt")
    private Boolean taxExempt;

    @Column(name = "txt_passport_no")
    private String passportNo;

    @Column(name = "dte_passport_expiry_date")
    private Date passportExpiryDate;

    @Column(name = "dte_id_expiry_date")
    private Date idExpiryDate;

    @Column(name = "ser_branch_id")
    private Integer branchId;

    @Column(name = "txt_general_field1")
    private String generalField1;

    @Column(name = "txt_general_field2")
    private String generalField2;

    @Column(name = "txt_general_field3")
    private String generalField3;

    @Column(name = "num_payscale_stage")
    private BigDecimal payscaleStage;

    @Column(name = "dte_increment_date")
    private Date incrementDate;

    @Column(name = "emp_opening_bal")
    private Integer empOpeningBalance;

    @Column(name = "ser_employee_requisition_id")
    private Integer employeeRequisitionId;

    @Column(name = "ser_employee_requsition_id")
    private Integer employeeRequsitionId;

    @Column(name = "txt_approver")
    private String approver;
}
