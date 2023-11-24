package com.sheffield.model.user;

public class User {
    private String userId;
    private String email;
    private int failed_login_attempts;
    private String password_hash;
    private boolean account_locked;
    private String forename;
    private String surname;

    // Constructor to initialize a User object with its attributes
    public User(String userId, String email, String password_hash, int failed_login_attempts, boolean account_locked, String forename, String surname) {
        this.setuserId(userId);
        this.setemail(email);
        this.setPasswordHash(password_hash);
        this.setFailedLogin(failed_login_attempts);
        this.setaccountLocked(account_locked);
        this.setForename(forename);
        this.setSurname(surname);
    }

    // Getter and setter methods for the userId attribute with validation
    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        if (isValiduserId(userId)) {
            this.userId = userId;
        } else {
            throw new IllegalArgumentException("userId is not valid.");
        }
    }

    // Getter and setter methods for the email attribute with validation
    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        if (isValidemail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("email is not valid.");
        }
    }

    // Getter and setter methods for the failed login attempts attribute with validation
    public int getFailedLoginAttempt() {
        return failed_login_attempts;
    }

    public void setFailedLogin(int failed_login_attempts) {
        this.failed_login_attempts = failed_login_attempts;
    }

    // Getter and setter methods for the Genre attribute with validation
    public String getPasswordHash() {
        return password_hash;
    }

    public void setPasswordHash(String password_hash) {
        this.password_hash = password_hash;
    }

    public boolean getaccountLocked() {
        return account_locked;
    }    

    public void setaccountLocked(boolean account_locked) {
        this.account_locked = account_locked;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getForename() {
        return forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }



    // Private validation methods for each attribute

    private boolean isValiduserId(String userId) {
        // Implement userId validation logic here (e.g., length, format)
        return userId != null && userId.length() == 36;
    }

    private boolean isValidemail(String email) {
        // Implement email validation logic here (e.g., length)
        return email != null && email.length() <= 100;
    }


    @Override
    public String toString() {
        return "{ " +
            " userId='" + getuserId() + "'" +
            ", email='" + getemail() + "'" +
            ", failed attempts ='" + getFailedLoginAttempt() + "'" +
            ", Account Locked ='" + getaccountLocked() + "'" +
            ", Forename ='" + getForename() + "'" +
            ", Surname ='" + getSurname() + "'" +
            " }";
    }

}