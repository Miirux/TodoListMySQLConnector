import java.util.*;

public class EncryptionProgram {
	private Scanner scanner;
	private ArrayList<Character> list;
	private ArrayList<Character> shuffledList;
	private char character;
	private char[] letters;
	
	EncryptionProgram(){
		scanner = new Scanner(System.in);
		list = new ArrayList<>();
		shuffledList = new ArrayList<>();
		character = ' ';

		newKey();
		askQuestion();
	}
	private void askQuestion(){
		while(true) {
			System.out.println("Hello, welcome to the Substitution Cipher Encryption Program!");
			System.out.println("Here's the menu:");
			System.out.println("N: NewKey\nG: GetKey\nE: Encrypt\nD: Decrypt\nQ: Quit");
			char response = Character.toUpperCase(scanner.nextLine().charAt(0));
			
			switch(response) {
			case 'N':
				newKey();
				break;
			case 'G':
				getKey();
				break;
			case 'E':
				encrypt();
				break;
			case 'D':
				decrypt();
				break;
			case 'Q':
				quit();
				break;
			default:
				System.out.println("Key not found, try again...");
			}
		}
	}
	private void newKey(){
		
		character = ' ';
		list.clear();
		shuffledList.clear();
		
		for(int i=32;i<127;i++) {
			list.add(Character.valueOf(character));
			character++;
		}
		
		shuffledList = new ArrayList<>(list);
		Collections.shuffle(shuffledList);
		System.out.println("A new key has been created");
		
	}
	private void getKey(){
		System.out.println("Key: ");
		for(Character x : list) {
			System.out.print(x);
		}
		System.out.println();
		for(Character x : shuffledList) {
			System.out.print(x);
		}
		System.out.println();
	}
	private void encrypt(){
		System.out.println("Enter a message you want to encrypt: ");
		String message = scanner.nextLine();
		
		letters = message.toCharArray();
		
		for(int i =0;i<letters.length;i++) {
			
			for(int j =0;j<list.size();j++) {
				if(letters[i]==list.get(j)) {
					letters[i]=shuffledList.get(j);
					break;
				}
			}
		}
		System.out.println("Encrypted: ");
		for(char x : letters) {
			System.out.print(x);
		}
		System.out.println();
	}
	private void decrypt(){
		System.out.println("Enter a message tou want to decrypt: ");
		String message = scanner.nextLine();
		
		letters = message.toCharArray();
		
		for(int i =0;i<letters.length;i++) {
			
			for(int j =0;j<shuffledList.size();j++) {
				if(letters[i]==shuffledList.get(j)) {
					letters[i]=list.get(j);
					break;
				}
			}
		}
		System.out.println("Decrypted: ");
		for(char x : letters) {
			System.out.print(x);
		}
		System.out.println();
	}
	private void quit(){
		System.out.println("Good bye!");
		System.exit(0);
	}
}

