package model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	private static final long serialVersionUID = -8465990321138923924L;

	// todo: Posem el role?
	private int id; // primary key auto increment
	private String username;
	private String password;
	private String email;
	private String gender;
	private java.sql.Date birthdate;
	private String picture;
	private Polis polis;
	private Integer polisId;
	private Integer socialCredit = 0;


    public User() {
		super();

	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
        this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public java.sql.Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(java.sql.Date birthdate) {
		this.birthdate = birthdate;
	}

	public Polis getPolis() {
		return polis;
	}

	public void setPolis(Polis polis) {
		this.polis = polis;
	}

	public int getPolisId() {
		return polisId;
	}

	public void setPolisId(int polisId) {
		this.polisId = polisId;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Integer getSocialCredit() {
		return socialCredit;
	}

	public void setSocialCredit(Integer socialCredit) {
		this.socialCredit = socialCredit;
	}

}