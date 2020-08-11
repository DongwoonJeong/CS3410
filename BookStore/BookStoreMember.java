
public class BookStoreMember {
	private int id;
	private String name;
	private String phoneNumber;
	private int purchasePrice;

	public BookStoreMember(int id, String name, String phoneNumber,
			int purchasePrice) throws Exception {

		setId(id);
		setName(name);
		setPhoneNumber(phoneNumber);
		setPurchasePrice(purchasePrice);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws Exception {
		if (id > 0 && id <= 100000)
			this.id = id;
		else
			throw new Exception(getClass().getName() +  "-setID- Please type number between 0 to 100000.");
	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws Exception {
		if (name.getBytes().length <= 20)
			this.name = name;
		else
			throw new Exception(getClass().getName() +  "-setName- Name cannot be over 20byte");
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) throws Exception {
		if (phoneNumber.getBytes().length <= 11)
			this.phoneNumber = phoneNumber;
		else
			throw new Exception(getClass().getName() +  "-setPhoneNumber- phone number cannot be over 10btye ");
	}

	public int getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(int purchasePrice) throws Exception {
		this.purchasePrice = purchasePrice;
		if (purchasePrice >= 0)
			this.purchasePrice = purchasePrice;
		else
			throw new Exception(getClass().getName() +  "-setPurchasePrice- Please type valid number..");
	}

}
