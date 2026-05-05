package com.drl.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Table(name = "sysmgr_tbl_users")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ser_user_id")
    private Integer serUserId;
    
    @Column(name = "txt_user_name", length = 300)
    private String txtUserName;
    
    @Column(name = "txt_password", length = 150)
    private String txtPassword;
    
    @Column(name = "ser_language_id")
    private Integer serLanguageId;
    
    @Column(name = "bln_status")
    private Boolean blnStatus;
    
    @Column(name = "txt_machine_ip", length = 20)
    private String txtMachineIp;
    
    @Column(name = "ser_created_user_id")
    private Integer serCreatedUserId;
    
    @Column(name = "ser_modified_user_id")
    private Integer serModifiedUserId;
    
    @Column(name = "dte_createddate")
    private LocalDateTime dteCreatedDate;
    
    @Column(name = "dte_modifieddate")
    private LocalDateTime dteModifiedDate;
    
    @Column(name = "ser_employee_id")
    private Integer serEmployeeId;
    
    @Column(name = "bln_user_status")
    private Boolean blnUserStatus;
    
    @Column(name = "dte_user_status_date")
    private LocalDate dteUserStatusDate;
    
    @Column(name = "ser_group_id")
    private Integer serGroupId;
    
    @Column(name = "ser_store_id")
    private Integer serStoreId;
    
    // Constructors
    public User() {}
    
    public User(String txtUserName, String txtPassword) {
        this.txtUserName = txtUserName;
        this.txtPassword = txtPassword;
    }
    
    public User(int ser_user_id) {
    	this.serUserId = ser_user_id; 
    }

	// UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
    
    @Override
    public String getPassword() {
        return txtPassword;
    }
    
    @Override
    public String getUsername() {
        return txtUserName;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return blnStatus != null && blnStatus && blnUserStatus != null && blnUserStatus;
    }
    
   
}
