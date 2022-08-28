package object;

public class BillingAddress {
	private String firstName;
	private String lastName;
	private String addressLineOne;
	private String city;
	private String postalCode;
	private String email;
	private String country;
	private String state;
	
	//default constructor
	public BillingAddress(){}
	
	//Constructor with parameter (it should have a default one)
	public BillingAddress (String firstname, String lastname, String addresslineone, 
			String city, String postalcode, String email, String country, String state) {
		this.firstName = firstname;
		this.lastName = lastname;
		this.addressLineOne = addresslineone;
		this.city = city;
		this.postalCode = postalcode;
		this.email = email;
		this.country = country;
		this.state = state;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public BillingAddress setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public BillingAddress setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public String getAddressLineOne() {
		return addressLineOne;
	}
	
	public BillingAddress setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
		return this;
	}
	
	public String getCity() {
		return city;
	}
	
	public BillingAddress setCity(String city) {
		this.city = city;
		return this;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public BillingAddress setPostalCode(String postalCode) {
		this.postalCode = postalCode;
		return this;
	}
	
	public String getEmail() {
		return email;
	}
	
	public BillingAddress setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
