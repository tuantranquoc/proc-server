package com.example.demo.model.user;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
	
	
    @Id   
    @Column(name = "id")
    private String id;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "name")
    private String name;

    public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User(String username, String password, String email, String name) {		
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
	}

	public User() {		
	}
	
	

    
    
}
