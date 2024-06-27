import java.util.ArrayList;

class User {

    private final ArrayList <String> userNames = new ArrayList<String>(){{
        add("admin");
    }};
    private final ArrayList <Integer> userContacts = new ArrayList<Integer>(){{
        add(987654321);
    }};
    private final ArrayList <String> userFullNames = new ArrayList<String>(){{
        add("ADMIN");
    }};
    private final ArrayList <String> userPasswords = new ArrayList<String>(){{
        add("admin123");
    }};

    public void UserRegistration( String username, String password, String fullName, int contact){
        addUser(username, password, fullName, contact);
    }

    public boolean checkifUserExists(String username){
        return userNames.contains(username);
    }

    private void addUser(String username, String pass, String name,int contactNo){
        userNames.add(username);
        userPasswords.add(pass);
        userFullNames.add(name);
        userContacts.add(contactNo);
    }

    public boolean checkPass(String username, String pass){
        System.out.println("CLASS USER: " +username + " " + pass + " " + userPasswords.get(userNames.indexOf(username)) + " | " +userPasswords.get(userNames.indexOf(username)).equals(pass));
        return userPasswords.get(userNames.indexOf(username)).equals(pass);
    }
}