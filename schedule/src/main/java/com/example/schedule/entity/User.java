package com.example.schedule.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.schedule.Consts;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "group_code", referencedColumnName = "group_code", insertable = false, updatable = false)
	private Group group;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotEmpty(message = "グループ名は必須です。")
	@Column(name = "group_code")
	private String groupCode;

	@Column(name = "user_code")
	private String userCode;

	@NotEmpty(message = "ユーザ名またはログイン名は必須です。")
	@Size(max = Consts.MAX_CODE_LENGTH, message = "ユーザ名またはログイン名は最大 " + Consts.MAX_CODE_LENGTH + " 文字までです。")
	@Column(name = "user_name")
	private String userName;

	@NotEmpty(message = "ユーザの名は必須です。")
	@Size(max = Consts.MAX_NAME_LENGTH, message = "ユーザの名は最大 " + Consts.MAX_NAME_LENGTH + " 文字までです。")
	@Column(name = "user_first_name")
	private String userFirstName;

	@NotEmpty(message = "ユーザの姓は必須です。")
	@Size(max = Consts.MAX_NAME_LENGTH, message = "ユーザの姓は最大 " + Consts.MAX_NAME_LENGTH + " 文字までです。")
	@Column(name = "user_last_name")
	private String userLastName;

	@NotEmpty(message = "郵便番号は必須です。")
	@Pattern.List({ @Pattern(regexp = "[0-9\\-]+", message = "郵便番号が無効です。") })
	@Size(max = Consts.MAX_CODE_CONT_LENGTH, message = "郵便番号は最大 " + Consts.MAX_CODE_CONT_LENGTH + " 文字までです。")
	@Column(name = "post_code")
	private String postCode;

	@NotEmpty(message = "住所は必須です。")
	@Size(max = Consts.MAX_NAME_AREA_LENGTH, message = "住所は最大 " + Consts.MAX_NAME_AREA_LENGTH + " 文字までです。")
	@Column(name = "address")
	private String address;

	@NotEmpty(message = "電話番号は必須です。")
	@Pattern.List({ @Pattern(regexp = "[0-9\\-]+", message = "電話番号が無効です。") })
	@Size(max = Consts.MAX_CODE_CONT_LENGTH, message = "電話番号は最大 " + Consts.MAX_CODE_CONT_LENGTH + " 文字までです。")
	@Column(name = "tel_number")
	private String telNumber;

	@NotEmpty(message = "メールは必須です。")
	@Size(max = Consts.MAX_NAME_LENGTH, message = "メールは最大 " + Consts.MAX_NAME_LENGTH + " 文字までです。")
	@Email(message = "メールアドレスが無効です。「例) @ を含める必要があります」")
	@Column(name = "email")
	private String email;

	@NotEmpty(message = "パスワードは必須です。")
	@Column(name = "password")
	private String password;

	@Column(name = "del_flg")
	private Boolean delFlg;

	@Column(name = "created_by")
	private String createdBy;

	@JsonProperty("createdAt")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_by")
	private String updatedBy;

	@JsonProperty("updatedAt")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public User() {
	}

	public User(int id, String groupCode, String userCode, String userName, String userFirstName, String userLastName,
			String postCode, String address, String telNumber, String email, String password, Boolean delFlg,
			String createdBy, LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt, Group group,List<Role> roles) {
		this.id = id;
		this.groupCode = groupCode;
		this.userCode = userCode;
		this.userName = userName;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.postCode = postCode;
		this.address = address;
		this.telNumber = telNumber;
		this.email = email;
		this.delFlg = delFlg;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.group = group;
		this.roles = roles;
	}

	/*
	 * getterとsetterを定義する
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Boolean delFlg) {
		this.delFlg = delFlg;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@PrePersist
	protected void onCreate() {
		this.delFlg = false;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
