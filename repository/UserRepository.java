package repository;
import java.util.HashMap;
import java.util.Map;

import user.*;

public class UserRepository {

    private Map<Integer, Users> datastore = new HashMap<>();

    public void createAccount(Users user) {
        this.datastore.put(user.getID(), user.clone());
    }

    public Users retrieveAccount(int id) {
        return this.datastore.get(id).clone();
    }

    public void updateAccount(Users user) {
        this.datastore.put(user.getID(), user.clone());
    }

    public void deleteAccount(int id) {
        this.datastore.remove(id);
    }

}