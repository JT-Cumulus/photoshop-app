package repository;

import user.Users;

public class UserRepositoryImpl implements UserRepository {
    private UserRepository userDaoImpl;
    
    @Override
    public Users get(Long id) {
        Users user = userDaoImpl.read(id);
        return user;
    }

    @Override
    public void add(Users user) {
        userDaoImpl.create(user);
    }

    @Override
    public void update(Users user) {

    }

    @Override
    public void remove(Users user) {

    }
}
