import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


//
public class BookStore {
	private String DB_Path;
	private Scanner input = new Scanner(System.in);
	public RedBlackTree bookStoreData;

	public BookStore() {
		bookStoreData = new RedBlackTree();
		System.out.print("Input data file's name:");
		this.DB_Path = input.nextLine();
		readData();
		inputShow();
	}

	/**
	 * Purpose : BookStore Input function, search( member number, purchase price, rank), modify in order to operate program and support exit
	 * 
	 */
	private void inputShow() {
		try {

			String num;
			while (true) {
				System.out.println("1.  Sign up");
				System.out.println("2.  Update member information");
				System.out.println("3.  Search information of member (member number)");
				System.out.println("4.  Search information of member (purchase price)");
				System.out.println("5.  Search information of member (rank)");
				System.out.println("6.  Program exit");
				System.out.print(" Choose operation what you want : ");
				num = input.nextLine();
				switch (Integer.parseInt(num)) {
				case 1:
					join();
					break;
				case 2:
					update();
					break;
				case 3:
					searchId();
					break;
				case 4:
					searchPrice();
					break;
				case 5:
					searchRank();
					break;
				case 6:
					writeData();// Write information before exit.
					System.exit(1);
					break;
				default:
					System.out.println("Wrong input!.");
					break;
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("Wrong with number Format!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void showData(int id, String name, String phoneNumber,
			int purchasePrice, int rank) {
		System.out.println("=======================================");
		System.out.println("---------------------------------------");
		System.out.println(" Member number : " + id);
		System.out.println(" Name of Member : " + name);
		System.out.println(" Contact number : " + phoneNumber);
		System.out.println(" Purchase price : " + purchasePrice);
		System.out.println(" Member's rank: " + rank);
		System.out.println("---------------------------------------");
		System.out.println("=======================================");
	}

	/**
	 * Purpose : Store operated RedBlackTree until present with DFS method to DB_Path before program exit.
	 * 
	 */
	private void writeData() {
		DB_Path.split("\\");

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(DB_Path));
			RedBlackNode temp = bookStoreData.getRoot();
			BookStoreMember tempMember;
			// Realized DFS method with producing stack place
			ArrayList<Object> dataList;

			Stack<RedBlackNode> st = new Stack<RedBlackNode>();
			st.push(temp);

			while (!st.isEmpty()) {

				temp = st.pop();
				dataList = (ArrayList<Object>) temp.getData();

				for (int i = 0; i < dataList.size(); i++) {
					tempMember = (BookStoreMember) dataList.get(i);
					out.write(tempMember.getId() + " " + tempMember.getName()
							+ " " + tempMember.getPhoneNumber() + " "
							+ tempMember.getPurchasePrice());
					out.newLine();
				}
				if (!(temp.getLeft() == null)) {
					st.push(temp.getLeft());
				}
				if (!(temp.getRight() == null)) {
					st.push(temp.getRight());
				}
			}

			out.close();
		} catch (IOException e) {
			System.err.println(e); // If there is an error, print message
			System.exit(1);
		}

	}

	/**
	 * purpose : Read the existed file in the DB_Path, and then check effectiveness of file, and change to RedBlackTree 
    */
	private void readData() {
		String temp;
		int id;
		String name;
		String phoneNumber;
		int purchasePrice;

		try {

			FileReader textFileReader = new FileReader(DB_Path);

			BufferedReader in = new BufferedReader(textFileReader);

			while ((temp = in.readLine()) != null) {

				id = Integer.parseInt(temp.split(" ")[0]);
				name = temp.split(" ")[1];
				phoneNumber = temp.split(" ")[2];
				purchasePrice = Integer.parseInt(temp.split(" ")[3]);

				BookStoreMember tempMember = new BookStoreMember(id, name,
						phoneNumber, purchasePrice);
				bookStoreData.insert(purchasePrice, tempMember);

			}

			in.close();
		} catch (IOException e) {
			System.out.println(" Error Of IO problem during reading process ");
			System.out.println(" Program exit by force");
			System.exit(1);
		} catch (Exception e) {
			System.out.println(" Error of IO problem during reading data " + e.getMessage());
			System.out.println(" Program exit by force");
			System.exit(1);
		}
	}

	/**
	 * Find node and there exist datalist. The problem is that when we use this datalist, we need to use arrayed one. 
    * Comparator is realized with method like that arrayed in Collection.sort function.
	 * 
	 * */
	private final static Comparator<BookStoreMember> bsComparator = new Comparator<BookStoreMember>() {

		@Override
		public int compare(BookStoreMember o1, BookStoreMember o2) {
			// TODO Auto-generated method stub
			return o1.getId() - o2.getId();
		}
	};

	/**
	 * Purpose : Sign up. Check effectiveness and if the effectiveness is not suitable, error. 
  	 */
	public void join() {
		int id;
		String name;
		String phoneNumber;
		int purchasePrice;

		try {

			System.out.print(" Member number: ");
			id = Integer.parseInt(input.nextLine());

			try {
				findById(id);
			} catch (Exception e) {

				System.out.print(" Member name: ");
				name = input.nextLine();
				System.out.print(" Contact number: ");
				phoneNumber = input.nextLine();
				System.out.print(" Purchase price: ");
				purchasePrice = Integer.parseInt(input.nextLine());

				bookStoreData.insert(purchasePrice, new BookStoreMember(id,
						name, phoneNumber, purchasePrice));

				System.out.println("\n Added!");
				return;
			}
			throw new Exception(getClass().getName()
					+ "-join- Existed ID!");

		} catch (NumberFormatException e) {
			System.out.println(" Input suitable format!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Purpose : Search member information in RedBlackTree. Method : Search every nodes of RedBlackTree with BFS method.
  	 * Bring every nodes of data(ArrayList type) and then compare with member ID in the list and member number.
	 * 
	 * @param Member number
	 * @return Return three objects. If the data of member is exist, then initialized to each different stuff
    * Object[0] = Store BookStoreMember here which is same ID amaong BookStoreMemer which stored in dataList.
	 * Object[1] = Store dataList
    * Object[2] = Store position(which position in dataList)
    * 
	 **/
	private Object[] findById(int id) throws Exception {
		Queue<RedBlackNode> queue = new LinkedList<RedBlackNode>();
		queue.offer(bookStoreData.getRoot());

		RedBlackNode temp;
		ArrayList<BookStoreMember> dataList;
		BookStoreMember tempMember;
		Object[] tempObjArr = new Object[3];
		while (!queue.isEmpty()) {
			temp = (RedBlackNode) queue.poll();
			dataList = (ArrayList<BookStoreMember>) temp.getData();
			Collections.sort(dataList, bsComparator);
			for (int i = 0; i < dataList.size(); i++) {
				tempMember = (BookStoreMember) dataList.get(i);
				if (tempMember.getId() == id) {
					tempObjArr[0] = tempMember;
					tempObjArr[1] = dataList;
					tempObjArr[2] = i;
					return tempObjArr;
				}

			}

			if (!(temp.getLeft() == null)) {
				queue.offer(temp.getLeft());
			}
			if (!(temp.getRight() == null)) {
				queue.offer(temp.getRight());
			}
		}

		throw new Exception(getClass().getName()
				+ "-findById- Not exist ID!");
	}

	/**
	 * Purpose : Process checking effectiveness, and function for update.
	 */
	public void update() {
		int id;
		String name;
		String phoneNumber;
		try {
			System.out.print(" Member number : ");
			id = Integer.parseInt(input.nextLine());
			Object[] tempObjArr = findById(id);
			BookStoreMember tempMember = (BookStoreMember) tempObjArr[0];

			System.out.print(" New Name : ");
			name = input.nextLine();
			System.out.print(" New Contact number : ");
			phoneNumber = input.nextLine();
			new BookStoreMember(id, name, phoneNumber, 10);
															
			tempMember.setName(name);
			tempMember.setPhoneNumber(phoneNumber);
			System.out.println("\n Success change!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Purpose : Input member number, output data of that member number.
	 * 
	 */
	public void searchId() throws Exception {
		System.out.print(" Member number : ");
		int id = Integer.parseInt(input.nextLine());
		searchId(id);
	}

	/**
	 * 
	 * Find data with ID of member with findByld function.(ID is key)
	 * 
	 * @param Member number
	 * 
	 **/
	private void searchId(int id) {
		try {
			Object[] tempObjArr = findById(id);
			BookStoreMember tempMember = (BookStoreMember) tempObjArr[0];

			showData(
					id,
					tempMember.getName(),
					tempMember.getPhoneNumber(),
					tempMember.getPurchasePrice(),
					(bookStoreData.getRank(tempMember.getPurchasePrice()) + (int) tempObjArr[2]));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Purpose : when input purchase price, ouput data of member of the purchase price.( If the rank is same, member number is priority)
	 * 
	 */
	public void searchPrice() throws Exception {
		System.out.print(" Purchase price : ");
		int purchasePrice = Integer.parseInt(input.nextLine());
		searchPrice(purchasePrice);
	}

		private void searchPrice(int purchasePrice) {
		try {

			BookStoreMember temp;
			BookStoreMember tempMember;

			ArrayList<BookStoreMember> dataList = (ArrayList<BookStoreMember>) bookStoreData
					.findData(purchasePrice);

			Collections.sort(dataList, bsComparator);
			tempMember = dataList.get(0);

			if (tempMember != null) {
				showData(tempMember.getId(), tempMember.getName(),
						tempMember.getPhoneNumber(),
						tempMember.getPurchasePrice(),
						bookStoreData.getRank(purchasePrice));

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Purpose : When input rank, ouput data of member (If rank is same, member number is priority)
	 * 
	 */
	public void searchRank() throws Exception {
		System.out.print(" Rank : ");
		int rank = Integer.parseInt(input.nextLine());
		searchRank(rank);
	}

		private void searchRank(int rank) {
		try {
			BookStoreMember temp;
			BookStoreMember tempMember;

			ArrayList<BookStoreMember> dataList = (ArrayList<BookStoreMember>) bookStoreData
					.findDataByRank(rank);

			Collections.sort(dataList, bsComparator);

			temp = (BookStoreMember) dataList.get(0);
			tempMember = (BookStoreMember) dataList.get(rank
					- bookStoreData.getRank(temp.getPurchasePrice()));

			if (tempMember != null) {
				showData(tempMember.getId(), tempMember.getName(),
						tempMember.getPhoneNumber(),
						tempMember.getPurchasePrice(), rank);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
