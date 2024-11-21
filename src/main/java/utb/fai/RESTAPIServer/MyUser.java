package utb.fai.RESTAPIServer;

import javax.persistence.*
@Entity
public class MyUser {
    @Id
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // method to validate user data
    public boolean isUserDataValid() {
        return name != null && !name.isEmpty() && email != null && !email.isEmpty();
    }
}

    public boolean isUserDataValid() {
        return isEmailValid(this.email) && isPhoneNumberValid(this.phoneNumber);
    }

    private boolean isEmailValid(String email) {
        return email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}");
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
    }
    // TODO: Getters and setters

}
